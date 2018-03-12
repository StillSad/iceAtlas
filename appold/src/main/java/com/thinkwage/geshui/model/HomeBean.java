package com.thinkwage.geshui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ICE on 2017/4/27.
 */

public class HomeBean implements Serializable {

    /**
     * code : 0000
     * message : 操作成功!
     * description : null
     * appName : gsgj
     * notice : [{"noticeName":"人民的名义开播了","noticeUrl":"www.baidu.com"},{"noticeName":"人民的名义再创新高了","noticeUrl":"http://www.zg-sj.com/"}]
     * module : {"name":"个税咨询","url":"http://www.dgtle.com/","title":"新闻咨询"}
     * banners : [{"name":"测试sort","imgUrl":"http://61.164.38.108:9091/picture/appBanner/1493263892830.jpg","isChain":"yes","chainUrl":"测试sort","type":null,"sort":12},{"name":"1","imgUrl":"http://61.164.38.108:9091/picture/appBanner/1493263906879.jpg","isChain":"no","chainUrl":null,"type":null,"sort":3}]
     * articleInfo : {"totalAmount":null,"curPage":1,"pageSize":5,"totalPage":2,"list":[{"attribute":"top","head":{"iconUrl":null,"iconName":"我要贷款1"},"bodys":[{"imageUrl":"https://www.51geshui.com/CMS/articleImage/1490606091055.png","url":"https://www.baidu.com"},{"imageUrl":"https://www.51geshui.com/CMS/articleImage/1490606091055.png","url":"https://www.baidu.com"}],"operations":[{"title":"操作一","url":"https://www.baidu.com"}]},{"attribute":"top","head":{"iconUrl":null,"iconName":"我要贷款2"},"bodys":[{"imageUrl":"https://www.51geshui.com/CMS/articleImage/1490606091055.png","url":"https://www.baidu.com"}],"operations":[{"title":"操作二","url":"https://www.baidu.com"}]}]}
     */

    private String code;
    private String message;
    private Object description;
    private String appName;
    private ModuleBean module;
    private ArticleInfoBean articleInfo; //文章
    private List<NoticeBean> notice;//公告
    private List<BannersBean> banners; //轮播

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public ModuleBean getModule() {
        return module;
    }

    public void setModule(ModuleBean module) {
        this.module = module;
    }

    public ArticleInfoBean getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(ArticleInfoBean articleInfo) {
        this.articleInfo = articleInfo;
    }

    public List<NoticeBean> getNotice() {
        return notice;
    }

