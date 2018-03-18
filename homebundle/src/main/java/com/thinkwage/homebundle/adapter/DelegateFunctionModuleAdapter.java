package com.thinkwage.homebundle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.thinkwage.homebundle.R;

/**
 * Created by ICE on 2018/1/31.
 */

public class DelegateFunctionModuleAdapter extends DelegateAdapter.Adapter {

    public Context context;
    private LayoutHelper helper;
    private LayoutInflater inflater;
    private int[] modulIcon = new int[]{R.mipmap.icon_home_bundle_tax_plan,R.mipmap.icon_home_bundle_tax_count
                                        ,R.mipmap.icon_home_bundle_tax_msg,R.mipmap.icon_home_bundle_tax_declare};
    private String[] modulTitle = new String[]{"个税优化","个税计算","个税咨讯","个税申报"};


    public DelegateFunctionModuleAdapter(Context context, LayoutHelper helper) {
        this.inflater = LayoutInflater.from(context);
        this.helper = helper;
        this.context = context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FunctionModuleHolder(inflater.inflate(R.layout.item_home_function_module, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        FunctionModuleHolder functionModuleHolder = (FunctionModuleHolder) holder;
        functionModuleHolder.tvModuleTitle.setText(modulTitle[position]);
        functionModuleHolder.ivModuleIcon.setImageResource(modulIcon[position]);


    }

    @Override
    public int getItemCount() {
        return modulIcon.length;
    }

    public class FunctionModuleHolder extends RecyclerView.ViewHolder {
        public TextView tvModuleTitle;
        public ImageView ivModuleIcon;

        public FunctionModuleHolder(View itemView) {
            super(itemView);
            tvModuleTitle = itemView.findViewById(R.id.tv_module_title);
            ivModuleIcon = itemView.findViewById(R.id.iv_module_icon);
        }
    }

}
