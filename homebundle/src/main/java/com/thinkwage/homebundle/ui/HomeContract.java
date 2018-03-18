package com.thinkwage.homebundle.ui;

import com.ice.library.mvp.BaseView;
import com.ice.library.mvp.IModel;
import com.ice.api.bean.HomeBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ICE on 2018/1/22.
 */

public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends BaseView {
        void initBanner(List<HomeBean.BannersBean> banners);
        void initArticleInfo(HomeBean.ArticleInfoBean articleInfo);
        void initModule(HomeBean.ModuleBean module);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {
        Observable<HomeBean> homeIndex();
    }
}
