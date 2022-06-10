package com.example.check.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.check.AddGroupActivity;
import com.example.check.JoinGroupActivity;
import com.example.check.R;
import com.example.check.menu.MainActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link jGroupFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link jGroupFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class jGroupFragment extends Fragment {
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

    public jGroupFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment jGroupFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static jGroupFragment newInstance(String param1, String param2) {
//        jGroupFragment fragment = new jGroupFragment();
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
    private Button but_exit;
    private TextView my_name;
    private TextView my_phone;
    private TextView my_id;
    private String baseURL = "http://192.168.43.116:9000/add";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_j_group, container, false);

        my_name = (TextView) view.findViewById(R.id.myjgroupname);
        my_id = (TextView) view.findViewById(R.id.myjgroupid);
        my_phone = (TextView) view.findViewById(R.id.myjgroupphone);

        final SharedPreferences sp = getActivity().getSharedPreferences("onClick",MODE_PRIVATE);
        final String id = sp.getString("userId","");

        Bundle bundle = getArguments();
        final int groupid = bundle.getInt("groupid");

        StringBuilder stringBuilder = new StringBuilder(baseURL+"/altername");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(groupid).append("&");
        stringBuilder.append("userid").append("=").append(id);
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
                final String result = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        my_name.setText(result);
                        my_id.setText("群组id："+groupid);
                        my_phone.setText(sp.getString("userId",""));
                    }
                });

            }
        });

        but_exit = (Button) view.findViewById(R.id.but_exit3);
        but_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient okHttpClient = new OkHttpClient();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userid",sp.getString("userId",""));
                    jsonObject.put("groupid",groupid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),String.valueOf(jsonObject));
                final Request request = new Request.Builder()
                        .url(baseURL+"/delAddGroup")
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        if(result.equals("success")){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DynamicToast toast = new DynamicToast();
                                    Toast toast1 = toast.makeSuccess(getActivity(),"退出群组成功");
                                    toast1.setGravity(Gravity.TOP,0,50);
                                    toast1.show();
                                }
                            });
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        my_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setIcon(R.drawable.account);
                builder.setTitle("请输入要修改的用户名");
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.revise_group_name, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(view);

                final EditText groupname = (EditText)view.findViewById(R.id.group_name);

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
                                jsonObject.put("userid",sp.getString("userId",""));
                                jsonObject.put("groupid",groupid);
                                jsonObject.put("memname",a);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),String.valueOf(jsonObject));
                            final Request request = new Request.Builder()
                                    .url(baseURL+"/alter")
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
                                                my_name.setText(a);
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
