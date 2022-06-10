package com.example.check;

import androidx.appcompat.app.AppCompatActivity;

import com.example.check.Adapter.PictureAdapter;
import com.example.check.Adapter.TaskApater;
import com.example.check.Entity.Isend;
import com.example.check.Entity.Picture;
import com.example.check.Entity.Task;
import com.example.check.view.PieModel;
import com.example.check.view.PieChartView;
import com.example.check.view.ColorRandom;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.example.check.menu.createActivity;

import android.app.Activity;
import android.app.GameManager;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskActivity extends Activity {

//    private ImageView select;

    private ListView listView;
    private String baseURL = "http://192.168.43.116:9000/pic";
    //    private List<Picture> pics;
    private List<Isend> pics;
    private PictureAdapter adapter;
    private Handler handler;
    private Button but_return,but_message;
    private float p1=0.0f;
    private float p2=0.0f;
    private float p3=0.0f;
    private int pass=0;
    private int all=0;
    private int unpass=0;
    private int unfinish=0;

    private PieChartView	id_pie_chart;

    private TextView		id_tv_1, id_tv_2, id_tv_3;

    private List<PieModel>	pieModelList	= new ArrayList<>();

    private List<Integer>	colorList		= new ArrayList<>();
    private int taskid,groupid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        taskid=bundle.getInt("taskid");//从上一个页面接受的任务ID
        groupid=bundle.getInt("groupid");
//        Toast.makeText(this,"任务ID:"+taskid,Toast.LENGTH_SHORT).show();
        but_return = (Button)findViewById(R.id.but_return);
        but_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(TaskActivity.this,createActivity.class);
                intent.putExtra("groupid",groupid);
                startActivity(intent);
            }
        });
        but_message = (Button)findViewById(R.id.but_message);
        but_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder("http://192.168.43.116:9000/sendMess");
                stringBuilder.append("?");
                stringBuilder.append("tid").append("=").append(taskid);
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
//                        if (result.equals("1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DynamicToast toast = new DynamicToast();
                                Toast toast1 = toast.makeSuccess(TaskActivity.this, "消息发送成功");
                                toast1.setGravity(Gravity.TOP, 0, 50);
                                toast1.show();
                            }
                        });
//                        }
                    }
                });
            }
        });
        listView = (ListView)findViewById(R.id.tasklist) ;
        unfinishCount();//计算没有完成的数量
        unpassCount();//计算没有通过的数量
        passCount();//计算已经通过的数量

        handler = new Handler();
        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getPic");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(taskid);
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
                JSONArray jsonArray = null;
                pics = new ArrayList<>();
                if (result.equals("")){
                    Looper.prepare();
                    DynamicToast toast = new DynamicToast();
                    Toast toast1 = toast.makeError(TaskActivity.this,"出错啦");
                    toast1.setGravity(Gravity.TOP,0,50);
                    toast1.show();
                    Looper.loop();
                    return;
                }else {
                    try {
                        jsonArray = new JSONArray(result);
                        for(int i=0;i<jsonArray.length();i++){
                            Isend picture = new Isend();
//                            Picture picture = new Picture();
//                            picture.setUserid(jsonArray.getJSONObject(i).getString("userid"));
                            picture.setMemname(jsonArray.getJSONObject(i).getString("memname"));
                            picture.setPicid(jsonArray.getJSONObject(i).getInt("picid"));
                            picture.setResult(jsonArray.getJSONObject(i).getString("result"));
                            pics.add(picture);
                        }
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new PictureAdapter(TaskActivity.this,R.layout.picture_item,pics);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        if (pics.get(i).getResult().equals("2")){
                                            DynamicToast toast = new DynamicToast();
                                            Toast toast1 = toast.makeError(TaskActivity.this,"该用户还未填写任务，无法查看，请稍后再试");
                                            toast1.setGravity(Gravity.TOP,0,50);
                                            toast1.show();
                                        }else {
                                            Intent intent1 = new Intent(TaskActivity.this,PictureActivity.class);
                                            intent1.putExtra("picid",pics.get(i).getPicid());
                                            startActivity(intent1);
                                            finish();
                                        }
                                    }
                                });
                            }
                        };
                        new Thread(){
                            public void run(){
                                handler.post(runnable);
                            }
                        }.start();
                        all=jsonArray.length();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        while ((all==0)) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("all:"+all);
