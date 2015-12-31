package com.opticalix.opticalixtemplate.component;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.component.base.BaseActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by opticalix@gmail.com on 15/12/31.
 */
public class DemoFrgContainerActivity extends BaseActivity {
    public static final int PAGER_ADAPTER = 0;
    @Bind(R.id.fragment_container)
    FrameLayout mFragmentContainer;

    @Override
    protected boolean enableFullLoadingWhenAccessingNetwork() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_container);
        ButterKnife.bind(this);


        Class clazz = (Class) getIntent().getSerializableExtra("class");
        Method newInstanceMethod = null;
        if (clazz != null) {
            try {
                newInstanceMethod = clazz.getMethod("newInstance");//newInstance can not hold any parameters
                if (null != newInstanceMethod) {
                    Fragment fragment = (Fragment) newInstanceMethod.invoke(null);
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }
}
