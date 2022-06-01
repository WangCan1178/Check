package com.example.check.addtask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.check.R;
import com.example.check.menu.createActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddTaskZDYActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener, DatePicker.OnDateChangedListener{
    private EditText taskName,t_description; //创建的任务名称
    private RadioGroup muban;  //任务模板选择，文字识别，图片识别
    private Button but_publishZDY;
    private LinearLayout picture,keywords;
    private Context context;
    private LinearLayout llStartDate, llStartTime;
    private TextView tvStartDate, tvStartTime;
    private LinearLayout llFinishDate, llFinishTime;
    private TextView tvFinishDate, tvFinishTime;
    private int year, month, day, hour, minute;
    //在TextView上显示的字符
    private StringBuffer date, time;
    private String baseURL = "http://10.0.2.2:9000/task";
    private SharedPreferences sp;
    private  int groupid;
    private String recognition;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_zdy);
        sp = getSharedPreferences("onClick",MODE_PRIVATE);
        taskName = (EditText) findViewById(R.id.taskName);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        groupid=bundle.getInt("groupid");
        Toast.makeText(this,"群组ID:"+groupid,Toast.LENGTH_SHORT).show();
        muban  = (RadioGroup)findViewById(R.id.muban);
        muban.setOnCheckedChangeListener(this);
        recognition="0";//文字识别标为0，图片识别标为1
        t_description = (EditText) findViewById(R.id.t_description);
        context = this;
        date = new StringBuffer();
//            time = new StringBuffer();
        initView();
        initDateTime();
        picture = (LinearLayout)findViewById(R.id.picture);
        keywords = (LinearLayout)findViewById(R.id.keywords);
        but_publishZDY = (Button) findViewById(R.id.but_publishZDY);
        but_publishZDY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddTaskZDYActivity.this,tvFinishDate.getText().toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(AddTaskZDYActivity.this,tvStartDate.getText().toString(),Toast.LENGTH_SHORT).show();
//                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//                try {
//                    publishDate = sdf.parse(tvStartDate.getText().toString());
//                    endData =sdf.parse(tvFinishDate.getText().toString());
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                String formattedDate1 = output.format(publishDate);
//                String formattedDate2 = output.format(endData);
//
//                Log.d("Date format", "output date :" + formattedDate1);
//                Log.d("Date format", "output date :" + formattedDate2);
//                StringBuilder stringBuilder = new StringBuilder(baseURL+"/insertTaskJKM");
//                stringBuilder.append("?");
//                stringBuilder.append("taskname").append("=").append(taskName.getText().toString()).append("&");
//                stringBuilder.append("groupid").append("=").append(groupid).append("&");
//                stringBuilder.append("description").append("=").append(description).append("&");
//                stringBuilder.append("publishtime").append("=").append(publishDate).append("&");
//                stringBuilder.append("endtime").append("=").append(endData);
//
//                final Request request = new Request.Builder()
//                        .url(stringBuilder.toString())
//                        .get()
//                        .build();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("title",taskName.getText().toString());
                    jsonObject.put("groupid",groupid);
                    jsonObject.put("description",t_description.getText().toString());
                    jsonObject.put("publishtime",tvStartDate.getText().toString());
                    jsonObject.put("endtime",tvFinishDate.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),String.valueOf(jsonObject));
                final Request request = new Request.Builder()
                        .url(baseURL+"/insertTaskJKM")
                        .post(requestBody)
                        .build();
                OkHttpClient okHttpClient = new OkHttpClient();
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
                                    Toast toast1 = toast.makeSuccess(AddTaskZDYActivity.this,"创建任务成功");
                                    toast1.setGravity(Gravity.TOP,0,50);
                                    toast1.show();
                                    Intent intent = new Intent(AddTaskZDYActivity.this, createActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DynamicToast toast = new DynamicToast();
                                    Toast toast1 = toast.makeError(AddTaskZDYActivity.this, result+"创建任务失败!!!");
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
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        picture = (LinearLayout)findViewById(R.id.picture);
        keywords = (LinearLayout)findViewById(R.id.keywords);
        //得到用户选中的 RadioButton 对象
        RadioButton radioButton_checked= (RadioButton) group.findViewById(checkedId);
        String gender=radioButton_checked.getText().toString();
        Toast.makeText(this,gender,Toast.LENGTH_LONG).show();
        switch (checkedId){
            case R.id.mubanpic:
                keywords.setVisibility(View.GONE);
                picture.setVisibility(View.VISIBLE);
                break;
            case R.id.mubantext:
                keywords.setVisibility(View.VISIBLE);
                picture.setVisibility(View.GONE);
                break;
        }
    }
    private void initView() {
        context = this;
        date = new StringBuffer();
        llStartDate = (LinearLayout) findViewById(R.id.ll_start_date);
        tvStartDate = (TextView) findViewById(R.id.tv_start_date);
        llStartDate.setOnClickListener(this);
        llFinishDate = (LinearLayout) findViewById(R.id.ll_deadline_date);
        tvFinishDate = (TextView) findViewById(R.id.tv_deadline_date);
        llFinishDate.setOnClickListener(this);
        //        llStartTime.setOnClickListener(this);
        //        llStartTime = (LinearLayout) findViewById(R.id.ll_start_time);
//        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
//        llFinishTime = (LinearLayout) findViewById(R.id.ll_deadline_time);
//        tvFinishTime = (TextView) findViewById(R.id.tv_deadline_time);

//        llFinishTime.setOnClickListener(this);
    }

    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
