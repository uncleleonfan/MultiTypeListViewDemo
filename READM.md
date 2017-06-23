# 添加依赖 #
    compile 'com.squareup.okhttp3:okhttp:3.8.1'
    compile 'com.google.code.gson:gson:2.8.0'
    compile 'com.github.bumptech.glide:glide:3.7.0'

# 加载多条目数据 #
    private void loadDataFromServer() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        String url = "http://10.0.2.2:8080/zhbj/multi_type.json";
        Request request = builder.get().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                MultiTypeNewsBean newsListBean = gson.fromJson(result, MultiTypeNewsBean.class);
                mNewsList = newsListBean.getData().getNews();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListView.setAdapter(new NewsListAdapter(MainActivity.this, mNewsList));
                    }
                });
            }
        });
    }

# 创建Adapter #
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
	
	}

# 创建不同类型Item的视图 #
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

        return convertView;
    }

# Item的动画 #

    ScaleAnimation scaleAnimation = new ScaleAnimation
            (0.7f, 1.0f, 0.7f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
    scaleAnimation.setInterpolator(new OvershootInterpolator());
    scaleAnimation.setDuration(500);
    convertView.startAnimation(scaleAnimation);