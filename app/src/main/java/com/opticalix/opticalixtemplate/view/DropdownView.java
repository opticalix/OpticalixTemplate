package com.opticalix.opticalixtemplate.view;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.utils.LogUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by opticalix@gmail.com on 16/1/4.
 */
public class DropdownView extends RelativeLayout {
    private Drawable mArrowDownDrawable;
    private Drawable mArrowUpDrawable;
    private String[] mTitles;
    private int mDropdownListCount;
    private int mDrawablePadding;
    private int mAnchor;
    private LinearLayout mLinearContainer;

    private ListView[] mListViews;//all listViews
    private int mCheckedIndex = -1;//which listView is shown now

    public DropdownView(Context context) {
        super(context);
        init();
    }

    public DropdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DropdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DropdownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        initParams();
        innerCreateViews();
    }

    private void initParams() {
        //fixme
        mDropdownListCount = 2;//
        mArrowDownDrawable = getResources().getDrawable(R.mipmap.ic_arrow_down);//selector or two drawables
        mArrowUpDrawable = getResources().getDrawable(R.mipmap.ic_arrow_up);
        if (mArrowDownDrawable != null) {
            mArrowDownDrawable.setBounds(0, 0, mArrowDownDrawable.getMinimumWidth(), mArrowDownDrawable.getMinimumHeight());
        }
        if (mArrowUpDrawable != null) {
            mArrowUpDrawable.setBounds(0, 0, mArrowUpDrawable.getMinimumWidth(), mArrowUpDrawable.getMinimumHeight());
        }
        mTitles = new String[mDropdownListCount];
        for (int i = 0; i < mDropdownListCount; i++) {
            mTitles[i] = "Title" + i;
        }

        mDrawablePadding = 10;//

    }

    private void innerCreateViews() {
        //add linearLayout first
        mLinearContainer = new LinearLayout(getContext());
        mLinearContainer.setOrientation(LinearLayout.HORIZONTAL);
        if (mAnchor == 0) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mLinearContainer.setId(mAnchor = Util.myGenerateViewId());
            } else {
                mLinearContainer.setId((mAnchor = generateViewId()));
            }
            LogUtils.d("generate id=" + mAnchor);
        }
        addView(mLinearContainer, generateRelativeLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //
        for (int i = 0; i < mDropdownListCount; i++) {
            FrameLayout frameLayout = new FrameLayout(getContext());

            TextView title = new TextView(getContext());
            title.setTag(generateUniqueTag4TextView(i));
            title.setCompoundDrawables(null, null, mArrowDownDrawable, null);
            title.setCompoundDrawablePadding(mDrawablePadding);
            FrameLayout.LayoutParams frameLps = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            frameLps.gravity = Gravity.CENTER;
            title.setLayoutParams(frameLps);
            title.setText(mTitles[i]);

            frameLayout.addView(title);
            LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            lps.weight = 1;
            lps.gravity = Gravity.CENTER_VERTICAL | Gravity.TOP;
            frameLayout.setLayoutParams(lps);

            mLinearContainer.addView(frameLayout);
            frameLayout.setTag(i);
            frameLayout.setOnClickListener(mOnClickListener);
        }
    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof FrameLayout) {
                int vTag = (int) v.getTag();
                LogUtils.d("tag=" + vTag);
                onTitleClick(v, vTag);
            }

        }
    };

    private void onTitleClick(View v, int which) {
        if(mCheckedIndex != -1 && mCheckedIndex != which){
            //restore last checked one
            changeTitleDrawable(mCheckedIndex);
            changeListViewVisibility(mCheckedIndex);
        }
        mCheckedIndex = mCheckedIndex == which ? -1 : which;
        changeTitleDrawable(which);
        changeListViewVisibility(which);

        animateListHeight();
    }

    private void changeListViewVisibility(int which) {
        mListViews[which].setVisibility(mListViews[which].getVisibility() == VISIBLE ? GONE : VISIBLE);
    }

    private void changeTitleDrawable(int which){
        View view = findViewWithTag(generateUniqueTag4TextView(which));
        if(view != null && view instanceof TextView){
            TextView viewWithTag = (TextView) view;
            viewWithTag.setCompoundDrawables(null, null, viewWithTag.getCompoundDrawables()[2] == mArrowDownDrawable ? mArrowUpDrawable : mArrowDownDrawable, null);
        }
    }

    private String generateUniqueTag4TextView(int which){
        return "textView-title-"+which;
    }


    /*
    init param:
    rows

    public methods:
    setTitle(args...)
    setIcon(icon)
    setVerticalDivider(divider)
    setShowAnimation()
    setDismissAnimation()

    setAdapter(whichList, adapter)
    setOnItemClickListener(listener)
    attachListView()
     */
    public void attachListView(ListView... listViews) {
        if (listViews == null || listViews.length < mDropdownListCount)
            throw new RuntimeException("should offer enough listViews");
        mListViews = listViews;
        for (int i = 0; i < mDropdownListCount; i++) {
            RelativeLayout.LayoutParams params;
            ListView listView = listViews[i];
            listView.setVisibility(GONE);//not visible at first
            addView(listView);
            params = (RelativeLayout.LayoutParams) listView.getLayoutParams();
            if (params == null) {
                params = generateRelativeLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                listView.setLayoutParams(params);
            }
            ((RelativeLayout.LayoutParams) listView.getLayoutParams()).addRule(RelativeLayout.BELOW, mAnchor);


        }
    }

    public void animateListHeight() {
        PropertyValuesHolder topList = PropertyValuesHolder.ofInt("top", mListViews[0].getTop(), 5);

        ValueAnimator animList = ValueAnimator.ofPropertyValuesHolder(topList);
        animList.setDuration(300L);

        ValueAnimator.AnimatorUpdateListener listUpdater = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int top = ((Integer)animation.getAnimatedValue("top")).intValue();
                mListViews[0].setTop(top);
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) mListViews[0].getLayoutParams();
                params.height = mListViews[0].getBottom() - top;
                mListViews[0].setLayoutParams(params);

                LogUtils.d("top="+top+", params.height="+params.height);
            }
        };

        animList.addUpdateListener(listUpdater);
        animList.start();
    }

    RelativeLayout.LayoutParams generateRelativeLayoutParams(int w, int h) {
        return new RelativeLayout.LayoutParams(w, h);
    }

    static class Util {
        private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

        /**
         * Generate a value suitable for use in {@link #setId(int)}.
         * This value will not collide with ID values generated at build time by aapt for R.id.
         *
         * @return a generated ID value
         */
        public static int myGenerateViewId() {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        }
    }

    interface OnDropdownItemClickListener {
        void onItemClick(View v, int whichList, int position);
    }

}
