package com.example.check.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.check.Adapter.MemberAdapter;
import com.example.check.Entity.Member;
import com.example.check.R;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link cMemberFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link cMemberFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class cMemberFragment extends Fragment {
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

    public cMemberFragment() {
        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment cMemberFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static cMemberFragment newInstance(String param1, String param2) {
//        cMemberFragment fragment = new cMemberFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    private String baseURL = "http://10.0.2.2:9000/manage";
    private SharedPreferences sp;
    private ListView listView;
    private Handler handler;
    private ArrayList<Member> members;
    private MemberAdapter adapter;
    private Button but_add_role;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_c_member, container, false);

        listView = (ListView)view.findViewById(R.id.memberList);
        handler = new Handler();
        but_add_role = (Button)view.findViewById(R.id.but_add_role);

        final Bundle bundle = getArguments();
        final int id = bundle.getInt("groupid");

        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getMember");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(id);
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
                members = new ArrayList<>();
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
                            Member member = new Member();
                            member.setGroupid(jsonArray.getJSONObject(i).getInt("groupid"));
                            member.setMemid(jsonArray.getJSONObject(i).getString("userid"));
                            member.setMemname(jsonArray.getJSONObject(i).getString("memname"));
                            members.add(member);
                        }
//                        System.out.println(members);
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new MemberAdapter(getActivity(),R.layout.member_item,members);
                                listView.setAdapter(adapter);
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
        but_add_role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setIcon(R.drawable.account);
                builder.setTitle("请输入要添加的管理员");
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.revise_group_name, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(view1);

                final EditText groupname = (EditText)view1.findViewById(R.id.group_name);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        final String a = groupname.getText().toString().trim();
                        //    将输入的用户名和密码打印出来
                        //Toast.makeText(getActivity(), "用户昵称: " + a , Toast.LENGTH_SHORT).show();
                        if (a.equals("")){
                            DynamicToast toast = new DynamicToast();
                            Toast toast1 = toast.makeError(getActivity(),"昵称不能为空");
                            toast1.setGravity(Gravity.TOP,0,50);
                            toast1.show();
                        }else{
                            OkHttpClient okHttpClient = new OkHttpClient();
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("userid",a);
                                jsonObject.put("groupid",id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),String.valueOf(jsonObject));
                            final Request request = new Request.Builder()
                                    .url(baseURL+"/addManager")
                                    .post(requestBody)
                                    .build();
                            Call call = okHttpClient.newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    final String result = response.body().string();
                                    if (result.equals("success")){
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DynamicToast toast = new DynamicToast();
                                                Toast toast1 = toast.makeSuccess(getActivity(),"修改成功");
                                                toast1.setGravity(Gravity.TOP,0,50);
                                                toast1.show();
                                                //刷新页面
                                                onCreate(bundle);
                                            }
                                        });
                                    }

                                }
                            });
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                builder.show();
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
