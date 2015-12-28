package com.opticalix.opticalixtemplate.component;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.component.base.BaseActivity;
import com.opticalix.opticalixtemplate.loader.SampleDataLoader;
import com.opticalix.opticalixtemplate.loader.base.DataFailType;
import com.opticalix.opticalixtemplate.loader.base.IBaseLoadHandler;
import com.opticalix.opticalixtemplate.net.HttpConfig;
import com.opticalix.opticalixtemplate.net.HttpUtils;
import com.opticalix.opticalixtemplate.net.interceptor.Interceptors;
import com.opticalix.opticalixtemplate.net.test.HttpBinModel;
import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.opticalix.opticalixtemplate.utils.ToastUtils;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by opticalix@gmail.com on 15/12/28.
 */
public class HttpActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_1)
    TextView mTv1;
    @Bind(R.id.et_url)
    EditText mEtUrl;
    @Bind(R.id.tv_2)
    TextView mTv2;
    @Bind(R.id.tv_3)
    TextView mTv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);

//        HttpUtils.getOkHttpClient().interceptors().add(Interceptors.LOG_INTERCEPTOR);
//        HttpUtils.getOkHttpClient().interceptors().add(Interceptors.REWRITE_CACHE_CONTROL_INTERCEPTOR);
    }

    private void doGet(String url) {
        SampleDataLoader sampleDataLoader = new SampleDataLoader();
        //代理 loading？
        sampleDataLoader.loadSampleGet(url, this, mHandler, new IBaseLoadHandler() {
            @Override
            public void onDataBack(Object object) {
                HttpBinModel model = (HttpBinModel) object;
                ToastUtils.showShort(getApplicationContext(), "onDataBack " + model.toString());
            }

            @Override
            public void onDataFail(DataFailType dataFailType) {
                ToastUtils.showShort(getApplicationContext(), dataFailType.getDesc());
            }
        });
    }

    private void doPost(String url) {
        SampleDataLoader sampleDataLoader = new SampleDataLoader();
        sampleDataLoader.loadSamplePost(url, this, mHandler, new IBaseLoadHandler() {
            @Override
            public void onDataBack(final Object object) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(getApplicationContext(), "onDataBack " + object);
                    }
                });
            }

            @Override
            public void onDataFail(final DataFailType dataFailType) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(getApplicationContext(), dataFailType.getDesc());
                    }
                });

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                doGet(mEtUrl.getText().toString());
                break;
            case R.id.tv_2:
                doPost(mEtUrl.getText().toString());
                break;
            case R.id.tv_3:

                break;
        }
    }

    @Override
    protected void onHandleMessage(Activity activity, Message msg) {
        super.onHandleMessage(activity, msg);
    }

    @Override
    protected boolean enableFullLoadingWhenAccessingNetwork() {
        return true;
    }
}
