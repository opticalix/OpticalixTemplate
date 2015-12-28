package com.opticalix.opticalixtemplate.component.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.opticalix.opticalixtemplate.R;


/**
 * Created by opticalix@gmail.com on 15/10/21.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), innerGetTheme());
        setDialogProperty(dialog);
        dialog.setContentView(innerGetLayoutRes());
        dialog.setTitle(innerGetTitle());
        TextView tv = (TextView) dialog.findViewById(R.id.tv_loading_msg);
        if (tv != null) {
            tv.setText(innerGetMessage());
        }
        setDialogPropertyAfter(dialog);
        return dialog;
    }

    protected void setDialogPropertyAfter(Dialog dialog) {
    }

    protected int innerGetTheme() {
        return android.R.attr.alertDialogTheme;
    }

    protected abstract int innerGetLayoutRes();

    protected String innerGetTitle() {
        return "";
    }

    protected String innerGetMessage() {
        return "";
    }

    protected void setDialogProperty(Dialog dialog) {
        dialog.setCancelable(isCancelable());
        dialog.setCanceledOnTouchOutside(false);
    }

}
