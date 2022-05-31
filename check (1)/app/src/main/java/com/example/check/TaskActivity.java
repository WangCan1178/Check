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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskActivity extends Activity {

//    private ImageView select;
    private ListView listView;
    private String baseURL = "http://10.0.2.2:9000/pic";
    private List<Picture> pics;
    private PictureAdapter adapter;
    private Handler handler;
//    private float p1=0f;
//    private float p2=0f;
//    private float p3=0f;
//private int pass=0;
//    private int    unpuss=0;
//    private int unfinish=0;

    private PieChartView	id_pie_chart;

    private TextView		id_tv_1, id_tv_2, id_tv_3;

    private List<PieModel>	pieModelList	= new ArrayList<>();

    private List<Integer>	colorList		= new ArrayList<>();
    private int taskid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        taskid=bundle.getInt("taskid");//从上一个页面接受的任务ID
        Toast.makeText(this,"任务ID:"+taskid,Toast.LENGTH_SHORT).show();

        listView = (ListView)findViewById(R.id.tasklist) ;
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
                            Picture picture = new Picture();
//                            picture.setPhoto(jsonArray.getJSONObject(i).getString("url"));
                            picture.setUserid(jsonArray.getJSONObject(i).getString("userid"));
//                            Log.d("userid",jsonArray.getJSONObject(i).getInt("userid"));
                            picture.setPicid(jsonArray.getJSONObject(i).getInt("picid"));
                            picture.setResult(jsonArray.getJSONObject(i).getInt("result"));
                            pics.add(picture);
                        }

                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new PictureAdapter(TaskActivity.this,R.layout.picture_item,pics);
                                listView.setAdapter(adapter);

//                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                            Intent intent = new Intent(TaskActivity.this, com.example.check.SubmitTaskActivity.class);
//                                            Bundle bundle1 = new Bundle();
//                                            bundle1.putInt("taskid", pics.get(i).getTaskid());
//                                            intent.putExtras(bundle1);
//                                            startActivity(intent);
//                                    }
//                                });
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
//        unpuss=unpass();
//        pass= pass();
//        unfinish=unfinish();
//        Toast.makeText(this,"pass："+pass,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,"unpass："+unpuss,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,"unfinish："+unfinish,Toast.LENGTH_SHORT).show();
        id_tv_1 = findViewById(R.id.id_tv_1);
        id_tv_2 = findViewById(R.id.id_tv_2);
        id_tv_3 = findViewById(R.id.id_tv_3);
//        id_tv_4 = findViewById(R.id.id_tv_4);
        id_pie_chart = findViewById(R.id.id_pie_chart);

        ColorRandom colorRandom = new ColorRandom(10);
        for (int i = 0; i < 3; i++) {
            int colors = (int) colorRandom.getColors().get(i);
            // 这个是算百分比的，我直接设定了固定的数字
            switch (i){
                case 0:
//                    p1=(float)(pass/(unfinish+unpuss+pass)f);
//                    DecimalFormat var = new DecimalFormat("#.###");
//                    Toast.makeText(this,"p1"+var.format(p1),Toast.LENGTH_SHORT).show();
                    pieModelList.add(new PieModel(colors, 0.5f));
                    break;
                case 1:
//                    p2=(float)(unpuss/(unfinish+unpuss+pass));
//                    DecimalFormat var2 = new DecimalFormat("#.###");
//                    Toast.makeText(this,"p2 "+var2.format(p2),Toast.LENGTH_SHORT).show();
                    pieModelList.add(new PieModel(colors, 0.3f));
                    break;
                case 2:
//                    p3 = (float)(unfinish/(unfinish+unpuss+pass));
//                    DecimalFormat var3 = new DecimalFormat("#.###");
//                    Toast.makeText(this," p3"+var3.format(p3),Toast.LENGTH_SHORT).show();
                    pieModelList.add(new PieModel(colors, 0.2f));
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
//                pass = pass();
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
                //onCreate(null);
//                unpuss = unpass();
                unpass();
//                Toast.makeText(TaskActivity.this,"已经通过的任务",Toast.LENGTH_LONG).show();
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
//                unfinish = unfinish();
                unfinish();
//                onCreate(null);
//                Toast.makeText(TaskActivity.this,"没有完成的任务",Toast.LENGTH_LONG).show();
            }
        });

//        id_tv_4.setOnClickListener(new View.OnClickListener() {
//
//            @Override public void onClick(View v) {
//                if (pieModelList.get(3).selected) {
//                    pieModelList.get(3).selected = false;
//                } else {
//                    pieModelList.get(3).selected = true;
//                }
//                id_pie_chart.setData(pieModelList);
//                id_pie_chart.invalidate();
//            }
//        });

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
                        for(int i=0;i<jsonArray.length();i++){
                            Picture picture = new Picture();
//                            picture.setPhoto(jsonArray.getJSONObject(i).getString("url"));
                            picture.setUserid(jsonArray.getJSONObject(i).getString("userid"));
//                            Log.d("userid",jsonArray.getJSONObject(i).getInt("userid"));
                            picture.setPicid(jsonArray.getJSONObject(i).getInt("picid"));
                            picture.setResult(jsonArray.getJSONObject(i).getInt("result"));
                            pics.add(picture);
                        }
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new PictureAdapter(TaskActivity.this,R.layout.picture_item,pics);
                                listView.setAdapter(adapter);
