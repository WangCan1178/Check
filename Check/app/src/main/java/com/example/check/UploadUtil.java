package com.example.check;


import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.check.LoginActivity;
import com.example.check.R;
import com.example.check.SubmitTaskActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class UploadUtil {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000;//超时时间
    private static final String CHARSET = "utf-8";//设置编码
    private static String result = "提交失败！我们出了错误，请重试！";


    public static String uploadImage(File file, String RequestURL, int taskid, String userid,String type) {

        String BOUNDARY = UUID.randomUUID().toString();//边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";//内容类型
        try {

            //创建RequestBody封装参数
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);// MediaType.parse("image/jpeg")//application/octet-stream
            //创建MultipartBody,给RequestBody进行设置
            MultipartBody multipartBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM).addFormDataPart("userid", userid)
                    .addFormDataPart("taskid",String.valueOf(taskid))
                    .addFormDataPart("type",type)
                    .addFormDataPart("file",file.getName(), fileBody)
                    .build();
            //创建Request
            Request request = new Request.Builder()
                    .url(RequestURL)
                    .post(multipartBody)
                    .build();

            //创建okhttp对象
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build();
            //上传完图片,得到服务器反馈数据
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("ff", "uploadMultiFile() e=" + e);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    result = response.body().string();
                    Log.i("ff", "uploadMultiFile() response=" + result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("imthe per",result);
        return result;
    }

    private static String getMIMEType(File file) {
        String fileName = file.getName();
        if (fileName.endsWith("png") || fileName.endsWith("PNG")) {
            return "image/png";
        } else {
            return "image/jpg";
        }
    }
}