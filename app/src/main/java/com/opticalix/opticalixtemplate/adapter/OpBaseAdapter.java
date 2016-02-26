package com.opticalix.opticalixtemplate.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.opticalix.opticalixtemplate.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic adapter for ListView use.
 * Created by opticalix@gmail.com on 16/1/11.
 */
public abstract class OpBaseAdapter<T extends BaseModel> extends BaseAdapter {
    private List<T> mModelList = new ArrayList<>();
    private List<Integer> mViewIds = new ArrayList<>();

    @Override
    public int getCount() {
        return mModelList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < 0 || mModelList.size() <= position) {
            return null;
        }
        return mModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || !(convertView.getTag() instanceof ViewHolder)) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(offerViewLayout(), parent, false);
            holder = createViewHolder();
            offerViewIds(mViewIds);
            if (mViewIds != null && mViewIds.size() > 0) {
                for (int i : mViewIds) {
                    View viewById = convertView.findViewById(i);
                    if (viewById == null)
                        throw new RuntimeException("Can not find View by id=" + i);
                    holder.put(i, viewById);
                }
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        updateView(holder, (T) getItem(position));

        return convertView;
    }

    protected abstract int offerViewLayout();

    protected abstract void offerViewIds(List<Integer> viewIds);

    protected abstract void updateView(ViewHolder holder, T item);

    public List<T> getModelList() {
        return mModelList;
    }

    public void addToModelList(List<T> modelList) {
        mModelList.addAll(modelList);
    }

    /**
     * Create a NEW viewHolder.
     * @return
     */
    private ViewHolder createViewHolder() {
        return new ViewHolder();
    }

    /**
     * Contains a map which hold the relationship (Id of View to View).
     */
    static class ViewHolder {
        SparseArray<View> mIdToView = new SparseArray<>();

        public void put(int id, View view) {
            mIdToView.put(id, view);
        }

        public View get(int id) {
            return mIdToView.get(id);
        }

        public TextView getTv(int id) {
            return (TextView) get(id);
        }

        public ImageView getIv(int id) {
            return (ImageView) get(id);
        }

    }
}
