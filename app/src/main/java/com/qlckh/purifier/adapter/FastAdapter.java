package com.qlckh.purifier.adapter;

import android.widget.BaseAdapter;

/**
 * @author Andy
 * @date   2018/5/19 16:13
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    FastAdapter.java
 */
public abstract class FastAdapter extends BaseAdapter {


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
