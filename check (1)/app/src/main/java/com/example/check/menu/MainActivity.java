package com.example.check.menu;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.check.R;
import com.example.check.fragment.joinFragment;
import com.example.check.fragment.createFragment;
import com.example.check.fragment.myFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    BottomNavigationView mNavigationView;
    joinFragment  joinFragments = new joinFragment();
//    createActivity createActivity = new createActivity();
    createFragment  createFragments = new createFragment();
    myFragment myFragments =new myFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); //页面初始化导航栏
    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.showCode:
//                showCode.setImageBitmap(Code.getInstance().createBitmap());
//                realCode = Code.getInstance().getCode().toLowerCase();
//                Log.v(TAG,"realCode"+realCode);
//                break;
//            case R.id.jiaoyan:
//                String phoneCode = inCode.getText().toString().toLowerCase();
//                String msg = "生成的验证码："+realCode+"输入的验证码:"+phoneCode;
//                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
//                if (phoneCode.equals(realCode)) {
//                    Toast.makeText(MainActivity.this, phoneCode + "验证码正确", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, phoneCode + "验证码错误", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
    private void init() {

        //获取页面标签对象
        viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(this);


        //页面切换
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return  joinFragments;
                    case 1:
                        return  createFragments;
                    case 2:
                        return  myFragments;
                }

                return null;
            }

            @Override
            public int getCount() {
                return 3;
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

