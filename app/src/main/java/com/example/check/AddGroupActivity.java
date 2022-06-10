package com.example.check;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.check.addtask.AddTaskJKMActivity;
import com.example.check.menu.MainActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddGroupActivity extends AppCompatActivity {
    private EditText cgroupname;  //创建群组的群组名称
    //private TextView cgroupID;  //创建完成后系统给出的群组号
    private Button but_create_group;
    private String baseURL = "http://192.168.43.116:9000/manage";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgroup);

        sp = getSharedPreferences("onClick",MODE_PRIVATE);

        cgroupname = (EditText) findViewById(R.id.cgroupname);
        but_create_group = (Button) findViewById(R.id.but_create_group);
        but_create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击提交按钮创建群组，下面生成群组的ID
                StringBuilder stringBuilder = new StringBuilder(baseURL+"/addGroup");
                stringBuilder.append("?");
                stringBuilder.append("userid").append("=").append(sp.getString("userId","")).append("&");
                stringBuilder.append("groupname").append("=").append(cgroupname.getText().toString()).append("&");
                stringBuilder.append("memname").append("=").append(sp.getString("userName",""));
                OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(stringBuilder.toString())
                        .get()
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String result = response.body().string();
                        if(result.equals("success")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DynamicToast toast = new DynamicToast();
                                    Toast toast1 = toast.makeSuccess(AddGroupActivity.this,result+"创建群组成功");
                                    toast1.setGravity(Gravity.TOP,0,50);
                                    toast1.show();
                                    Intent intent = new Intent(AddGroupActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DynamicToast toast = new DynamicToast();
                                    Toast toast1 = toast.makeError(AddGroupActivity.this, result+"创建任务失败!!!");
                                    toast1.setGravity(Gravity.TOP,0,50);
                                    toast1.show();
                                }
                            });
                        }
                    }
                });

            }
        });
    }
}
