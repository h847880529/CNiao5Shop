package com.example.hwg.cniao5shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hwg.cniao5shop.R;

/**
 * Created by hwg on 2017/2/13.
 */

public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.MyViewHolder>{

    private LayoutInflater mInflater ;
    private static int VIEW_TYPE_L = 0 ;
    private static int VIEW_TYPE_R = 1 ;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext()) ;

        if (viewType == VIEW_TYPE_R){
        }
        return null ;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    @Override
    public int getItemViewType(int position) {

        if (position % 2 == 0){
            return VIEW_TYPE_R ;
        }
        else return VIEW_TYPE_L ;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle ;
        ImageView imageViewBig ;
        ImageView imageViewSmallTop ;
        ImageView imageViewSmallBottom ;

        public MyViewHolder(View itemView) {
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);

        }
    }
}
