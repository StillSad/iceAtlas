package com.thinkwage.homebundle.bean;

import com.ice.library.bean.NetBaseResult;

import java.util.List;

/**
 * Created by ICE on 2018/1/24.
 */

public class HomeBean extends NetBaseResult {

    /**
     * advertisements : {}
     * appName : string
     * articleInfo : {"totalAmount":0,"curPage":0,"pageSize":0,"totalPage":0,"list":[{"attribute":"string","bodys":[{"imageUrl":"string","summary":"string","title":"string","type":"string","url":"string"}],"head":{"iconName":"string","iconUrl":"string"},"id":"string","login":"string","operations":[{"title":"string","url":"string"}],"shareSummary":"string","shareTitle":"string","shareUrl":"string"}]}
     * banners : [{"chainUrl":"string","imgUrl":"string","isChain":"string","name":"string","sort":0,"type":"string"}]
     * info : {"allcity":[{"centerx":0,"centery":0,"city_id":"string","district_online":0,"name":"string","pinyin":"string","province":"string","zone_online":0}],"hotcity":[{"centerx":0,"centery":0,"city_id":"string","district_online":0,"name":"string","pinyin":"string","province":"string","zone_online":0}],"code":"0000","message":"string","description":"string"}
     * module : {"name":"string","title":"string","url":"string"}
     * notice : [{"noticeName":"string","noticeUrl":"string"}]
     * real : string
     * version : {"downUrl":"string","force":"string","newContent":"string","versionNumber":"string","code":"0000","message":"string","description":"string"}
     * code : 0000
     * message : string
     * description : string
     */

    public AdvertisementsBean advertisements;
    public String appName;
    public ArticleInfoBean articleInfo;
    public InfoBean info;
    public ModuleBean module;
    public String real;
    public VersionBean version;
    public List<BannersBean> banners;
    public List<NoticeBean> notice;

    public static class AdvertisementsBean {
    }

    public static class ArticleInfoBean {
        /**
         * totalAmount : 0
         * curPage : 0
         * pageSize : 0
         * totalPage : 0
         * list : [{"attribute":"string","bodys":[{"imageUrl":"string","summary":"string","title":"string","type":"string","url":"string"}],"head":{"iconName":"string","iconUrl":"string"},"id":"string","login":"string","operations":[{"title":"string","url":"string"}],"shareSummary":"string","shareTitle":"string","shareUrl":"string"}]
         */

        public String totalAmount;
        public String curPage;
        public String pageSize;
        public String totalPage;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * attribute : string
             * bodys : [{"imageUrl":"string","summary":"string","title":"string","type":"string","url":"string"}]
             * head : {"iconName":"string","iconUrl":"string"}
             * id : string
             * login : string
             * operations : [{"title":"string","url":"string"}]
             * shareSummary : string
             * shareTitle : string
             * shareUrl : string
             */

            public String attribute;
            public HeadBean head;
            public String id;
            public String login;
            public String shareSummary;
            public String shareTitle;
            public String shareUrl;
            public List<BodysBean> bodys;
            public List<OperationsBean> operations;

            public static class HeadBean {
                /**
                 * iconName : string
                 * iconUrl : string
                 */

                public String iconName;
                public String iconUrl;
            }

            public static class BodysBean {
                /**
                 * imageUrl : string
                 * summary : string
                 * title : string
                 * type : string
                 * url : string
                 */

                public String imageUrl;
                public String summary;
                public String title;
                public String type;
                public String url;
            }

            public static class OperationsBean {
                /**
                 * title : string
                 * url : string
                 */

                public String title;
                public String url;
            }
        }
    }

    public static class InfoBean {
        /**
         * allcity : [{"centerx":0,"centery":0,"city_id":"string","district_online":0,"name":"string","pinyin":"string","province":"string","zone_online":0}]
         * hotcity : [{"centerx":0,"centery":0,"city_id":"string","district_online":0,"name":"string","pinyin":"string","province":"string","zone_online":0}]
         */

        public List<AllcityBean> allcity;
        public List<HotcityBean> hotcity;

        public static class AllcityBean {
            /**
             * centerx : 0
             * centery : 0
             * city_id : string
             * district_online : 0
             * name : string
             * pinyin : string
             * province : string
             * zone_online : 0
             */

            public String centerx;
            public String centery;
            public String city_id;
            public String district_online;
            public String name;
            public String pinyin;
            public String province;
            public String zone_online;
        }

        public static class HotcityBean {
            /**
             * centerx : 0
             * centery : 0
             * city_id : string
             * district_online : 0
             * name : string
             * pinyin : string
             * province : string
             * zone_online : 0
             */

            public String centerx;
            public String centery;
            public String city_id;
            public String district_online;
            public String name;
            public String pinyin;
            public String province;
            public String zone_online;
        }
    }

    public static class ModuleBean {
        /**
         * name : string
         * title : string
         * url : string
         */

        public String name;
        public String title;
        public String url;
    }

    public static class VersionBean {
        /**
         * downUrl : string
         * force : string
         * newContent : string
         * versionNumber : string
         */

        public String downUrl;
        public String force;
        public String newContent;
        public String versionNumber;

    }

    public static class BannersBean {
        /**
         * chainUrl : string
         * imgUrl : string
         * isChain : string
         * name : string
         * sort : 0
         * type : string
         */

        public String chainUrl;
        public String imgUrl;
        public String isChain;
        public String name;
        public String sort;
        public String type;
    }

    public static class NoticeBean {
        /**
         * noticeName : string
         * noticeUrl : string
         */

        public String noticeName;
        public String noticeUrl;
    }
}
