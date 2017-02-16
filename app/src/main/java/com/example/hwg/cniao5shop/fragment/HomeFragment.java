package com.example.hwg.cniao5shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.hwg.cniao5shop.Contants;
import com.example.hwg.cniao5shop.R;
import com.example.hwg.cniao5shop.adapter.decoration.CardViewtemDecortion;
import com.example.hwg.cniao5shop.adapter.HomeCatgoryAdapter;
import com.example.hwg.cniao5shop.bean.Banner;
import com.example.hwg.cniao5shop.bean.Campaign;
import com.example.hwg.cniao5shop.bean.HomeCampaign;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Ivan on 15/9/25.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment" ;
    private SliderLayout mSliderLayout ;
    private PagerIndicator mPagerIndicator ;
    private RecyclerView mRecyclerView ;
    private HomeCatgoryAdapter mAdapter ;

    private Gson mGson = new Gson() ;
    private List<Banner> mBanner =new ArrayList<>();
    private List<HomeCampaign> mHomeCampaign = new ArrayList<>() ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home , null) ;
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider) ;
        mPagerIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator) ;

        requestImages();

        initRecyclerView(view);

        return  view ;
    }

    //获取轮播图片的资源
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
                    Log.i(TAG , "json"+json) ;
                    mBanner = mGson.fromJson(json,new TypeToken<List<Banner>>(){}.getType());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initSlider() ;
                        }
                    });
                }
            }
        });

    }

    //获取主页商品信息
    private void initRecyclerView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView) ;

        String homeCampaignUrl = Contants.API.CAMPAIGN_HOME ;

        OkHttpClient client = new OkHttpClient() ;

        Request request = new Request.Builder().url(homeCampaignUrl).get().build() ;

        Call call = client.newCall(request) ;

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String json = response.body().string() ;
                    mHomeCampaign = mGson.fromJson(json , new TypeToken<List<HomeCampaign>>(){}.getType()) ;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initDate(mHomeCampaign);
                        }
                    });
                }
            }
        });

    }

    //加载主页商品信息
    private void initDate(List<HomeCampaign> homeCampaigns){
        mAdapter = new HomeCatgoryAdapter(homeCampaigns , getActivity()) ;
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnCampaignClickListener(new HomeCatgoryAdapter.OnCampaignClickListener() {
            @Override
            public void onClick(View view, Campaign campaign) {
                Toast.makeText(getContext(), campaign.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.addItemDecoration(new CardViewtemDecortion());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

    }


    //加载轮播图片
    private void initSlider(){

        if (mBanner != null){
            for (Banner banner : mBanner){
                TextSliderView textSliderView = new TextSliderView(this.getActivity()) ;
                textSliderView
                        .image(banner.getImgUrl())
                        .description(banner.getName()) ;
                mSliderLayout.addSlider(textSliderView);
            }
        }

        //设置下标的样式
//        mSliderLayout.setCustomIndicator(mPagerIndicator);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        //设置文字的动画
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        //设置切换动画
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        //设置间隔时间
        mSliderLayout.setDuration(3000);

    }
}
