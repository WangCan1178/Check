package com.example.check.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.JsonReader;
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
import com.example.check.R;
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

public class MemberAdapter extends ArrayAdapter<Member> {
    private String baseURL = "http://10.0.2.2:9000/manage";
    private SharedPreferences sp;

    public MemberAdapter(@NonNull Context context, int resource, @NonNull List<Member> members) {
        super(context, resource, members);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        final Member member = getItem(position);//得到当前项的 Member 实例
        //为每一个子项加载设定的布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.member_item, parent, false);
        //分别获取 image view 和 textview 的实例
        TextView memberText = view.findViewById(R.id.memberText);
        Button delete = view.findViewById(R.id.deleteMember);
        // 设置要显示的图片和文字
        memberText.setText(member.getMemname());
        sp = getContext().getSharedPreferences("onClick", MODE_PRIVATE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                OkHttpClient client = new OkHttpClient();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userid",member.getMemid());
                    jsonObject.put("groupid",member.getGroupid());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),String.valueOf(jsonObject));
                final Request request = new Request.Builder()
                        .url(baseURL+"/delMember")
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
                                    Intent intent = new Intent(view.getContext(), com.example.check.menu.createActivity.class);
                                    intent.putExtra("groupid",member.getGroupid());
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