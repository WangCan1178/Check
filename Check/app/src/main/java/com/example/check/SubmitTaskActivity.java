package com.example.check;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.check.menu.MainActivity;
import com.example.check.menu.joinActivity;
import com.example.check.util.DcimUriget;
import com.example.check.util.UploadUtil;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SubmitTaskActivity extends AppCompatActivity {
    private ImageView image;
    private Button but_submit;
    private String baseURL = "http://10.0.2.2:9000";
    private SharedPreferences sp;
    private int taskid,groupid;
    private TextView taskname,description;
    private TextView state;
    private File fileer;
    // 准备申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};
    private static final int REQUEST_CODE = 10001;

    private void getphotos() {
        Intent getImage = new Intent(Intent.ACTION_PICK, null);
        getImage.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//这是图片类型
        startActivityForResult(getImage, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            for (String permission : permissions) {
                //  GRANTED---授权  DINIED---拒绝
                if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);

                }
            }
        }
        if (resultCode != RESULT_OK && data.getData()!=null) {
            return;
        } else {
            Uri imageuri = data.getData();
            String filePath = DcimUriget.getFilePathByUri(SubmitTaskActivity.this, imageuri);
            if (!TextUtils.isEmpty(filePath)) {//如果路径不为空
                //需要Glide加载图片
                final Bitmap bitmaperer = BitmapFactory.decodeFile(filePath);
                fileer = new File(filePath);//将图片转为file形式。
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image.setImageBitmap(bitmaperer);
                    }
                });
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_task);
        image = (ImageView) findViewById(R.id.image);
        description = (TextView)findViewById(R.id.description);
        sp = getSharedPreferences("onClick",MODE_PRIVATE);
        taskname = (TextView)findViewById(R.id.taskname);
        state = (TextView) findViewById(R.id.state);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        taskid = bundle.getInt("taskid");
        groupid = bundle.getInt("groupid");
        Toast.makeText(this,"群组ID:"+taskid,Toast.LENGTH_SHORT).show();
        final SharedPreferences sp = getSharedPreferences("onClick",MODE_PRIVATE);
        final String id = sp.getString("userId","");


        StringBuilder stringBuilder = new StringBuilder(baseURL+"/task/altername");
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
                final String result = response.body().string();
                SubmitTaskActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        taskname.setText(result);
                    }
                });
            }
        });

        StringBuilder stringBuilder2 = new StringBuilder(baseURL+"/task/alterdescription");
        stringBuilder2.append("?");
        stringBuilder2.append("id").append("=").append(taskid);
        OkHttpClient client2 = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url(stringBuilder2.toString())
                .get()
                .build();
        Call call2 = client2.newCall(request2);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result2 = response.body().string();
                SubmitTaskActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        description.setText(result2);
                    }
                });
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getphotos();
            }
        });
        but_submit = (Button) findViewById(R.id.but_submit);
        but_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = SubmitTaskActivity.this.getSharedPreferences("onClick", MODE_PRIVATE);
                String userid = sp.getString("userId","");
//                Log.d("hhhh",userid);
                String result = UploadUtil.uploadImage(fileer, "http://10.0.2.2:9000/pic/add",taskid,userid, (String) description.getText());
                Toast.makeText(SubmitTaskActivity.this,result, Toast.LENGTH_SHORT).show();
                if (result!=null){state.setText(result);}
                Intent intent = new Intent(SubmitTaskActivity.this, joinActivity.class);  //(当前Activity，目标Activity)
                Bundle bundle1 = new Bundle();
                bundle1.putInt("groupid",groupid);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }
}
