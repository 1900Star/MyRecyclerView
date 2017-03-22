package recyclerview.yunlian.com.myrecyclerview.bean;

import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2016/7/8 14:13
 * 描述	      ${TODO}
 *
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-07-12 14:49:37 +0800 (星期二, 12 七月 2016) $
 * 更新描述   ${TODO}
 */
public class ItemInfoBean {
    public long   id;
    public String des;
    public String packageName;
    public float  stars;//2.5
    public String iconUrl;
    public String name;
    public String downloadUrl;
    public int    size;

    /*--------------- 详情页面里面额外的字段 ---------------*/

    public String                 author;// 黑马程序员
    public String                 date;// 2015-06-10
    public String                 downloadNum;//	40万+
    public String                 version;// 1.1.0605.0
    public List<ItemInfoSafeBean> safe;//Array
    public List<String>           screen;//Array

    public class ItemInfoSafeBean {
        public String safeDes;// 已通过安智市场安全检测，请放心使用
        public int    safeDesColor;//	0
        public String safeDesUrl;// app/com.itheima.www/safeDesUrl0.jpg
        public String safeUrl;// app/com.itheima.www/safeIcon0.jpg

        @Override
        public String toString() {
            return "ItemInfoSafeBean{" +
                    "safeDes='" + safeDes + '\'' +
                    ", safeDesColor=" + safeDesColor +
                    ", safeDesUrl='" + safeDesUrl + '\'' +
                    ", safeUrl='" + safeUrl + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ItemInfoBean{" +
                "id=" + id +
                ", des='" + des + '\'' +
                ", packageName='" + packageName + '\'' +
                ", stars=" + stars +
                ", iconUrl='" + iconUrl + '\'' +
                ", name='" + name + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", size=" + size +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", downloadNum='" + downloadNum + '\'' +
                ", version='" + version + '\'' +
                ", safe=" + safe +
                ", screen=" + screen +
                '}';
    }
}
