package com.example.check.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.check.Adapter.TaskApater;
import com.example.check.Entity.Isend;
import com.example.check.R;
import com.example.check.menu.MainActivity;
import com.example.check.menu.joinActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.StringReader;
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
// * {@link jTaskFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link jTaskFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class jTaskFragment extends Fragment {
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

    public jTaskFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment jTaskFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static jTaskFragment newInstance(String param1, String param2) {
//        jTaskFragment fragment = new jTaskFragment();
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
    private ImageView but_return,but_refresh;

    private String baseURL = "http://10.0.2.2:9000/add";
    private SharedPreferences sp;
    private List<Isend> tasks;
    private TaskApater adapter;
    private ListView listView;
    private Handler handler;
private int id;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_j_task, container, false);

        but_refresh=(ImageView)view.findViewById(R.id.but_refresh);
        but_return=(ImageView)view.findViewById(R.id.but_return);

        Bundle bundle = getArguments();
        id = bundle.getInt("groupid");
        Toast.makeText(getActivity(),"group"+id,Toast.LENGTH_SHORT).show();
        but_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        but_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), joinActivity.class);
                intent.putExtra("groupid",id);
                startActivity(intent);
            }
        });
        sp = getActivity().getSharedPreferences("onClick", MODE_PRIVATE);
        listView = (ListView)view.findViewById(R.id.tasklist1) ;
        handler = new Handler();

        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getTask");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(id).append("&");
        stringBuilder.append("userid").append("=").append(sp.getString("userId",""));
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
                tasks = new ArrayList<>();
                if (result.equals("")){
                    Looper.prepare();
                    DynamicToast toast = new DynamicToast();
                    Toast toast1 = toast.makeError(getActivity(),"出错啦");
                    toast1.setGravity(Gravity.TOP,0,50);
                    toast1.show();
                    Looper.loop();
                    return;
                }else {
                    try {
                        jsonArray = new JSONArray(result);
                        for(int i=0;i<jsonArray.length();i++){
                            Isend task = new Isend();
                            task.setTaskid(jsonArray.getJSONObject(i).getInt("taskid"));
                            task.setTitle(jsonArray.getJSONObject(i).getString("title"));
//                            task.setIfend(jsonArray.getJSONObject(i).getInt("ifend"));
                            task.setResult(jsonArray.getJSONObject(i).getInt("result"));
                            task.setIfend(jsonArray.getJSONObject(i).getInt("ifend"));
                            task.setGroupid(jsonArray.getJSONObject(i).getInt("groupid"));
//                            if ((jsonArray.getJSONObject(i).getInt("ifenf"))==0){
//                                Toast.makeText(getActivity(),"该任务已经结束了",Toast.LENGTH_SHORT).show();
//                                Intent intent =new Intent(getActivity(), jTaskFragment.class);
//                                startActivity(intent);
//                            }
                            tasks.add(task);
                        }
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new TaskApater(getActivity(),R.layout.task_item,tasks);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        if(tasks.get(i).getIfend()==1){
                                            DynamicToast toast = new DynamicToast();
                                            Toast toast1 = toast.makeError(getActivity(),"该任务已经结束了！");
                                            toast1.setGravity(Gravity.TOP,0,50);
                                            toast1.show();
                                        }else if(tasks.get(i).getIfend()==0){
                                            Intent intent = new Intent(getActivity(), com.example.check.SubmitTaskActivity.class);
                                            Bundle bundle1 = new Bundle();
                                            bundle1.putInt("taskid", tasks.get(i).getTaskid());
                                            bundle1.putInt("groupid",tasks.get(i).getGroupid());
                                            intent.putExtras(bundle1);
                                            startActivity(intent);
                                        }
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
            }
        });

        return view;
    }
//
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
