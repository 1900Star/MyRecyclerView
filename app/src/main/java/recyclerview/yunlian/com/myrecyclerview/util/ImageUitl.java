package recyclerview.yunlian.com.myrecyclerview.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Stran on 2017/2/24.
 */

public class ImageUitl {

    static        String   TAG       = "ImageUitl";
    public static String[] picUrlArr = {"http://img.mp.itc.cn/upload/20160921/e2539eff010449e8a04c0387e1c275c7_th.jpeg",
                                        "http://pic8.nipic.com/20100623/2568996_083300720588_2.jpg",
                                        "http://pic8.nipic.com/20100623/2568996_083301157944_2.jpg",
                                        "http://www.asahi.com/showbiz/gallery/20110309sada/images/home03.jpg",
                                        "http://s9.sinaimg.cn/middle/64df0f90gc499680089f8&690",
                                        "http://img4.imgtn.bdimg.com/it/u=4199968251,2321509994&fm=214&gp=0.jpg"};
    private static Bitmap bm;

    public ImageUitl()

    {}


    public static Bitmap getGlidLoadPic(Context context)

    {
        Random       random = new Random();
        final String url = picUrlArr[random.nextInt(picUrlArr.length)];

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();


                Request request = new Request.Builder().url(url)
                                                       .build();
                Response response = null;
                try {
                    response = client.newCall(request)
                                     .execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InputStream is = response.body()
                                         .byteStream();
                bm = BitmapFactory.decodeStream(is);


            }
        }).start();
        return bm;


    }


}
