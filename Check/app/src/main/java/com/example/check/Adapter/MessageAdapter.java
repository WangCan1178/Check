package com.example.check.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.check.Entity.Member;
import com.example.check.Entity.Message;
import com.example.check.JoinGroupActivity;
import com.example.check.R;
import com.example.check.SubmitTaskActivity;
import com.example.check.menu.MainActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class MessageAdapter extends ArrayAdapter<Message> {
    private String baseURL = "http://10.0.2.2:9000";
    private SharedPreferences sp;
    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> members) {
        super(context, resource, members);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        final Message message = getItem(position);//得到当前项的 Message 实例
        //为每一个子项加载设定的布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.message_item, parent, false);
        //分别获取 image view 和 textview 的实例

        TextView content = view.findViewById(R.id.content);
        TextView title = view.findViewById(R.id.title);
        TextView flag = view.findViewById(R.id.flag);
        TextView task = view.findViewById(R.id.task);
        Button delete =view.findViewById(R.id.but_delete);
        title.setText(message.getTitle());
        content.setText("你收到了一条催办任务，任务时间为" + message.getTime()+ "，请及时处理。");
//        System.out.println("是否未读："+message.getIsread());
        if (message.getIsread()==0) {
            flag.setTextColor(0xffff0000);
            flag.setText("！");
        } else if(message.getIsread()==1){
            flag.setTextColor(0xff339900);
            flag.setText("√");
        }
        sp = getContext().getSharedPreferences("onClick", MODE_PRIVATE);
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                message.setIsread(1);
                StringBuilder stringBuilder = new StringBuilder(baseURL+"/alterRead");
                stringBuilder.append("?");
                stringBuilder.append("isread").append("=").append(message.getIsread()).append("&");
                stringBuilder.append("mesid").append("=").append(message.getMesid());
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
                        if (result.equals("1")) {
                            ((Activity) getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DynamicToast toast = new DynamicToast();
                                    Toast toast1 = toast.makeSuccess(view.getContext(), "消息已读成功");
                                    toast1.setGravity(Gravity.TOP, 0, 50);
                                    toast1.show();
//                                    Intent intent = new Intent(view.getContext(), MainActivity.class);
////                                    intent.putExtra("groupid",member.getGroupid());
//                                    ((Activity)getContext()).startActivity(intent);
                                }
                            });
                        }
                        Intent intent = new Intent(view.getContext(), com.example.check.SubmitTaskActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("taskid", message.getTaskid());
                        bundle1.putInt("groupid", message.getGroupid());
                        intent.putExtras(bundle1);
                        ((Activity) getContext()).startActivity(intent);
                    }
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                OkHttpClient client = new OkHttpClient();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mesid",message.getMesid());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),String.valueOf(jsonObject));
                final Request request = new Request.Builder()
                        .url(baseURL+"/deleteMes")
                        .post(requestBody)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        if(result.equals("success")){
                            ((Activity)getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DynamicToast toast = new DynamicToast();
                                    Toast toast1 = toast.makeSuccess(view.getContext(),"删除成功");
                                    toast1.setGravity(Gravity.TOP,0,50);
                                    toast1.show();
                                    Intent intent = new Intent(view.getContext(), MainActivity.class);
//                                    intent.putExtra("groupid",member.getGroupid());
                                    ((Activity)getContext()).startActivity(intent);
                                }
                            });
                        }
                    }
                });
            }
        });
        return view;
    }
}