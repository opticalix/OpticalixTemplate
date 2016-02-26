package com.opticalix.opticalixtemplate.adapter;

import com.opticalix.opticalixtemplate.model.BaseModel;
import com.opticalix.opticalixtemplate.model.PagedListInfo;

/**
 * Adapter for listViews that need paging.
 * Created by opticalix@gmail.com on 16/1/12.
 */
public abstract class PagedListAdapter<ItemType extends BaseModel> extends OpBaseAdapter<ItemType> {
    protected PagedListInfo<ItemType> mPagedListInfo;

    @Override
    public int getCount() {
        return mPagedListInfo.getDataListSize();
    }

    @Override
    public Object getItem(int position) {
        if (position < 0 || mPagedListInfo.getDataListSize() <= position) {
            return null;
        }
        return mPagedListInfo.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public PagedListInfo<ItemType> getPagedListInfo() {
        return mPagedListInfo;
    }

    public void setPagedListInfo(PagedListInfo<ItemType> pagedListInfo) {
        mPagedListInfo = pagedListInfo;
    }
}
