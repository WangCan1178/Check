package com.example.check.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.check.LoginActivity;
import com.example.check.R;
import com.example.check.menu.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class SmsActivity extends AppCompatActivity {
    private EditText EditPhone;  //短线验证的手机号码
    private EditText EditCode;  //短信登陆的验证码
    private Button but_SmsLogin;  //点击该按钮短信登陆成功进入主页面
    private TextView PassLogin; //点击该按钮进入密码登陆界面
    private Button but_SmsRegister;  //点击该按钮进入注册界面
    private TextView but_getSMS; //获取验证码
    private String phoneNum;
    private String code;
    private EventHandler eh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        EditPhone = (EditText) findViewById(R.id.phone2);
        EditCode = (EditText) findViewById(R.id.Smsyanzhengma);
        but_SmsLogin = (Button) findViewById(R.id.but_Smslogin);
        PassLogin = (TextView) findViewById(R.id.PassLogin);
        but_SmsRegister = (Button) findViewById(R.id.but_SmsRegister);
        but_getSMS = (TextView) findViewById(R.id.but_getSMS);
        but_SmsRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SmsActivity.this, RegisterActivity.class);  //(当前Activity，目标Activity)
                startActivity(intent);
            }
        });
        PassLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SmsActivity.this, LoginActivity.class);  //(当前Activity，目标Activity)
                startActivity(intent);
            }
        });

//        but_SmsLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SmsActivity.this,MainActivity.class);  //(当前Activity，目标Activity)
//                startActivity(intent);
//            }
//        });
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SmsActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                //如果需要跳转可在这里进行跳转页面
                                //登录成功跳转到软件首页页面
                                Intent intent=new Intent(SmsActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SmsActivity.this,"语音验证发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SmsActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        Log.i("test","test");
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    throwable.printStackTrace();
                    Log.i("1234",throwable.toString());
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SmsActivity.this,des,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        //注册一个事件回调监听，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eh);
        but_getSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum = EditPhone.getText().toString();
                if(!phoneNum.isEmpty()){
                    if(Utils.checkTel(phoneNum)){ //利用正则表达式获取检验手机号
                        // 获取验证码
                        SMSSDK.getVerificationCode("86", phoneNum);
                    }else{
                        Toast.makeText(getApplicationContext(),"请输入有效的手机号",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"请输入手机号",Toast.LENGTH_LONG).show();
                    return;
                }
                phoneNum = EditPhone.getText().toString();
            }
        });
        but_SmsLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = EditCode.getText().toString();
                if(!code.isEmpty()){
                    //提交验证码
                    SMSSDK.submitVerificationCode("86", phoneNum, code);
                }else{
                    Toast.makeText(getApplicationContext(),"请输入验证码",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
    // 使用完EventHandler需注销，否则可能出现内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
}

//https://blog.csdn.net/weixin_47766946/article/details/117415326?spm=1001.2101.3001.6650.5&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-5.pc_relevant_aa&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-5.pc_relevant_aa&utm_relevant_index=10