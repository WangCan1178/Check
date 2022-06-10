package com.example.check.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.check.Entity.Group;
import com.example.check.R;

import java.util.List;


public class GroupAdapter extends ArrayAdapter<Group> {
    public GroupAdapter(@NonNull Context context, int resource, @NonNull List<Group> groups){
        super(context,resource,groups);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Group group=getItem(position);//得到当前项的 Fruit 实例
        //为每一个子项加载设定的布局
        View view= LayoutInflater.from(getContext()).inflate(R.layout.group_item,parent,false);
        //分别获取 image view 和 textview 的实例
        TextView groupname =view.findViewById(R.id.group_name);
        TextView groupid=view.findViewById(R.id.group_id);
        // 设置要显示的图片和文字
        groupname.setText(group.getName());
//        groupid.setText(Integer.toString(group.getId()));
        groupid.setText(group.getDescribe());
        return view;
    }


}
