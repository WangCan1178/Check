package com.example.check.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.check.Adapter.GroupAdapter;
import com.example.check.Entity.Group;
import com.example.check.R;
import com.example.check.menu.createActivity;
import com.example.check.menu.joinActivity;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link createFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link createFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class createFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;

    public createFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment createFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static createFragment newInstance(String param1, String param2) {
//        createFragment fragment = new createFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
    private Button but_cgroup;
    private ImageView but_creategroup;

    private String baseURL = "http://192.168.43.116:9000/manage";
    private SharedPreferences sp;
    private ListView listView;
    private Handler handler;
    private GroupAdapter adapter;
    private List<Group> groups;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_create, container, false);
        but_creategroup = (ImageView) view.findViewById(R.id.but_creategroup);
        but_creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.example.check.AddGroupActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView)view.findViewById(R.id.datalist);
        handler = new Handler();

        sp = getActivity().getSharedPreferences("onClick", MODE_PRIVATE);

        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getManGroup");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(sp.getString("userId",""));
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(stringBuilder.toString())
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                JSONArray jsonArray = null;
                groups = new ArrayList<>();
                try {
                    jsonArray = new JSONArray(result);
                    for(int i=0;i<jsonArray.length();i++){
                        Group group = new Group();
                        group.setId(jsonArray.getJSONObject(i).getInt("groupid"));
                        group.setDescribe(jsonArray.getJSONObject(i).getString("describe"));
                        group.setName(jsonArray.getJSONObject(i).getString("name"));
                        groups.add(group);
                    }
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            adapter = new GroupAdapter(getActivity(),R.layout.group_item,groups);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent =new Intent(getActivity(), createActivity.class);
                                    intent.putExtra("groupid",groups.get(i).getId());
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    new Thread(){
                        public void run(){
                            handler.post(runnable);
                        }
                    }.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
