package com.example.check.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.check.Entity.Isend;
import com.example.check.Entity.Task;
import com.example.check.R;

import java.util.List;

public class TaskApater extends ArrayAdapter<Isend> {
    public TaskApater(@NonNull Context context, int resource, @NonNull List<Isend> tasks){
        super(context,resource,tasks);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Isend task=getItem(position);//得到当前项的 Fruit 实例
        //为每一个子项加载设定的布局
        View view= LayoutInflater.from(getContext()).inflate(R.layout.task_item,parent,false);
        //分别获取 image view 和 textview 的实例
        TextView taskname =view.findViewById(R.id.task_title);
        TextView taskid=view.findViewById(R.id.task_id);
//        TextView taskifend = view.findViewById(R.id.task_ifend);
        TextView taskresult = view.findViewById(R.id.task_result);
//        TextView taskifend = view.findViewById(R.id.task_ifend);
        // 设置要显示的图片和文字
        taskname.setText(task.getTitle());
        taskid.setText(Integer.toString(task.getTaskid()));
//        Toast.makeText(this,taskresult.getText().toString(),Toast.LENGTH_SHORT).show();
//        taskifend.setText(Integer.toString(task.getIfend()));
//        taskresult.setText(Integer.toString(task.getResult()));
//        Log.d("标题",task.getTitle());

//        Log.d("hhh",Integer.toString(task.getResult()));
        if(task.getIfend()==1){
            taskname.setTextColor(0xff808080);
            taskname.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            taskresult.setTextColor(0xff808080);
            taskresult.setText("已结束");
        } else if (task.getIfend() == 0) {
            if (task.getResult().equals("0")) {
                taskresult.setTextColor(0xffff0000);
                taskresult.setText("未通过");
            } else if (task.getResult().equals("1")) {
                taskresult.setTextColor(0xff339900);
                taskresult.setText("已通过");
            } else if (task.getResult().equals("2")) {
                taskresult.setTextColor(0xff0033ff);
                taskresult.setText("未填写");
            } else {
                taskresult.setText("出错啦！");
            }
        }
        return view;
    }
}
