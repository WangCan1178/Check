package com.example.check.register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.check.LoginActivity;
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

public class RevisePassActivity extends AppCompatActivity  implements View.OnClickListener {
    CustomizePasEditText et_password_first,et_password_second;
    private EditText et_code;
    private TextView but_getCode;
    private String code; // 验证码
    private String phone;
    private Button but_confirm;
    private String baseURL = "http://192.168.43.116:9000";//"http://10.0.2.2:9000"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_pass);
        //注册绑定

        et_password_first = findViewById(R.id.et_pass_first2);
        et_password_first.setEditTextHint(getResources().getString(R.string.et_hint_pass));
        et_password_second = findViewById(R.id.et_pass_second2);
        et_password_second.setEditTextHint(getResources().getString(R.string.et_hint_pass));
        et_code = (EditText)findViewById(R.id.et_code2);
        but_getCode = (TextView) findViewById(R.id.but_getCode);
        but_getCode.setOnClickListener(this);
        but_confirm = (Button)findViewById(R.id.but_confirm);
        but_confirm.setOnClickListener(this);
//        findViewById(R.id.btn_confirm).setOnClickListener(this);
//        // 从前一个页面获取要修改密码的手机号码
//        mPhone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.but_getCode) { // 点击了“获取验证码”按钮
            //Toast.makeText(this,phone,Toast.LENGTH_SHORT).show();
            // 生成六位随机数字的验证码
            code = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
            // 弹出提醒对话框，提示用户六位验证码数字
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请记住验证码");
            builder.setMessage("本次验证码是" + code + "，请输入验证码");
            builder.setPositiveButton("好的", null);
            AlertDialog alert = builder.create();
            alert.show();
        } else if (v.getId() == R.id.but_confirm) { // 点击了“确定”按钮
            String password_first = et_password_first.getText();
            String password_second = et_password_second.getText();
            if (!et_code.getText().toString().equals(code)) {
                DynamicToast toast = new DynamicToast();
                Toast toast1 = toast.makeError(RevisePassActivity.this,"验证码错误，请重新输入");
                toast1.setGravity(Gravity.TOP,0,50);
                toast1.show();
            }
            else if (!isPassword(password_first)) {
                DynamicToast toast = new DynamicToast();
                Toast toast1 = toast.makeError(RevisePassActivity.this,"密码格式不正确，请重新输入");
                toast1.setGravity(Gravity.TOP,0,50);
                toast1.show();
                return;
            }
            else if (!password_first.equals(password_second)) {
                DynamicToast toast = new DynamicToast();
                Toast toast1 = toast.makeError(RevisePassActivity.this,"两次密码输入不相同，请重新输入");
                toast1.setGravity(Gravity.TOP,0,50);
                toast1.show();
                return;
            }
            else {
                // 把修改好的新密码返回给前一个页面
                SharedPreferences sp = getSharedPreferences("onClick",MODE_PRIVATE);
                OkHttpClient okHttpClient = new OkHttpClient();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userid",sp.getString("userId",""));
                    jsonObject.put("password",password_first);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),String.valueOf(jsonObject));
                final Request request = new Request.Builder()
                        .url(baseURL+"/alterpsw")
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
                                    Toast toast1 = toast.makeSuccess(RevisePassActivity.this,"修改成功，请重新登录");
                                    toast1.setGravity(Gravity.TOP,0,50);
                                    toast1.show();
                                    Intent i = new Intent(RevisePassActivity.this , LoginActivity.class);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    public static boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }

    public static boolean isPassword(String value) {
        if (value != null) {
            Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }
}
