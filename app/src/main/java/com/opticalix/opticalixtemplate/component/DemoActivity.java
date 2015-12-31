//package com.opticalix.opticalixtemplate.component;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Message;
//import android.view.View;
//import android.widget.TextView;
//
//import com.opticalix.opticalixtemplate.R;
//import com.opticalix.opticalixtemplate.component.base.BaseActivity;
//import com.opticalix.opticalixtemplate.component.frg.DemoPagerFragment;
//import com.opticalix.opticalixtemplate.utils.LogUtils;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
///**
// * Created by opticalix@gmail.com on 15/12/24.
// */
//public class DemoActivity extends BaseActivity implements View.OnClickListener {
//
//    @Bind(R.id.tv_1)
//    TextView mTv1;
//    @Bind(R.id.tv_2)
//    TextView mTv2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_demo);
//        ButterKnife.bind(this);
//
//        mTv1.setOnClickListener(this);
//        mTv2.setOnClickListener(this);
//    }
//
//    @Override
//    protected void onHandleMessage(Activity activity, Message msg) {
//        super.onHandleMessage(activity, msg);
//        LogUtils.d(this, "Receive handle msg, what=" + msg.what);
//    }
//
//    @Override
//    protected boolean enableFullLoadingWhenAccessingNetwork() {
//        return true;
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_1:
//                Intent intent = new Intent(this, HttpActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.tv_2:
//                //change fragment here!!
//                Intent demo = new Intent(this, DemoFrgContainerActivity.class);
//                demo.putExtra("class", DemoPagerFragment.class);
//                startActivity(demo);
//                break;
//        }
//    }
//}
