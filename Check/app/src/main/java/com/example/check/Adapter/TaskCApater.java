package com.example.check.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.check.Entity.Task;
import com.example.check.R;

import java.util.List;

public class TaskCApater extends ArrayAdapter<Task> {
    public TaskCApater(@NonNull Context context, int resource, @NonNull List<Task> tasks){
        super(context,resource,tasks);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task=getItem(position);//得到当前项的 Fruit 实例
        //为每一个子项加载设定的布局
        View view= LayoutInflater.from(getContext()).inflate(R.layout.task_item,parent,false);
        //分别获取 image view 和 textview 的实例
        TextView taskname =view.findViewById(R.id.task_title);
        TextView taskid=view.findViewById(R.id.task_id);
        // 设置要显示的图片和文字
        taskname.setText(task.getTitle());
        taskid.setText(Integer.toString(task.getTaskid()));

        return view;
    }
}
