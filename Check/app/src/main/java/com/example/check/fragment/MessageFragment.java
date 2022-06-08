package com.example.check.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.check.Adapter.MessageAdapter;
import com.example.check.Adapter.TaskApater;
import com.example.check.Entity.Isend;
import com.example.check.Entity.Message;
import com.example.check.R;
import com.example.check.TaskActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
// * {@link MessageFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link MessageFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class MessageFragment extends Fragment {
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
//
    public MessageFragment() {
        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MessageFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static MessageFragment newInstance(String param1, String param2) {
//        MessageFragment fragment = new MessageFragment();
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
    private String baseURL = "http://10.0.2.2:9000";
    private SharedPreferences sp;
    private List<Message> messages;
    private MessageAdapter adapter;
    private ListView listView;
    private Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_message, container, false);
        sp = getActivity().getSharedPreferences("onClick", MODE_PRIVATE);
        listView = (ListView)view.findViewById(R.id.messagelist) ;
        handler = new Handler();
        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getMess");
        stringBuilder.append("?");
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
                messages = new ArrayList<>();
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
                            Message message = new Message();
                            message.setGroupid(jsonArray.getJSONObject(i).getInt("groupid"));
                            message.setIsread(jsonArray.getJSONObject(i).getInt("isread"));
                            message.setTitle(jsonArray.getJSONObject(i).getString("title"));
                            message.setGroupid(jsonArray.getJSONObject(i).getInt("groupid"));
                            message.setTime(jsonArray.getJSONObject(i).getString("time"));
                            message.setTaskid(jsonArray.getJSONObject(i).getInt("taskid"));
                            message.setMesid(jsonArray.getJSONObject(i).getInt("mesid"));
//                            message.setTime(timeStamp2Date(jsonArray.getJSONObject(i).getString("time"),null));
                            messages.add(message);
                        }
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new MessageAdapter(getActivity(),R.layout.message_item,messages);
                                listView.setAdapter(adapter);
//                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                        OkHttpClient client = new OkHttpClient();
//                                        JSONObject jsonObject = new JSONObject();
//                                        try {
//                                            messages.get(i).setIsread(1);
//                                            jsonObject.put("tid",messages.get(i).getMesid());
//                                            jsonObject.put("isred",messages.get(i).getIsread());
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),String.valueOf(jsonObject));
//                                        final Request request = new Request.Builder()
//                                                .url(baseURL+"/alterRead")
//                                                .post(requestBody)
//                                                .build();
//                                        Call call = client.newCall(request);
//                                        call.enqueue(new Callback() {
//                                            @Override
//                                            public void onFailure(Call call, IOException e) {
//
//                                            }
//
//                                            @Override
//                                            public void onResponse(Call call, Response response) throws IOException {
//                                                String result = response.body().string();
//                                                if(result=="success") {
//                                                    DynamicToast toast = new DynamicToast();
//                                                    Toast toast1 = toast.makeSuccess(getActivity(), "消息已读成功");
//                                                    toast1.setGravity(Gravity.TOP, 0, 50);
//                                                    toast1.show();
//                                                }
//                                            }
//                                        });
//                                        Intent intent = new Intent(getActivity(), com.example.check.SubmitTaskActivity.class);
//                                        Bundle bundle1 = new Bundle();
//                                        bundle1.putInt("taskid",messages.get(i).getTaskid());
//                                        bundle1.putInt("groupid",messages.get(i).getGroupid());
//                                        intent.putExtras(bundle1);
//                                        startActivity(intent);
//                                    }
//                                });
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
    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
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
