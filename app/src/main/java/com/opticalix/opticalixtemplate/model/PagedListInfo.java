package com.opticalix.opticalixtemplate.model;

import com.opticalix.opticalixtemplate.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by opticalix@gmail.com on 16/1/12.
 */
public class PagedListInfo<T extends BaseModel> extends BaseModel {
    private final String TAG = this.getClass().getSimpleName();
    private List<T> mDataList = new ArrayList<>();

    private int mTotal;
//    private int mCountPerPage;
//    private int mCurr;

    private boolean mNeedMore;
    private boolean mIsBusy;

    public PagedListInfo(int total) {
        mTotal = total;
    }

    public void setTotal(int total) {
        mTotal = total;
    }

    /**
     * Call this on retrieving data successfully.
     *
     * @param data new data
     */
    public void update(List<T> data) {
        if (data != null){
            mDataList.addAll(data);
        }

        mIsBusy = false;
        if (mDataList.size() < mTotal) {
            mNeedMore = true;
        } else {
            LogUtils.i(TAG, "dataListSize=" + mDataList.size() + ", total=" + mTotal);
            mNeedMore = false;
        }
    }

    /**
     * Call this when sending load-data request.
     *
     * @return enter lock successful or not
     */
    public boolean tryToEnterLock() {
        if (mIsBusy) return false;
        mIsBusy = true;
        return true;
    }

    /**
     * Call this on failure of retrieving data.
     */
    public void releaseLock() {
        mIsBusy = false;
    }

    public int getDataListSize() {
        return mDataList == null ? 0 : mDataList.size();
    }

    private boolean isEmpty() {
        return mDataList == null || mDataList.size() == 0;
    }

    private boolean needMore() {
        return isEmpty() || mNeedMore;
    }

    public T getFirst() {
        if (mDataList == null || mDataList.size() == 0) {
            return null;
        }
        return mDataList.get(0);
    }

    public T getLast() {
        if (mDataList == null || mDataList.size() == 0) {
            return null;
        }
        return mDataList.get(mDataList.size() - 1);
    }

    public T getItem(int index){
        if(index < 0 || mDataList == null || index >= mDataList.size()){
            return null;
        }
        return mDataList.get(index);
    }
}
