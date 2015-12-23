package com.opticalix.opticalixtemplate.component;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.model.GlobalConfig;
import com.opticalix.opticalixtemplate.utils.AssetsUtils;
import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.opticalix.opticalixtemplate.utils.ScreenUtils;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        loadConfig();

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
}
