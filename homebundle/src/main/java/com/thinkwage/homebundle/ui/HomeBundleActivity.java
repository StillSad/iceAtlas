package com.thinkwage.homebundle.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.app.annotation.apt.MvpActivity;
import com.ice.api.bean.HomeBean;
import com.thinkwage.homebundle.R;
import com.thinkwage.homebundle.R2;
import com.thinkwage.homebundle.adapter.DelegateFunctionModuleAdapter;
import com.thinkwage.homebundle.adapter.DelegateHeaderAdapter;
import com.thinkwage.homebundle.base.ComponentActivity;
import com.thinkwage.homebundle.di.component.ActivityComponent;


import java.util.List;

import butterknife.BindView;

@MvpActivity
public class HomeBundleActivity extends ComponentActivity<HomeBundlePresenter> implements HomeBundleContract.View{

    @BindView(R2.id.rv_content)
    RecyclerView rvContent;

    private DelegateAdapter mDelegateAdapter;
    private List<DelegateAdapter.Adapter> mAdapters;

    @Override
    public void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    public void initRecyclerView() {
        //初始化
        //创建VirtualLayoutManager对象
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        rvContent.setLayoutManager(layoutManager);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        rvContent.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置适配器
        mDelegateAdapter = new DelegateAdapter(layoutManager, true);
        mDelegateAdapter.addAdapter(initHeader());
        mDelegateAdapter.addAdapter(initFunctionModule());
        rvContent.setAdapter(mDelegateAdapter);

    }


    public  DelegateFunctionModuleAdapter initFunctionModule(){
        GridLayoutHelper gridLayoutHelper=new GridLayoutHelper(4);
        //自定义设置某些位置的Item的占格数
//        gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (position >5) {
//                    return 2;
//                }else {
//                    return 1;
//                }
//            }
//        });
        //是否填满可用区域
        gridLayoutHelper.setAutoExpand(true);

        DelegateFunctionModuleAdapter delegateFunctionModuleAdapter=new DelegateFunctionModuleAdapter(this,gridLayoutHelper);
        return delegateFunctionModuleAdapter;
    }

    public DelegateHeaderAdapter initHeader(){
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();


        DelegateHeaderAdapter delegateHeaderAdapter=new DelegateHeaderAdapter(this,linearLayoutHelper);
        return delegateHeaderAdapter;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.initData();
    }

    @Override
    public void initBanner(List<HomeBean.BannersBean> banners) {

    }

    @Override
    public void initArticleInfo(HomeBean.ArticleInfoBean articleInfo) {

    }

    @Override
    public void initModule(HomeBean.ModuleBean module) {

    }

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_home_bundle;
    }
}
