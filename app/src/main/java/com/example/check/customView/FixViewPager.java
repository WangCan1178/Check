package com.example.check.customView;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class FixViewPager extends ViewPager {
    public FixViewPager(@NonNull Context context){super(context);}
    public FixViewPager(@NonNull Context context, @Nullable AttributeSet attr){
        super(context,attr);
    }
    @Override
    public void setCurrentItem(int item){
        super.setCurrentItem(item,false);
    }
}
