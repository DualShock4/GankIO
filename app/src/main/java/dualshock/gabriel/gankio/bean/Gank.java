package dualshock.gabriel.gankio.bean;


import java.io.Serializable;
import java.util.ArrayList;

public class Gank {
    //    "_id": "584172ca421aa939befafafe",
//            "createdAt": "2016-12-02T21:10:34.47Z",
//            "desc": "Android\u8def\u7531\u52a8\u6001\u914d\u7f6e\u65b9\u6848\u2014\u2014\u53ef\u80fd\u662f\u6700\u7b80\u5355\u7684\u70ed\u66f4\u65b0\u5b9e\u73b0",
//            "publishedAt": "2016-12-13T11:42:38.536Z",
//            "source": "web",
//            "type": "Android",
//            "url": "http://www.sixwolf.net/blog/2016/12/02/%E7%83%AD%E6%9B%B4%E6%96%B0%E6%96%B9%E6%A1%88%E4%B9%8B%E8%B7%AF%E7%94%B1%E5%8A%A8%E6%80%81%E9%85%8D%E7%BD%AE/",
//            "used": true,
//            "who": "Tang Likang"
    public ArrayList<GankInfo> results;


    public class GankInfo implements Serializable{
        public String _id;
        public String publishedAt;
        public String url;
        public String who;
        public String desc;
    }
}
