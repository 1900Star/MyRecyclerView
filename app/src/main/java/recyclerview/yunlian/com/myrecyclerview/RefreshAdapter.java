package recyclerview.yunlian.com.myrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import recyclerview.yunlian.com.myrecyclerview.bean.ItemData;


/**
 * Created by Stran on 2016/11/5.
 */

public class RefreshAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>


{
    private View mView;
    private String TAG = "RefreshAdapter";
    private Context mContext;

    private ArrayList<ItemData> mList;

    private static final int TYPE_ITEM   = 0;
    private static final int TYPE_FOOTER = 1;

    //上拉加载更多
    public static final int    PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int    LOADING_MORE     = 1;
    //没有加载更多 隐藏
    public static final int    NO_LOAD_MORE     = 2;
    public              String url              = "http://192.168.31.210:8080/GooglePlayServer/game?index=0";

    //    public              String url              = "http://192.168.31.210:8080/market/newproduct?page=1&pageNum=10&orderby=saleDown";
    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;
    private final LayoutInflater mInflater;


    public RefreshAdapter(Context context, ArrayList<ItemData> list) {
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            mView = mInflater.from(parent.getContext())
                             .inflate(R.layout.item_recycler, parent, false);

            final MyViewHolder viewHolder = new MyViewHolder(mView);
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {

            mView = mInflater.from(parent.getContext())
                             .inflate(R.layout.load_more_footview_layout, parent, false);

            LoadMoreViewHolder moreViewHolder = new LoadMoreViewHolder(mView);
            return moreViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder itermHolder = (MyViewHolder) holder;

            ItemData data = mList.get(position);

            //TODO
            //绑定Icon
            //
            itermHolder.mIcon.setImageBitmap(data.getBitmap());


            //绑定name
            itermHolder.mName.setText(data.getName());
            //绑定内容
            itermHolder.mDes.setText(data.getDes());

        } else if (holder instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
            switch (mLoadMoreStatus) {

                case PULLUP_LOAD_MORE:
                    if (mList.size() == 0) {
                        //                        loadMoreViewHolder.loadLayout.setVisibility(View.GONE);
                    }
                    loadMoreViewHolder.tvLoadText.setText("上拉加载更多...");


                    break;
                case LOADING_MORE:
                    loadMoreViewHolder.tvLoadText.setText("正加载更多...");
                    break;
                case NO_LOAD_MORE:
                    loadMoreViewHolder.loadLayout.setVisibility(View.GONE);

                    break;
                default:
                    break;
            }
        }

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void AddHeader(List<ItemData> items) {
        mList.addAll(0, items);
        notifyDataSetChanged();
    }

    public void AddFooter(List<ItemData> items) {
        mList.addAll(0, items);
        notifyDataSetChanged();


    }

    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();

    }


    public class MyViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener
    {
        private final RelativeLayout mItemMain;
        private final View           mBtnDelete;

        private final TextView  mName;
        private       ImageView mIcon;
        private final TextView  mDes;
//        private final View mMark;
        private final View mStick;

        //普通的Item
        //        public MyViewHolder(View itemView) {
        //            super(itemView);
        //            mTv = (TextView) itemView.findViewById(R.id.tv);
        //
        //            initlister();
        //        }

        /**
         * 带滑动删除的Item
         * @param itemView
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            mDes = (TextView) itemView.findViewById(R.id.tv_des);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mItemMain = (RelativeLayout) itemView.findViewById(R.id.main);
            mBtnDelete = itemView.findViewById(R.id.delete);
//            mMark = itemView.findViewById(R.id.mark);
            mStick = itemView.findViewById(R.id.stick);


            initlister();
        }

        //从获取数据
        private void initlister() {
            mItemMain.setOnClickListener(this);
            mItemMain.setOnLongClickListener(this);
            mBtnDelete.setOnClickListener(this);
//            mMark.setOnClickListener(this);
            mStick.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete:
                    int position = getAdapterPosition();
                    mList.remove(position);
                    notifyItemRemoved(position);
                    break;
//                case R.id.mark:
//
//                    break;
                case R.id.stick:
                    break;
                default:
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


    public class LoadMoreViewHolder
            extends RecyclerView.ViewHolder
    {
        private LinearLayout loadLayout;
        private ProgressBar  pbLoad;
        private TextView     tvLoadText;


        public LoadMoreViewHolder(View itemView) {
            super(itemView);


            loadLayout = (LinearLayout) itemView.findViewById(R.id.loadLayout);
            pbLoad = (ProgressBar) itemView.findViewById(R.id.pbLoad);
            tvLoadText = (TextView) itemView.findViewById(R.id.tvLoadText);


        }

    }


}
