package com.itheima.multipletypelistviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<MultiTypeNewsBean.DataBean.NewsBean> mNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list_view);
        loadDataFromServer();
    }

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
}
