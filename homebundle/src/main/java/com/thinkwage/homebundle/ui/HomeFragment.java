package com.thinkwage.homebundle.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.annotation.apt.MvpFragment;
import com.ice.library.base.fragment.BaseFragment;
import com.thinkwage.homebundle.R;
import com.thinkwage.homebundle.R2;
import com.thinkwage.homebundle.base.ComponentFragment;
import com.thinkwage.homebundle.bean.HomeBean;
import com.thinkwage.homebundle.di.component.FragmentComponent;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ICE on 2018/1/25.
 */
@MvpFragment
public class HomeFragment extends ComponentFragment<HomePresenter> implements HomeContract.View{

    @BindView(R2.id.tv_location)
    TextView tvLocation;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.iv_add)
    ImageView tvAdd;

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object data) {

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
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
    protected void initInject(FragmentComponent fragmentComponent) {
        mFragmentComponent.inject(this);
    }
}
