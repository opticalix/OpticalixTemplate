package com.opticalix.opticalixtemplate.adapter;

import com.opticalix.opticalixtemplate.model.SimpleText;

import java.util.List;

/**
 * Created by opticalix@gmail.com on 16/1/11.
 */
public class DemoSimpleTextListAdapter extends OpBaseAdapter<SimpleText> {

    @Override
    protected int offerViewLayout() {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    protected void offerViewIds(List<Integer> viewIds) {
        viewIds.add(android.R.id.text1);
    }

    @Override
    protected void updateView(ViewHolder holder, SimpleText item) {
        holder.getTv(android.R.id.text1).setText(item.name);
        item.printFields();
    }
}
