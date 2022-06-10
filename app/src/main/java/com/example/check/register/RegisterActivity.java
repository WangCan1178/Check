package com.example.check.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.check.JoinGroupActivity;
import com.example.check.LoginActivity;
import com.example.check.menu.MainActivity;
import com.example.check.R;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends Activity {
    public static final String TAG = MainActivity.class.getName();
    private Button but_next2;
    private ImageView showCode;//验证码图片
    private EditText inCode;//获取输入的验证码
    private EditText phone1;//获取手机号
    private String realCode;//产生的验证码
    private Button but_setCode;

    private String baseURL = "http://192.168.43.116:9000";
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        phone1 =(EditText)findViewById(R.id.phone1);
        inCode = (EditText)findViewById(R.id.inCode);
        showCode = (ImageView)findViewById(R.id.showCode);
        but_setCode = (Button)findViewById(R.id.jiaoyan);
        showCode.setImageBitmap(Code.getInstance().createBitmap());//将验证码用图片的形式显示出来
        realCode = Code.getInstance().getCode().toLowerCase();
        showCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                Log.v(TAG,"realCode"+realCode);
            }
        });
        but_setCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneCode = inCode.getText().toString().toLowerCase();
                //String msg = "生成的验证码："+realCode+"输入的验证码:"+phoneCode;
                //Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_LONG).show();
                if (phoneCode.equals(realCode)) {
                    phone=phone1.getText().toString();
                    if (isTelPhoneNumber(phone)){
                        StringBuilder stringBuilder = new StringBuilder(baseURL+"/phone");
                        stringBuilder.append("?");
                        stringBuilder.append("phone").append("=").append(phone);
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
                                    Intent intent = new Intent(RegisterActivity.this , Register2Activity.class);
                                    intent.putExtra("phone", phone);
                                    startActivity(intent);
                                }else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DynamicToast toast = new DynamicToast();
                                            Toast toast1 = toast.makeError(RegisterActivity.this,"该账号已经注册，请返回登录");
                                            toast1.setGravity(Gravity.TOP,0,50);
                                            toast1.show();
                                            Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }
                        });
                    }else {
                        DynamicToast toast = new DynamicToast();
                        Toast toast1 = toast.makeError(RegisterActivity.this,"手机号格式不正确，请重新输入");
                        toast1.setGravity(Gravity.TOP,0,50);
                        toast1.show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, phoneCode + "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }
//    @Override
//    public void onClick(View v){
//        Intent i = new Intent(RegisterActivity.this ,Register2Activity.class);
//        startActivity(i);
//    }
}

