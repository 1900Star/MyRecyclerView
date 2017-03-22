package recyclerview.yunlian.com.myrecyclerview.bean;

import android.graphics.Bitmap;

/**
 * Created by Stran on 2017/3/20.
 */

public class ItemData {
    private Bitmap bitmap;
    private String name;
    private String des;

    public ItemData() {
    }

    public ItemData(Bitmap bitmap, String name, String des) {
        this.bitmap = bitmap;
        this.name = name;
        this.des = des;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
