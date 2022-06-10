package com.example.check.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.check.JoinGroupActivity;
import com.example.check.LoginActivity;
import com.example.check.menu.MainActivity;
import com.example.check.R;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register3Activity extends AppCompatActivity{
    private EditText nameText;
    private Button but_next3;
    CustomizePasEditText passEditText;
    CustomizePasEditText passEditText2;
    String phone;
    private String baseURL = "http://192.168.43.116:9000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        but_next3 = (Button)findViewById(R.id.but_next3);
        //注册绑定

        nameText = (EditText)findViewById(R.id.et_name);
        passEditText = findViewById(R.id.et_pass);
        passEditText.setEditTextHint(getResources().getString(R.string.et_hint_pass));
        passEditText2 = findViewById(R.id.et_pass2);
        passEditText2.setEditTextHint(getResources().getString(R.string.et_hint_pass));

        //获得输入内容
        phone = getIntent().getStringExtra("phone");
        but_next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String pass = passEditText.getText().toString();
                String pass2 = passEditText2.getText().toString();
                if (!name.equals("")){
                    if (isPassword(pass)){
                        if(pass.equals(pass2)){
                            OkHttpClient okHttpClient = new OkHttpClient();
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("userid",phone);
                                jsonObject.put("password",pass);
                                jsonObject.put("name",name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),String.valueOf(jsonObject));
                            final Request request = new Request.Builder()
                                    .url(baseURL+"/register")
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
                                                Toast toast1 = toast.makeSuccess(Register3Activity.this,"注册成功");
                                                toast1.setGravity(Gravity.TOP,0,50);
                                                toast1.show();
                                                Intent i = new Intent(Register3Activity.this , LoginActivity.class);
                                                startActivity(i);
                                            }
                                        });
                                    }
                                }
                            });
                        }else {
                            DynamicToast toast = new DynamicToast();
                            Toast toast1 = toast.makeError(Register3Activity.this,"两次密码输入不相同，请重新输入");
                            toast1.setGravity(Gravity.TOP,0,50);
                            toast1.show();
                        }
                    }else {
                        DynamicToast toast = new DynamicToast();
                        Toast toast1 = toast.makeError(Register3Activity.this,"密码格式不正确，请重新输入");
                        toast1.setGravity(Gravity.TOP,0,50);
                        toast1.show();
                    }
                }else {
                    DynamicToast toast = new DynamicToast();
                    Toast toast1 = toast.makeError(Register3Activity.this,"昵称不能为空，请重新输入");
                    toast1.setGravity(Gravity.TOP,0,50);
                    toast1.show();
                }

            }
        });
    }
//    @Override
//    public void onClick(View v){
//        Intent i = new Intent(Register3Activity.this ,MainActivity.class);
//        startActivity(i);
//    }
public static boolean isPassword(String value) {
    if (value != null) {
        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
    return false;
}
    }


