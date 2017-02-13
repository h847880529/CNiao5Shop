package com.example.hwg.cniao5shop;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.hwg.cniao5shop.bean.Tab;
import com.example.hwg.cniao5shop.fragment.CartFragment;
import com.example.hwg.cniao5shop.fragment.CategoryFragment;
import com.example.hwg.cniao5shop.fragment.HomeFragment;
import com.example.hwg.cniao5shop.fragment.HotFragment;
import com.example.hwg.cniao5shop.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Tab> mTabs = new ArrayList<>(5) ;

    private LayoutInflater mInflater ;
    private FragmentTabHost mTabHost ;
    private ImageView img ;
    private TextView text ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTab();
    }

    private void initTab(){
        Tab home = new Tab(R.string.home , R.drawable.selector_icon_home , HomeFragment.class) ;
        Tab tab_hot = new Tab(R.string.hot , R.drawable.selector_icon_hot , HotFragment.class) ;
        Tab tab_category = new Tab(R.string.catagory , R.drawable.selector_icon_category , CategoryFragment.class) ;
        Tab tab_cart = new Tab(R.string.cart , R.drawable.selector_icon_cart , CartFragment.class) ;
        Tab tab_mine = new Tab(R.string.mine , R.drawable.selector_icon_user , MineFragment.class) ;

        mTabs.add(home);
        mTabs.add(tab_hot);
        mTabs.add(tab_category);
        mTabs.add(tab_cart);
        mTabs.add(tab_mine);

        mInflater = LayoutInflater.from(this) ;

        mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost) ;
        //设置替换那个布局
        mTabHost.setup(this , getSupportFragmentManager() , R.id.realtabcontent);


        for (Tab tab : mTabs){
            //生成一个TabSpes对象，这个对象代表了一个Tab页
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle())) ;
            //设置该页的indicator(指示器)，设置该Tab页的名字，图标，以及布局
            tabSpec.setIndicator(buildIndicator(tab)) ;
            //将设置好的TabSpec对象添加到TabHost中
            mTabHost.addTab(tabSpec , tab.getFragment() , null);
        }

        //去掉分隔线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        //设置默认选择第一个
        mTabHost.setCurrentTab(0);
    }

    //
    private View buildIndicator(Tab tab){
        View view = mInflater.inflate(R.layout.tab_indicator , null) ;
        img = (ImageView) view.findViewById(R.id.icon_tab) ;
        text = (TextView) view.findViewById(R.id.txt_indicator) ;

        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());

        return view ;
    }
}
