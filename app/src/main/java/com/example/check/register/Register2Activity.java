package com.example.check.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.check.R;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_code;  //手机短信发送的验证码
    private TextView but_getcode;
    private Button but_next2;  //下一步按钮
    private TextView tv_phone;
    private String phone;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        et_code = (EditText) findViewById(R.id.code);
        but_getcode = (TextView) findViewById(R.id.but_getCode);
        but_getcode.setOnClickListener(this);
        but_next2 = (Button) findViewById(R.id.but_next2);
        but_next2.setOnClickListener(this);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        phone = "请输入"+getIntent().getStringExtra("phone")+"获取的验证码";
        tv_phone.setText(phone);

    }
    @Override
    public void onClick(View v){
        phone = getIntent().getStringExtra("phone");
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
        }else if(v.getId() == R.id.but_next2){
            if (code.equals(et_code.getText().toString())){
                DynamicToast toast = new DynamicToast();
                Toast toast1 = toast.makeSuccess(Register2Activity.this,"验证码正确");
                toast1.setGravity(Gravity.TOP,0,50);
                toast1.show();
                Intent i = new Intent(Register2Activity.this , Register3Activity.class);
                i.putExtra("phone", phone);
                startActivity(i);
            }else {
                DynamicToast toast = new DynamicToast();
                Toast toast1 = toast.makeError(Register2Activity.this,"验证码错误，请重新输入");
                toast1.setGravity(Gravity.TOP,0,50);
                toast1.show();
            }

        }
    }
//    @Override
//    public void onClick(View v){
//        Intent i = new Intent(Register2Activity.this ,Register3Activity.class);
//        startActivity(i);
//    }
}
