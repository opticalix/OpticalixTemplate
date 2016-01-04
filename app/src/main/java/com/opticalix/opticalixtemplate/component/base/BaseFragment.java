package com.opticalix.opticalixtemplate.component.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.opticalix.opticalixtemplate.net.request.base.IEnvironment;

/**
 * Created by opticalix@gmail.com on 16/1/4.
 */
public class BaseFragment extends Fragment implements ISimpleLoading, IEnvironment {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    /**
     * Fragment中需要使用getChildFragmentManager
     *
     * @param clazz
     * @return
     */
    public BaseDialogFragment showDialogFragment(Class<? extends BaseDialogFragment> clazz) {
        BaseDialogFragment fragmentByTag = (BaseDialogFragment) this.getChildFragmentManager().findFragmentByTag(clazz.getSimpleName());
        if (fragmentByTag != null) {
            return fragmentByTag;
        }
        try {
            BaseDialogFragment dialogFragment = clazz.newInstance();
            FragmentTransaction fragmentTransaction = this.getChildFragmentManager()
                    .beginTransaction();
            fragmentTransaction.add(dialogFragment,
                    clazz.getSimpleName());//add tag
            fragmentTransaction.commitAllowingStateLoss();//ignore ex
            this.getChildFragmentManager().executePendingTransactions();
            return dialogFragment;
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dismissDialogFragment(Class<? extends BaseDialogFragment> clazz) {
        BaseDialogFragment dialogFragment = (BaseDialogFragment) this.getChildFragmentManager().findFragmentByTag(clazz.getSimpleName());
        if (dialogFragment != null) {
            dialogFragment.dismissAllowingStateLoss();
            this.getChildFragmentManager().beginTransaction().remove(dialogFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void showLoading() {
        if (getView() != null) {
            if (getLoadViewId() != 0) {
                View loading = getView().findViewById(getLoadViewId());
                loading.setVisibility(View.VISIBLE);
            } else {
                showDialogFragment(BaseFullLoadingFragment.class);
            }
        }
    }

    @Override
    public void dismissLoading() {
        if (getView() != null) {
            if (getLoadViewId() != 0) {
                View loading = getView().findViewById(getLoadViewId());
                loading.setVisibility(View.GONE);
            } else {
                dismissDialogFragment(BaseFullLoadingFragment.class);
            }
        }
    }

    @Override
    public int getLoadViewId() {
        return 0;
    }

    @Override
    public String getEnvironmentTag() {
        return this.getClass().getSimpleName();
    }

}
