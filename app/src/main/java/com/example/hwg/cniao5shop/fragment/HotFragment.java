package com.example.hwg.cniao5shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.example.hwg.cniao5shop.Contants;
import com.example.hwg.cniao5shop.R;
import com.example.hwg.cniao5shop.adapter.decoration.DividerItemDecoration;
import com.example.hwg.cniao5shop.adapter.HotWaresAdapter;
import com.example.hwg.cniao5shop.bean.Page;
import com.example.hwg.cniao5shop.bean.Wares;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Ivan on 15/9/22.
 */
public class HotFragment extends Fragment{

    private int curPage=1;
    private int pageSize=28;

    private Gson mGson = new Gson() ;

    private Page<Wares> mPage ;
    private List<Wares> mWares ;

    private HotWaresAdapter mAdapter ;

    private RecyclerView mRecyclerView ;

    private MaterialRefreshLayout mRefreshLayout ;


    private static final int STATE_NORMAL = 0 ;//正常加载
    private static final int STATE_REFRESH = 1 ;//下拉刷新
    private static final int STATE_MORE = 2 ;//加载更多

    private int state = STATE_NORMAL ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_hot,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.hot_recyclerView) ;


        getData();

        return view ;

    }

    //获取热卖商品的信息
    private void getData(){
        String url = Contants.API.WARES_HOT + "?curPage="+curPage+"&pageSize="+pageSize ;

        OkHttpClient client = new OkHttpClient() ;
        Request request = new Request.Builder().get().url(url).build() ;

        Call call = client.newCall(request) ;

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string() ;
                mPage = mGson.fromJson(json , new TypeToken<Page<Wares>>(){}.getType()) ;
                mWares = mPage.getList() ;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showData();
                    }
                });
            }
        });

    }

    //展示数据
    private void showData(){
        mAdapter = new HotWaresAdapter(mWares) ;

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL_LIST));

    }

}