    public void setNotice(List<NoticeBean> notice) {
        this.notice = notice;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public static class ModuleBean implements Serializable {
        /**
         * name : 个税咨询
         * url : http://www.dgtle.com/
         * title : 新闻咨询
         */

        private String name;
        private String url;
        private String title;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ArticleInfoBean implements Serializable {
        /**
         * totalAmount : null
         * curPage : 1
         * pageSize : 5
         * totalPage : 2
         * list : [{"attribute":"top","head":{"iconUrl":null,"iconName":"我要贷款1"},"bodys":[{"imageUrl":"https://www.51geshui.com/CMS/articleImage/1490606091055.png","url":"https://www.baidu.com"},{"imageUrl":"https://www.51geshui.com/CMS/articleImage/1490606091055.png","url":"https://www.baidu.com"}],"operations":[{"title":"操作一","url":"https://www.baidu.com"}]},{"attribute":"top","head":{"iconUrl":null,"iconName":"我要贷款2"},"bodys":[{"imageUrl":"https://www.51geshui.com/CMS/articleImage/1490606091055.png","url":"https://www.baidu.com"}],"operations":[{"title":"操作二","url":"https://www.baidu.com"}]}]
         */

        private Object totalAmount;
        private int curPage;
        private int pageSize;
        private int totalPage;
        private List<ListBean> list;

        public Object getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Object totalAmount) {
            this.totalAmount = totalAmount;
        }

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * attribute : top
             * head : {"iconUrl":null,"iconName":"我要贷款1"}
             * bodys : [{"imageUrl":"https://www.51geshui.com/CMS/articleImage/1490606091055.png","url":"https://www.baidu.com"},{"imageUrl":"https://www.51geshui.com/CMS/articleImage/1490606091055.png","url":"https://www.baidu.com"}]
             * operations : [{"title":"操作一","url":"https://www.baidu.com"}]
             */
            private String id;
            private String attribute;
            private String shareTitle;
            private String shareUrl;
            private String shareSummary;
            private String login;
            private HeadBean head;
            private List<BodysBean> bodys;
            private List<OperationsBean> operations;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getShareTitle() {
                return shareTitle;
            }

            public void setShareTitle(String shareTitle) {
                this.shareTitle = shareTitle;
            }

            public String getShareSummary() {
                return shareSummary;
            }

            public void setShareSummary(String shareSummary) {
                this.shareSummary = shareSummary;
            }

            public String getLogin() {
                return login;
            }

            public void setLogin(String login) {
                this.login = login;
            }

            public String getAttribute() {
                return attribute;
            }

            public void setAttribute(String attribute) {
                this.attribute = attribute;
            }

            public HeadBean getHead() {
                return head;
            }

            public void setHead(HeadBean head) {
                this.head = head;
            }

            public List<BodysBean> getBodys() {
                return bodys;
            }

            public void setBodys(List<BodysBean> bodys) {
                this.bodys = bodys;
            }

            public List<OperationsBean> getOperations() {
                return operations;
            }

            public void setOperations(List<OperationsBean> operations) {
                this.operations = operations;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public static class HeadBean {
                /**
                 * iconUrl : null
                 * iconName : 我要贷款1
                 */

                private String iconUrl;
                private String iconName;

                public String getIconUrl() {
                    return iconUrl;
                }

                public void setIconUrl(String iconUrl) {
                    this.iconUrl = iconUrl;
                }

                public String getIconName() {
                    return iconName;
                }

                public void setIconName(String iconName) {
                    this.iconName = iconName;
                }
            }

            public static class BodysBean implements Serializable {
                /**
                 * imageUrl : https://www.51geshui.com/CMS/articleImage/1490606091055.png
                 * url : https://www.baidu.com
                 */

                private String imageUrl;
                private String url;
                private String type;
                private String title;
                private String summary;
                private String login;
                private String shareTitle;
                private String shareSummary;
                private String shareUrl;

                public String getShareTitle() {
                    return shareTitle;
                }

                public void setShareTitle(String shareTitle) {
                    this.shareTitle = shareTitle;
                }

                public String getShareSummary() {
                    return shareSummary;
                }

                public void setShareSummary(String shareSummary) {
                    this.shareSummary = shareSummary;
                }

                public String getLogin() {
                    return login;
                }

                public void setLogin(String login) {
                    this.login = login;
                }

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getSummary() {
                    return summary;
                }

                public void setSummary(String summary) {
                    this.summary = summary;
                }

                public String getShareUrl() {
                    return shareUrl;
                }

                public void setShareUrl(String shareUrl) {
                    this.shareUrl = shareUrl;
                }
            }

            public static class OperationsBean implements Serializable {
                /**
                 * title : 操作一
                 * url : https://www.baidu.com
                 */

                private String title;
                private String url;
                private String login;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getLogin() {
                    return login;
                }

                public void setLogin(String login) {
                    this.login = login;
                }
            }
        }
    }

    public static class NoticeBean implements Serializable {
        /**
         * noticeName : 人民的名义开播了
         * noticeUrl : www.baidu.com
         */

        private String noticeName;
        private String noticeUrl;

        public String getNoticeName() {
            return noticeName;
        }

        public void setNoticeName(String noticeName) {
            this.noticeName = noticeName;
        }

        public String getNoticeUrl() {
            return noticeUrl;
        }

        public void setNoticeUrl(String noticeUrl) {
            this.noticeUrl = noticeUrl;
        }
    }

    public static class BannersBean implements Serializable {
        /**
         * name : 测试sort
         * imgUrl : http://61.164.38.108:9091/picture/appBanner/1493263892830.jpg
         * isChain : yes
         * chainUrl : 测试sort
         * type : null
         * sort : 12
         */

        private String name;
        private String imgUrl;
        private String isChain;
        private String chainUrl;
        private Object type;
        private int sort;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getIsChain() {
            return isChain;
        }

        public void setIsChain(String isChain) {
            this.isChain = isChain;
        }

        public String getChainUrl() {
            return chainUrl;
        }

        public void setChainUrl(String chainUrl) {
            this.chainUrl = chainUrl;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
