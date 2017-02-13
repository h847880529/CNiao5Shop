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
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.hwg.cniao5shop.R;
import com.example.hwg.cniao5shop.adapter.HomeCatgoryAdapter;
import com.example.hwg.cniao5shop.bean.HomeCategory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ivan on 15/9/25.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment" ;
    private SliderLayout mSliderLayout ;
    private PagerIndicator mPagerIndicator ;
    private RecyclerView mRecyclerView ;
    private HomeCatgoryAdapter mAdapter ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home , null) ;
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider) ;
        mPagerIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator) ;

        initSlider();

        initRecyclerView(view);
        return  view ;
    }

    private void initRecyclerView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView) ;

        List<HomeCategory> list = new ArrayList<>(15);
        HomeCategory category = new HomeCategory("热门活动",R.drawable.img_big_1,R.drawable.img_1_small1,R.drawable.img_1_small2);
        list.add(category);

        category = new HomeCategory("有利可图",R.drawable.img_big_4,R.drawable.img_4_small1,R.drawable.img_4_small2);
        list.add(category);
        category = new HomeCategory("品牌街",R.drawable.img_big_2,R.drawable.img_2_small1,R.drawable.img_2_small2);
        list.add(category);

        category = new HomeCategory("金融街 包赚翻",R.drawable.img_big_1,R.drawable.img_3_small1,R.drawable.imag_3_small2);
        list.add(category);

        category = new HomeCategory("超值购",R.drawable.img_big_0,R.drawable.img_0_small1,R.drawable.img_0_small2);
        list.add(category);

        mAdapter = new HomeCatgoryAdapter(list) ;
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

    }


    private void initSlider(){
        TextSliderView textSliderView1 = new TextSliderView(this.getActivity());
        textSliderView1
                .description("新品推荐")
                .image("http://m.360buyimg.com/mobilecms/s300x98_jfs/t2416/102/20949846/13425/a3027ebc/55e6d1b9Ne6fd6d8f.jpg");
        //设置sliderview的点击事件
        textSliderView1.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(), "新品推荐", Toast.LENGTH_SHORT).show();
            }
        });

        TextSliderView textSliderView2 = new TextSliderView(this.getActivity());
        textSliderView2
                .description("时尚男装")
                .image("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1507/64/486775407/55927/d72d78cb/558d2fbaNb3c2f349.jpg") ;
        textSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(), "时尚男装", Toast.LENGTH_SHORT).show();
            }
        }) ;

        TextSliderView textSliderView3 = new TextSliderView(this.getActivity());
        textSliderView3
                .description("家电秒杀")
                .image("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1363/77/1381395719/60705/ce91ad5c/55dd271aN49efd216.jpg") ;
        textSliderView3.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(), "家电秒杀", Toast.LENGTH_SHORT).show();
            }
        }) ;

        mSliderLayout.addSlider(textSliderView1);
        mSliderLayout.addSlider(textSliderView2);
        mSliderLayout.addSlider(textSliderView3);

        //设置下标的样式
//        mSliderLayout.setCustomIndicator(mPagerIndicator);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //设置文字的动画
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        //设置切换动画
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        //设置间隔时间
        mSliderLayout.setDuration(3000);

        //设置SliderLayout的监听事件
        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG , "onPageScrolled") ;
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG , "onPageSelected") ;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(TAG , "onPageScrollStateChanged") ;
            }
        });



    }
}
