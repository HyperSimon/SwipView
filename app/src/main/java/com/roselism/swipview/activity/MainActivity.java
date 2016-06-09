package com.roselism.swipview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.roselism.swipview.R;
import com.roselism.swipview.library.SlideView;

public class MainActivity extends AppCompatActivity {

    public SlideView mSlideView;
    public RelativeLayout mSurface;
    public LinearLayout mBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    public void initView() {
        mSlideView = (SlideView) findViewById(R.id.swipview);
        mSurface = (RelativeLayout) findViewById(R.id.surfaceView);
        mBottom = (LinearLayout) findViewById(R.id.bottomView);
    }

    public void initData() {

    }

    public void initEvent() {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
