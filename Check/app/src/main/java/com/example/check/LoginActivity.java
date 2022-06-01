package com.example.check;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.check.menu.MainActivity;
import com.example.check.register.CustomizePasEditText;
import com.example.check.register.ForgetPassActivity;
import com.example.check.register.RegisterActivity;
import com.example.check.register.SmsActivity;
import com.mob.tools.utils.SharePrefrenceHelper;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends Activity {
    private EditText name1;
    private EditText pass1;
    private Button but_login;
    private Button but_register;
    private TextView SmsLogin;
    private TextView but_forget;
    CustomizePasEditText pasEditText;
    private String baseURL = "http://10.0.2.2:9000";
    private SharedPreferences sp;
    private CheckBox ck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name1 = (EditText) findViewById(R.id.name1);
        ck = (CheckBox)findViewById(R.id.checkBox);
//        pass1 = (EditText) findViewById(R.id.pass1);
        //注册绑定

        pasEditText = findViewById(R.id.et_pass);
        pasEditText.setEditTextHint(getResources().getString(R.string.et_hint_pass));
        
        //获得输入内容
        final String pass = pasEditText.getText();
        but_login = (Button) findViewById(R.id.but_login);
        but_register = (Button) findViewById(R.id.but_register);
        SmsLogin = (TextView) findViewById(R.id.SmsLogin);
        but_forget = (TextView)findViewById(R.id.but_forget);

        sp = getSharedPreferences("onClick",MODE_PRIVATE);

        Again();

        but_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassActivity.class);  //(当前Activity，目标Activity)
                startActivity(intent);
            }
        });
        SmsLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SmsActivity.class);  //(当前Activity，目标Activity)
                startActivity(intent);
            }
        });
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        but_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }


    private void Again(){
        boolean ck = sp.getBoolean("ck",false);
        if (ck){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);  //(当前Activity，目标Activity)
            startActivity(intent);
        }
    }

    private void Login(){
        String pass = pasEditText.getText();
        StringBuilder stringBuilder = new StringBuilder(baseURL+"/login");
        stringBuilder.append("?");
        stringBuilder.append("username").append("=").append(name1.getText().toString()).append("&");
        stringBuilder.append("password").append("=").append(pass);
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
                if (result.equals("")){
                    Looper.prepare();
                    DynamicToast toast = new DynamicToast();
                    Toast toast1 = toast.makeError(LoginActivity.this,"用户名或密码错误，请重新输入");
                    toast1.setGravity(Gravity.TOP,0,50);
                    toast1.show();
                    Looper.loop();
                    return;
                }else {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        sp.edit().putString("userId",jsonObject.getString("userid")).apply();
                        sp.edit().putString("userName",jsonObject.getString("name")).apply();
                        sp.edit().putString("password",jsonObject.getString("password")).apply();
                        if (ck.isChecked()){
                            sp.edit().putBoolean("ck",true).apply();
                        }else {
                            sp.edit().putBoolean("ck",false).apply();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);  //(当前Activity，目标Activity)
                    startActivity(intent);
                }
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.but_login:
////                try {
//                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);  //(当前Activity，目标Activity)
//                   // intent.setClass(LoginActivity.this, Class.forName("MainActivity"));
//                    startActivity(intent);
////                } catch (ClassNotFoundException e) {
////                    // TODO Auto-generated catch block
////                    e.printStackTrace();
////                }
//            case R.id.but_register:
//                    Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
////                    //(当前Activity，目标Activity)
////                    intent.setClass(LoginActivity.this, Class.forName("RegisterActivity"));
//                    startActivity(i);
//        }
//    }
}
