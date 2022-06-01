package com.example.check;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.check.menu.MainActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.RequestBody.create;

public class JoinGroupActivity extends AppCompatActivity {
    private EditText jgroupid;
    private EditText jgroupname;
    private Button but_join_group;
    private String baseURL = "http://10.0.2.2:9000/add";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        sp = getSharedPreferences("onClick",MODE_PRIVATE);

        jgroupid = (EditText) findViewById(R.id.jgroupid);
        jgroupname = (EditText)findViewById(R.id.jgroupname);
        but_join_group = (Button) findViewById(R.id.but_join_group);

        but_join_group.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                //点击加入群组，该用户加入群组
                OkHttpClient okHttpClient = new OkHttpClient();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userid",sp.getString("userId",""));
                    jsonObject.put("groupid",jgroupid.getText().toString());
                    jsonObject.put("memname",jgroupname.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),String.valueOf(jsonObject));
                final Request request = new Request.Builder()
                        .url(baseURL+"/addGroup")
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DynamicToast toast = new DynamicToast();
                                    Toast toast1 = toast.makeSuccess(JoinGroupActivity.this,"加入群组成功");
                                    toast1.setGravity(Gravity.TOP,0,50);
                                    toast1.show();
                                    Intent intent = new Intent(JoinGroupActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });

            }
        });
    }
}
