package com.opticalix.opticalixtemplate.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.component.base.BaseActivity;
import com.opticalix.opticalixtemplate.component.frg.CropImgFragment;
import com.opticalix.opticalixtemplate.component.frg.ZeppDemoFragment;
import com.opticalix.opticalixtemplate.model.GlobalConfig;
import com.opticalix.opticalixtemplate.utils.AssetsUtils;
import com.opticalix.opticalixtemplate.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_button)
    TextView mTvDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mTvDemo.setOnClickListener(this);

        loadConfig();
    }

    @Override
    protected boolean enableFullLoadingWhenAccessingNetwork() {
        return false;
    }

    private void enterDemoActivity(Class<? extends Fragment> fragmentClass) {
        Intent demo = new Intent(this, DemoFrgContainerActivity.class);
        demo.putExtra("class", fragmentClass);//change demo class
        startActivity(demo);
    }

    private void loadConfig() {
        try {
            //branch name. todo use merge ignore strategy
            GlobalConfig config = (GlobalConfig) AssetsUtils.getObject(this, "global-config.json", GlobalConfig.class);
            LogUtils.d(config.branchName);
            if (config.branchName.equals("master")) {

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_button:
                enterDemoActivity(ZeppDemoFragment.class);
                break;
        }
    }
}