//        hour = calendar.get(Calendar.HOUR);
//        minute = calendar.get(Calendar.MINUTE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_deadline_date:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) { //清除上次记录的日期
                            date.delete(0, date.length());
                        }
                        tvFinishDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month+1)).append("-").append(day).append(" 00:00:00"));
                        dialog.dismiss();
                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog1 = builder1.create();
                View dialogView1 = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker1 = (DatePicker) dialogView1.findViewById(R.id.datePicker);

                dialog1.setTitle("设置日期");
                dialog1.setView(dialogView1);
                dialog1.show();
                //初始化日期监听事件
                datePicker1.init(year, month, day, this);
                break;
            case R.id.ll_start_date:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) { //清除上次记录的日期
                            date.delete(0, date.length());
                        }
//                        tvStartDate.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month)).append("月").append(day).append("日"));
                        tvStartDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month+1)).append("-").append(day).append(" 00:00:00"));
                        dialog.dismiss();
                    }
                });
                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog2 = builder2.create();
                View dialogView2 = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker2 = (DatePicker) dialogView2.findViewById(R.id.datePicker);

                dialog2.setTitle("设置日期");
                dialog2.setView(dialogView2);
                dialog2.show();
                //初始化日期监听事件
                datePicker2.init(year, month, day, this);
                break;
//            case R.id.ll_deadline_time:
//                AlertDialog.Builder builder3 = new AlertDialog.Builder(context);
//                builder3.setPositiveButton("设置", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (time.length() > 0) { //清除上次记录的日期
//                            time.delete(0, time.length());
//                        }
//                        tvFinishTime.setText(time.append(String.valueOf(hour)).append("时").append(String.valueOf(minute)).append("分"));
//                        dialog.dismiss();
//                    }
//                });
//                builder3.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog dialog3 = builder3.create();
//                View dialogView3 = View.inflate(context, R.layout.dialog_time, null);
//                TimePicker timePicker3 = (TimePicker) dialogView3.findViewById(R.id.timePicker);
//                timePicker3.setCurrentHour(hour);
//                timePicker3.setCurrentMinute(minute);
//                timePicker3.setIs24HourView(true); //设置24小时制
//                timePicker3.setOnTimeChangedListener(this);
//                dialog3.setTitle("设置时间");
//                dialog3.setView(dialogView3);
//                dialog3.show();
//                break;
//            case R.id.ll_start_time:
//                AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
//                builder4.setPositiveButton("设置", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (time.length() > 0) { //清除上次记录的日期
//                            time.delete(0, time.length());
//                        }
//                        tvStartTime.setText(time.append(String.valueOf(hour)).append("时").append(String.valueOf(minute)).append("分"));
//                        dialog.dismiss();
//                    }
//                });
//                builder4.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog dialog4 = builder4.create();
//                View dialogView4 = View.inflate(context, R.layout.dialog_time, null);
//                TimePicker timePicker4 = (TimePicker) dialogView4.findViewById(R.id.timePicker);
//                timePicker4.setCurrentHour(hour);
//                timePicker4.setCurrentMinute(minute);
//                timePicker4.setIs24HourView(true); //设置24小时制
//                timePicker4.setOnTimeChangedListener(this);
//                dialog4.setTitle("设置时间");
//                dialog4.setView(dialogView4);
//                dialog4.show();
//                break;
        }
    }


    /**
     * 日期改变的监听事件
     *
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

//    /**
//     * 时间改变的监听事件
//     *
//     * @param view
//     * @param hourOfDay
//     * @param minute
//     */
//    @Override
//    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//        this.hour = hourOfDay;
//        this.minute = minute;
//    }
}
