package recyclerview.yunlian.com.myrecyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import recyclerview.yunlian.com.myrecyclerview.bean.ItemData;
import recyclerview.yunlian.com.myrecyclerview.util.ImageUitl;
import recyclerview.yunlian.com.myrecyclerview.view.SwipeItemLayout;

import static recyclerview.yunlian.com.myrecyclerview.util.ImageUitl.getGlidLoadPic;

/**
 * Created by Stran on 2017/2/24.
 */
public class MainActivity
        extends AppCompatActivity

{

    private     RecyclerView        mRv;
    private     ArrayList<ItemData> mList;
    private     ArrayList<String>   mLists;

    private String[] nameArr = {"1900",
                                "Star",
                                "Smartisan",
                                "1900",
                                "Star",
                                "Smartisan"};
    private String[] desArr  = {"纯电动汽车领头羊are used as an antiseptic and antispasmodic (for pain associated with rheumatism – mixed with alcohol) and as a remedy against various infections of the throat and pharynx.特斯迪",
                                "到目前为止，电动车已被证明销售相当缓慢，当然特斯拉的Model S是个例外。这使得电动汽车硬",
                                "e-SUV也许是最有可能盈利的纯电动汽车。目前，盈利对于包括特斯拉在内的大批电动汽车制造商都是一个难题。不过SUV可能是个机会，消费",
                                "这厢ofo和摩拜们正上演着攻城略地的激情大戏，那厢共享汽车已是剑拔弩张。日前北京市交通委相关负责人接受媒体采访时表示，北京将推进分时租赁汽车网点布局，年底前分时租赁汽车预计达到2000辆。",
                                "政策的支持，资本的热捧固然是好事，但共享汽车这个弄潮儿的处境仍然相当尴尬。在北京政府明确表示推进共享汽车前，电动汽车分时",
                                "Approximately one in ten people are paying a visit to the doctor because they are experiencing feelings of tension, anxiety or headaches."};
    private ItemData mData;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RefreshAdapter mRefrshAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initListere();
    }



    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.recycler);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

    }

    private void initData()
            throws IOException
    {
        //设置下拉刷新颜色
                mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.RED);
        mList = new ArrayList<>();
        //模拟数据
        for (int i = 0; i < nameArr.length; i++) {

            String name = nameArr[i];
            String des  = desArr[i];

            Bitmap bitmap = ImageUitl.getGlidLoadPic(this);

            mData = new ItemData(bitmap, name, des);


        }
        mList.add(mData);


        initRvData();
    }

    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    private void initRvData() {
        mRefrshAdapter = new RefreshAdapter(this, mList);


        mRv.setHasFixedSize(true);
        mRv.setItemAnimator(new DefaultItemAnimator());
        //垂直
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        //Grid布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        StaggeredGridLayoutManager manage = new StaggeredGridLayoutManager(2,
                                                                           StaggeredGridLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);
        mRv.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(
                getApplicationContext()));
        mRv.setAdapter(mRefrshAdapter);
    }

    private void initListere() {

        initPullRefresh();
        initLoadMore();

    }


    private void initPullRefresh() {
                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //模拟网络请求数据
                                ArrayList<ItemData> headDatas = new ArrayList<>();
                                //                        for (int i = 10; i < 20; i++) {
                                //                            headDatas.add(String.format(Locale.CHINA, "第%03d条数据", i));
                                //                        }

                                //模拟数据
                                for (int i = 0; i < nameArr.length; i++) {

                                    String name = nameArr[i];
                                    String des  = desArr[i];

                                    Bitmap icon = null;
                                    icon = getGlidLoadPic(getApplicationContext());
                                    mData = new ItemData(icon, name, des);

                                    headDatas.add(i, mData);
                                }
                                mRefrshAdapter.AddHeader(headDatas);
                                //刷新完成
                                mSwipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(MainActivity.this,
                                               "更新了 " + headDatas.size() + " 条目数据",
                                               Toast.LENGTH_SHORT)
                                     .show();
                            }
                        }, 1000);


                    }
                });
    }

    private void initLoadMore() {
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem + 1 == mRefrshAdapter.getItemCount()) {
                    mRefrshAdapter.changeMoreStatus(mRefrshAdapter.LOADING_MORE);
                    //请求数据
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<ItemData> headDatas = new ArrayList<>();
                            //                            for (int i = 0; i < 10; i++) {
                            //                                headDatas.add(String.format(Locale.CHINA, "第%03d条数据", i));
                            //                            }
                            ImageView imageView = new ImageView(getApplicationContext());
                            //模拟数据
                            for (int i = 0; i < nameArr.length; i++) {

                                String name = nameArr[i];
                                String des  = desArr[i];

                                Bitmap icon = null;
                                icon = getGlidLoadPic(getApplicationContext());
                                mData = new ItemData(icon, name, des);

                                headDatas.add(i, mData);
                            }

                            mRefrshAdapter.AddFooter(headDatas);
                            //将状态设置为加载更多
                            mRefrshAdapter.changeMoreStatus(mRefrshAdapter.PULLUP_LOAD_MORE);
                            //没有更多 数据
                            //                            mRefrshAdapter.changeMoreStatus(mRefrshAdapter.NO_LOAD_MORE);
                            //刷新完成
                            //                            mSwipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(MainActivity.this,
                                           "更新了 " + headDatas.size() + " 条目数据",
                                           Toast.LENGTH_SHORT)
                                 .show();
                        }
                    }, 1000);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                lastItem = manager.findLastVisibleItemPosition();
                //                lastItem=manager.findFirstVisibleItemPosition();

            }
        });
        //

    }
}