//                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                            Intent intent = new Intent(TaskActivity.this, com.example.check.SubmitTaskActivity.class);
//                                            Bundle bundle1 = new Bundle();
//                                            bundle1.putInt("taskid", pics.get(i).getTaskid());
//                                            intent.putExtras(bundle1);
//                                            startActivity(intent);
//                                    }
//                                });
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
//        onCreate(null);
//        Toast.makeText(TaskActivity.this,"没有通过的任务",Toast.LENGTH_LONG).show();
//        return listView.getCount();
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
                        for(int i=0;i<jsonArray.length();i++){
                            Picture picture = new Picture();
//                            picture.setPhoto(jsonArray.getJSONObject(i).getString("url"));
                            picture.setUserid(jsonArray.getJSONObject(i).getString("userid"));
//                            Log.d("userid",jsonArray.getJSONObject(i).getInt("userid"));
                            picture.setPicid(jsonArray.getJSONObject(i).getInt("picid"));
                            picture.setResult(jsonArray.getJSONObject(i).getInt("result"));
                            pics.add(picture);
                        }
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new PictureAdapter(TaskActivity.this,R.layout.picture_item,pics);
                                listView.setAdapter(adapter);
//                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                            Intent intent = new Intent(TaskActivity.this, com.example.check.SubmitTaskActivity.class);
//                                            Bundle bundle1 = new Bundle();
//                                            bundle1.putInt("taskid", pics.get(i).getTaskid());
//                                            intent.putExtras(bundle1);
//                                            startActivity(intent);
//                                    }
//                                });
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
//        onCreate(null);
//        Toast.makeText(TaskActivity.this,"没有完成的任务",Toast.LENGTH_LONG).show();
//        return listView.getCount();
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
                        for(int i=0;i<jsonArray.length();i++){
                            Picture picture = new Picture();
//                            picture.setPhoto(jsonArray.getJSONObject(i).getString("url"));
                            picture.setUserid(jsonArray.getJSONObject(i).getString("userid"));
//                            Log.d("userid",jsonArray.getJSONObject(i).getInt("userid"));
                            picture.setPicid(jsonArray.getJSONObject(i).getInt("picid"));
                            picture.setResult(jsonArray.getJSONObject(i).getInt("result"));
                            pics.add(picture);
                        }
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new PictureAdapter(TaskActivity.this,R.layout.picture_item,pics);
                                listView.setAdapter(adapter);
//                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                            Intent intent = new Intent(TaskActivity.this, com.example.check.SubmitTaskActivity.class);
//                                            Bundle bundle1 = new Bundle();
//                                            bundle1.putInt("taskid", pics.get(i).getTaskid());
//                                            intent.putExtras(bundle1);
//                                            startActivity(intent);
//                                    }
//                                });
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
//        onCreate(null);
//        Toast.makeText(TaskActivity.this,"已经完成的任务",Toast.LENGTH_LONG).show();
//        return listView.getCount();
    }
//   public void showPopupMenu(final View view) {
//
//        // View当前PopupMenu显示的相对View的位置
//        PopupMenu popupMenu = new PopupMenu(TaskActivity.this,view);
//        // menu布局
//        popupMenu.getMenuInflater().inflate(R.menu.task_select, popupMenu.getMenu());
//        // menu的item点击事件
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.unpass:  //未通过
//                        onCreate(null);
//                        Toast.makeText(TaskActivity.this,"没有通过的任务",Toast.LENGTH_LONG).show();
////                        Toast.makeText(TaskActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
////                        Intent intentJKM = new Intent(TaskActivity.this,com.example.check.TaskActivity.class);
////                        startActivity(intentJKM);
//                        return true;
////                        break;
//                    case R.id.pass:  //已通过
//                        onCreate(null);
////                        Toast.makeText(TaskActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(TaskActivity.this,"已经通过的任务",Toast.LENGTH_LONG).show();
////                        Intent intentXCM = new Intent(TaskActivity.this);
////                        startActivity(intentXCM);
//                        return true;
////                        break;
//                    case R.id.unfinish:  // 未完成
//                        onCreate(null);
//                        Toast.makeText(TaskActivity.this,"没有完成的任务",Toast.LENGTH_LONG).show();
////                        Toast.makeText(TaskActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
////                        Intent intentDXX = new Intent(TaskActivity.this,com.example.check.TaskActivity.class);
////                        startActivity(intentDXX);
//                        return true;
////                        break;
//                    default:
//                        onCreate(null);
//                        return false;
////                        break;
//                }
////                Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
////                return false;
//            }
//        });
//        // PopupMenu关闭事件
//        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//            @Override
//            public void onDismiss(PopupMenu menu) {
//                Toast.makeText(TaskActivity.this, "关闭PopupMenu", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        popupMenu.show();
//
//    }
}
