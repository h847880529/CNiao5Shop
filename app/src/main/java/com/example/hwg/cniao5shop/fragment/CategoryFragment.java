package com.example.hwg.cniao5shop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.hwg.cniao5shop.Contants;
import com.example.hwg.cniao5shop.R;
import com.example.hwg.cniao5shop.adapter.CategoryAdapter;
import com.example.hwg.cniao5shop.adapter.CategoryWaresAdapter;
import com.example.hwg.cniao5shop.adapter.decoration.DividerItemDecoration;
import com.example.hwg.cniao5shop.bean.Banner;
import com.example.hwg.cniao5shop.bean.Category;
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
public class CategoryFragment extends Fragment {

    private int currPage=1;
    private int totalPage=1;
    private int pageSize=10;
    private long categoryId=0;


    private RecyclerView mRecyclerViewCategory ;
    private RecyclerView mRecyclerViewWares ;

    private SliderLayout mSliderLayout ;

    private List<Category> mCategory ;

    private List<Banner> mBanner ;

    private Page<Wares> mPage ;
    private List<Wares> mWares ;

    private Gson mGson = new Gson() ;

    private CategoryAdapter categoryAdapter ;
    private CategoryWaresAdapter categoryWaresAdapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category,container,false) ;

        mRecyclerViewCategory = (RecyclerView) view.findViewById(R.id.recyclerView_catgory) ;
        mRecyclerViewWares = (RecyclerView) view.findViewById(R.id.recyclerview_wares) ;
        mSliderLayout = (SliderLayout) view.findViewById(R.id.catgory_slider) ;

        requestCategory();

        requestImages();

        return  view;
    }

    //获取商品分类信息
    private void requestCategory(){
        String url = Contants.API.CATEGORY_LIST ;
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
                mCategory = mGson.fromJson(json , new TypeToken<List<Category>>(){}.getType()) ;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showCategoryData();

                    }
                });
            }
        });
    }

    //获取轮播图片数据
    private void requestImages() {
        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1" ;

        OkHttpClient client = new OkHttpClient() ;
        Request request = new Request.Builder().url(url).build() ;

        Call call = client.newCall(request) ;

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String json = response.body().string() ;
                    mBanner = mGson.fromJson(json,new TypeToken<List<Banner>>(){}.getType());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showSlider();
                        }
                    });
                }
            }
        });
    }


    private void requestWares(long categoryId){

        String url = Contants.API.CATEGORY_WARES_LIST + "categoryId=" + categoryId + "&curPage=" + currPage + "&pageSize=" + pageSize ;
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
                mPage = mGson.fromJson(json , new TypeToken<Page<Wares>>(){}.getType());
                mWares = mPage.getList() ;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showCategoryWares() ;
                    }
                });
            }
        });

    }


    //加载分类信息
    private void showCategoryData(){
        categoryAdapter = new CategoryAdapter(mCategory) ;

        mRecyclerViewCategory.setAdapter(categoryAdapter);

        mRecyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerViewCategory.setItemAnimator(new DefaultItemAnimator());

        mRecyclerViewCategory.addItemDecoration(new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL_LIST));
    }

    //加载轮播图片
    private void showSlider(){
        if (mBanner != null) {
            for (Banner banner : mBanner) {
                DefaultSliderView defaultSliderView = new DefaultSliderView(getContext()) ;
                defaultSliderView.image(banner.getImgUrl()).description(banner.getName()) ;
                mSliderLayout.addSlider(defaultSliderView);
            }
            //设置下标位置
            mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            //设置切换动画
            mSliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
            //设置间隔时间
            mSliderLayout.setDuration(3000);
        }
    }

    //显示二级菜单信息
    private void showCategoryWares(){
        categoryWaresAdapter = new CategoryWaresAdapter(mWares) ;

        mRecyclerViewWares.setAdapter(categoryWaresAdapter);

        mRecyclerViewWares.setLayoutManager(new GridLayoutManager(getContext() , 2));
    }
}



