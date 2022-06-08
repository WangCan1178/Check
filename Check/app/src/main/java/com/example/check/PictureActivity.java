package com.example.check;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.check.Entity.Picture;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PictureActivity extends Activity {
    private TextView picResult;
    private Button alterB;
    private ImageView picture;
    private int picid;
    private Handler handler;
    private String baseURL = "http://10.0.2.2:9000/pic";
    private Picture pictureObject;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        picResult = (TextView)findViewById(R.id.picResult);
        alterB = (Button)findViewById(R.id.but_alter);
        picture = (ImageView)findViewById(R.id.PIC);
        handler = new Handler();
        final Intent intent = getIntent();
        picid = intent.getIntExtra("picid",0);

        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getPicD");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(picid);
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
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    //System.out.println(jsonObject);
                    pictureObject = new Picture();
                    pictureObject.setUserid(jsonObject.getString("userid"));
                    pictureObject.setPicid(jsonObject.getInt("picid"));
                    pictureObject.setResult(jsonObject.getString("result"));
//                    pictureObject.setPhoto("photo"+jsonObject.getString("photo"));
                    pictureObject.setPhoto(jsonObject.getString("photo"));
                    pictureObject.setTaskid(jsonObject.getInt("taskid"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                System.out.println("hhhhh"+pictureObject.getResult());
                if (pictureObject.getResult().equals("1")){
                    picResult.setText("识别结果：已通过");
                }else {
                    picResult.setText("识别结果：未通过");
                }
                System.out.println("1234fhhhhhhh"+pictureObject.getPhoto());
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                String base64 = pictureObject.getPhoto();
                byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                picture.setImageBitmap(decodedByte);
//                picture.setImageURI(Uri.fromFile(new File(pictureObject.getPhoto())));
                    }
                };
                new Thread(){
                    public void run(){
                        handler.post(runnable);
                    }
                }.start();
            }
        });

        alterB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pictureObject.getResult().equals("0")){
                    new AlertDialog.Builder(PictureActivity.this).setTitle("修改结果")
                            .setMessage("是否将结果修改为已通过？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String num = "1";
                                    StringBuilder stringBuilder = new StringBuilder(baseURL+"/alterR");
                                    stringBuilder.append("?");
                                    stringBuilder.append("picid").append("=").append(picid).append("&");
                                    stringBuilder.append("result").append("=").append(num);
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
                                            Intent intent1 = new Intent(PictureActivity.this, PictureActivity.class);
                                            intent1.putExtra("picid",picid);
                                            startActivity(intent1);
                                        }
                                    });
                                }
                            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }else {
                    new AlertDialog.Builder(PictureActivity.this).setTitle("修改结果")
                            .setMessage("是否将结果修改为未通过？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    final String num = "0";
                                    StringBuilder stringBuilder = new StringBuilder(baseURL+"/alterR");
                                    stringBuilder.append("?");
                                    stringBuilder.append("picid").append("=").append(picid).append("&");
                                    stringBuilder.append("result").append("=").append(num);
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
                                            Intent intent1 = new Intent(PictureActivity.this, TaskActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("taskid",pictureObject.getTaskid());
                                            intent1.putExtras(bundle);
                                            startActivity(intent1);
                                            finish();
                                        }
                                    });
                                }
                            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }

            }
        });
    }
//    public static Bitmap getBitmapFromPath(String path){
//        if (!new File(path).exists()) {
//            return null;
//        }
//        //最大读取10M的图片
//        byte[] buf = new byte[1024*1024*10];
//        Bitmap bitmap = null;
//
//        try {
//            FileInputStream fis = new FileInputStream(path);
//            int len = fis.read(buf, 0, buf.length);
//            bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
//            if (bitmap == null) {
//                return null;
//            }
//            fis.close();
//        } catch (Exception e) {
//            return null;
//        }
//        return bitmap;
//    }

}
