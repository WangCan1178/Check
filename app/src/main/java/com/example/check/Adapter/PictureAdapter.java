package com.example.check.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.check.Entity.Isend;
import com.example.check.Entity.Picture;
import com.example.check.R;

import java.util.List;

public class PictureAdapter extends ArrayAdapter<Isend> {
    public PictureAdapter(@NonNull Context context, int resource, @NonNull List<Isend> tasks){
        super(context,resource,tasks);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Picture picture = getItem(position);
        Isend picture =getItem(position);
        //为每一个子项加载设定的布局
        View view= LayoutInflater.from(getContext()).inflate(R.layout.picture_item,parent,false);
        //分别获取 image view 和 textview 的实例
//        ImageView photo = view.findViewById(R.id.image);
        TextView userid =view.findViewById(R.id.pic_userid);
        TextView picid=view.findViewById(R.id.pic_id);
        TextView taskresult = view.findViewById(R.id.pic_result);
//        picture.setPhoto("photo");

//        photo.setImageURI(picture.getPhoto());//获取到的url在界面上显示成图片,没写

//        userid.setText(picture.getUserid());
        userid.setText(picture.getMemname());
//        Log.d("hhh",userid.getText().toString());
        picid.setText(Integer.toString(picture.getPicid()));
//        Log.d("hhh",Integer.toString(picture.getResult()));
        if(picture.getResult().equals("0")){
            taskresult.setTextColor(0xffff0000);
            taskresult.setText("未通过");
        }else if(picture.getResult().equals("1")){
            taskresult.setTextColor(0xff339900);
            taskresult.setText("已通过");
        }else if(picture.getResult().equals("2")){
            taskresult.setTextColor(0xff0033ff);
            taskresult.setText("未填写");
        }else {
            taskresult.setText("出错啦！");
        }
        // 设置要显示的图片和文字

        return view;
    }
}