//        System.out.println("unfinish:"+unfinish);
//        System.out.println("unpass:"+unpass);
//        System.out.println("pass:"+pass);
        id_tv_1 = findViewById(R.id.id_tv_1);
        id_tv_2 = findViewById(R.id.id_tv_2);
        id_tv_3 = findViewById(R.id.id_tv_3);
        id_pie_chart = findViewById(R.id.id_pie_chart);

        p1=(float)pass/(float)all;
        p2=(float)unpass/(float)all;
        p3 = (float)unfinish/(float)all;
//      System.out.println("p1::"+(float)pass/(float)all);
//      System.out.println("p2::"+unpass/all);
//      System.out.println("p3::"+unfinish/all);
//        System.out.println("p1:"+p1+"    p2:"+p2+"   p3:"+p3);
        ColorRandom colorRandom = new ColorRandom(3);
        for (int i = 0; i < 3; i++) {
            int colors = (int) colorRandom.getColors().get(i);
            switch (i){
                case 0:
                    pieModelList.add(new PieModel(colors, p1));
                    break;
                case 1:
                    pieModelList.add(new PieModel(colors, p2));
                    break;
                case 2:
                    pieModelList.add(new PieModel(colors, p3));
                    break;
            }
        }
        id_pie_chart.setData(pieModelList);
        id_pie_chart.startAnima();

        id_tv_1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (pieModelList.get(0).selected) {
                    pieModelList.get(0).selected = false;
                } else {
                    pieModelList.get(1).selected = false;
                    pieModelList.get(2).selected = false;
                    pieModelList.get(0).selected = true;
                }
                id_pie_chart.setData(pieModelList);
                id_pie_chart.invalidate();
                pass();
            }
        });

        id_tv_2.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                if (pieModelList.get(1).selected) {
                    pieModelList.get(1).selected = false;
                } else {
                    pieModelList.get(0).selected = false;
                    pieModelList.get(2).selected = false;
                    pieModelList.get(1).selected = true;
                }
                id_pie_chart.setData(pieModelList);
                id_pie_chart.invalidate();
                unpass();
            }
        });

        id_tv_3.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                if (pieModelList.get(2).selected) {
                    pieModelList.get(2).selected = false;
                } else {
                    pieModelList.get(0).selected = false;
                    pieModelList.get(1).selected = false;
                    pieModelList.get(2).selected = true;
                }
                id_pie_chart.setData(pieModelList);
                id_pie_chart.invalidate();
                unfinish();
            }
        });
    }

    protected void unpass(){
        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getPicUnPass");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(taskid);
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
                JSONArray jsonArray = null;
                pics = new ArrayList<>();
                if (result.equals("")){
                    Looper.prepare();
                    DynamicToast toast = new DynamicToast();
                    Toast toast1 = toast.makeError(TaskActivity.this,"出错啦");
                    toast1.setGravity(Gravity.TOP,0,50);
                    toast1.show();
                    Looper.loop();
                    return;
                }else {
                    try {
                        jsonArray = new JSONArray(result);
                        unpass=jsonArray.length();
                        for(int i=0;i<jsonArray.length();i++){
//                            Picture picture = new Picture();
                            Isend picture = new Isend();
//                            picture.setUserid(jsonArray.getJSONObject(i).getString("userid"));
                            picture.setMemname(jsonArray.getJSONObject(i).getString("memname"));
                            picture.setPicid(jsonArray.getJSONObject(i).getInt("picid"));
                            picture.setResult(jsonArray.getJSONObject(i).getString("result"));
                            pics.add(picture);
                        }
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new PictureAdapter(TaskActivity.this,R.layout.picture_item,pics);
                                listView.setAdapter(adapter);
                            }
                        };
                        new Thread(){
                            public void run(){
                                handler.post(runnable);
                            }
                        }.start();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected void unfinish(){
        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getPicUnFinish");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(taskid);
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
                JSONArray jsonArray = null;
                pics = new ArrayList<>();
                if (result.equals("")){
                    Looper.prepare();
                    DynamicToast toast = new DynamicToast();
                    Toast toast1 = toast.makeError(TaskActivity.this,"出错啦");
                    toast1.setGravity(Gravity.TOP,0,50);
                    toast1.show();
                    Looper.loop();
                    return;
                }else {
                    try {
                        jsonArray = new JSONArray(result);
                        unfinish=jsonArray.length();
                        for(int i=0;i<jsonArray.length();i++){
//                            Picture picture = new Picture();
                            Isend picture = new Isend();
//                            picture.setUserid(jsonArray.getJSONObject(i).getString("userid"));
                            picture.setMemname(jsonArray.getJSONObject(i).getString("memname"));
                            picture.setPicid(jsonArray.getJSONObject(i).getInt("picid"));
                            picture.setResult(jsonArray.getJSONObject(i).getString("result"));
                            pics.add(picture);
                        }
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new PictureAdapter(TaskActivity.this,R.layout.picture_item,pics);
                                listView.setAdapter(adapter);
                            }
                        };
                        new Thread(){
                            public void run(){
                                handler.post(runnable);
                            }
                        }.start();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected void pass(){
        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getPicPass");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(taskid);
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
                JSONArray jsonArray = null;
                pics = new ArrayList<>();
                if (result.equals("")){
                    Looper.prepare();
                    DynamicToast toast = new DynamicToast();
                    Toast toast1 = toast.makeError(TaskActivity.this,"出错啦");
                    toast1.setGravity(Gravity.TOP,0,50);
                    toast1.show();
                    Looper.loop();
                    return;
                }else {
                    try {
                        jsonArray = new JSONArray(result);
                        pass=jsonArray.length();
                        for(int i=0;i<jsonArray.length();i++){
//                            Picture picture = new Picture();
                            Isend picture = new Isend();
//                            picture.setUserid(jsonArray.getJSONObject(i).getString("userid"));
                            picture.setMemname(jsonArray.getJSONObject(i).getString("memname"));
                            picture.setPicid(jsonArray.getJSONObject(i).getInt("picid"));
                            picture.setResult(jsonArray.getJSONObject(i).getString("result"));
                            pics.add(picture);
                        }
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new PictureAdapter(TaskActivity.this,R.layout.picture_item,pics);
                                listView.setAdapter(adapter);
                            }
                        };
                        new Thread(){
                            public void run(){
                                handler.post(runnable);
                            }
                        }.start();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected void unfinishCount(){
        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getPicUnFinish");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(taskid);
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
                JSONArray jsonArray = null;
                if (result.equals("")){
                    Looper.prepare();
                    DynamicToast toast = new DynamicToast();
                    Toast toast1 = toast.makeError(TaskActivity.this,"出错啦");
                    toast1.setGravity(Gravity.TOP,0,50);
                    toast1.show();
                    Looper.loop();
                    return;
                }else {
                    try {
                        jsonArray = new JSONArray(result);
                        unfinish=jsonArray.length();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected void unpassCount(){
        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getPicUnPass");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(taskid);
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
                JSONArray jsonArray = null;
                if (result.equals("")){
                    Looper.prepare();
                    DynamicToast toast = new DynamicToast();
                    Toast toast1 = toast.makeError(TaskActivity.this,"出错啦");
                    toast1.setGravity(Gravity.TOP,0,50);
                    toast1.show();
                    Looper.loop();
                    return;
                }else {
                    try {
                        jsonArray = new JSONArray(result);
                        unpass=jsonArray.length();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected void passCount(){
        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getPicPass");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(taskid);
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
                JSONArray jsonArray = null;
                if (result.equals("")){
                    Looper.prepare();
                    DynamicToast toast = new DynamicToast();
                    Toast toast1 = toast.makeError(TaskActivity.this,"出错啦");
                    toast1.setGravity(Gravity.TOP,0,50);
                    toast1.show();
                    Looper.loop();
                    return;
                }else {
                    try {
                        jsonArray = new JSONArray(result);
                        pass=jsonArray.length();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
