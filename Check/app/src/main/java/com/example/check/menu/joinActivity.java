package com.example.check.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.check.Adapter.TaskApater;
import com.example.check.R;
import com.example.check.fragment.jGroupFragment;
import com.example.check.fragment.jTaskFragment;
import com.example.check.fragment.jMessageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class joinActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener{
    ViewPager viewPager;
    BottomNavigationView mNavigationView;
    jTaskFragment jTaskFragments = new jTaskFragment();
    jMessageFragment jMessageFragments = new jMessageFragment();
    com.example.check.fragment.jGroupFragment jGroupFragment = new jGroupFragment();
    private ImageView but_joingroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinmenu);

        Intent intent = getIntent();

        int id = intent.getIntExtra("groupid",0);
        Bundle bundle = new Bundle();
        bundle.putInt("groupid",id);
        jTaskFragments.setArguments(bundle);
        jGroupFragment.setArguments(bundle);

        init();
    }
    private void init() {

        //获取页面标签对象
        viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);
        mNavigationView = findViewById(R.id.join_nav);
        mNavigationView.setOnNavigationItemSelectedListener(this);

        //页面切换
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return  jTaskFragments;
                    case 1:
                        return  jGroupFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    //实现接口的相关方法  implements上面两个方法后 alt+enter就会弹出这些接口，直接回车实现他们
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        viewPager.setCurrentItem(menuItem.getOrder());
        return true;
    }
}
