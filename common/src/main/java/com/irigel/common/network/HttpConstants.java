package com.irigel.common.network;

/**
 *
 * @author zhangqing
 * @date 2/20/21
 */
public class HttpConstants {
    public static final String HEADER_ACCESS_KEY = "ak";
    public static final String TAG_MODE_HOST = "modeHost";
    public static final String TAG_UNSPLASH_HOST = "unSplashHost";

    /**
     * Host
     */
    public static final class OnLineUrl {
        public static final String UNSPLASH_HOST = "https://api.unsplash.com/";
        public static final String MODE_HOST = "https://fig-service.atcloudbox.com/";
    }

    /**
     * Host
     */
    public static final class DevUrl {
        public static final String MODE_HOST = "http://52.83.76.155:8136/";
    }

    /**
     * Key
     */
    public static final class Key {
        public static final String ACCESS_KEY = "5k3f3VwwMXr7QAG3Dey0qHhsOP0Z4Dct";
        public static final String UNSPLASH_KEY = "tU03by1oIgUu8BDpMhPSejFa7YjERFNj3n43c2TXn5c";
        public static final String UNSPLASH_SECRET = "ydtMVdJ3WDW7lhgjmqKVf3VufBZRJBAWBRxNxhW_O78";

    }

    /**
     * 参数名称
     */
    public static final class Param {
        public static final String TOKEN = "token";
        public static final String TYPE = "type";
        public static final String PAGE = "page";
        public static final String PAGE_SIZE = "per_page";
        public static final String CLIENTID = "client_id";
    }

    /**
     * URL 中路径部分
     */
    public static final class Path {
        public static final String SETTING = "/settings/getUserSettings";
        public static final String TOPIC =  "topic";
    }
}