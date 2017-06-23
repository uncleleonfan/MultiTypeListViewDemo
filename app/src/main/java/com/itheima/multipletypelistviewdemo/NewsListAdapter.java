package com.itheima.multipletypelistviewdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Leon on 2017/6/22.
 */

public class NewsListAdapter extends BaseAdapter {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_MULTI_IMAGES = 1;

    private Context mContext;
    private List<MultiTypeNewsBean.DataBean.NewsBean> mList;

    public NewsListAdapter(Context context, List<MultiTypeNewsBean.DataBean.NewsBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //如果是普通类型
        if (getItemViewType(position) == TYPE_NORMAL) {
            NormalViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.view_news_list_item, null);
                viewHolder = new NormalViewHolder(convertView);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (NormalViewHolder) convertView.getTag();
            }

            MultiTypeNewsBean.DataBean.NewsBean newsBean = mList.get(position);
            viewHolder.mTitle.setText(newsBean.getTitle());
            viewHolder.mPublishTime.setText(newsBean.getPubdate());
            Glide.with(mContext).load(newsBean.getListimage()).into(viewHolder.mImageView);
        } else {//如果是多图片类型
            MultiImagesViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.view_multi_image_list_item, null);
                viewHolder = new MultiImagesViewHolder(convertView);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (MultiImagesViewHolder) convertView.getTag();
            }

            MultiTypeNewsBean.DataBean.NewsBean newsBean = mList.get(position);
            viewHolder.mTitle.setText(newsBean.getTitle());
            viewHolder.mPublishTime.setText(newsBean.getPubdate());
            Glide.with(mContext).load(newsBean.getListimage1()).into(viewHolder.mImageView1);
            Glide.with(mContext).load(newsBean.getListimage2()).into(viewHolder.mImageView2);
            Glide.with(mContext).load(newsBean.getListimage3()).into(viewHolder.mImageView3);
        }

        ScaleAnimation scaleAnimation = new ScaleAnimation
                (0.7f, 1.0f, 0.7f, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        scaleAnimation.setDuration(500);
        convertView.startAnimation(scaleAnimation);

        return convertView;
    }


    /**
     * 返回对应位置item的类型
     */
    @Override
    public int getItemViewType(int position) {
        String type = mList.get(position).getType();
        return Integer.parseInt(type);
    }

    /**
     * 返回Item的类型个数
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }


    private class NormalViewHolder {
        public TextView mTitle;
        public TextView mPublishTime;
        public ImageView mImageView;

        public NormalViewHolder(View root) {
            mTitle = (TextView) root.findViewById(R.id.title);
            mPublishTime = (TextView) root.findViewById(R.id.publish_time);
            mImageView = (ImageView) root.findViewById(R.id.image_view);
        }
    }

    private class MultiImagesViewHolder {
        public TextView mTitle;
        public TextView mPublishTime;
        public ImageView mImageView1;
        public ImageView mImageView2;
        public ImageView mImageView3;

        public MultiImagesViewHolder(View root) {
            mTitle = (TextView) root.findViewById(R.id.title);
            mPublishTime = (TextView) root.findViewById(R.id.publish_time);
            mImageView1 = (ImageView) root.findViewById(R.id.image_view1);
            mImageView2 = (ImageView) root.findViewById(R.id.image_view2);
            mImageView3 = (ImageView) root.findViewById(R.id.image_view3);
        }
    }
}
