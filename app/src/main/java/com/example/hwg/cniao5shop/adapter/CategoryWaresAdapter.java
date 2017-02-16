package com.example.hwg.cniao5shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hwg.cniao5shop.R;
import com.example.hwg.cniao5shop.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by hwg on 2017/2/16.
 */

public class CategoryWaresAdapter extends RecyclerView.Adapter<CategoryWaresAdapter.ViewHolder>{

    private LayoutInflater mInflater ;

    private List<Wares> mList ;

    public CategoryWaresAdapter(List<Wares> list){
        mList = list ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext()) ;
        View view = mInflater.inflate(R.layout.template_category_wares , null) ;

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wares ware = mList.get(position) ;

        holder.draweeView.setImageURI(ware.getImgUrl());
        holder.mWaresTitle.setText(ware.getName());
        holder.mWaresPrice.setText("￥" + ware.getPrice());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView draweeView ;
        private TextView mWaresTitle ;
        private TextView mWaresPrice ;

        public ViewHolder(View itemView) {
            super(itemView);

            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.category_wares_img) ;
            mWaresTitle = (TextView) itemView.findViewById(R.id.category_wares_title) ;
            mWaresPrice = (TextView) itemView.findViewById(R.id.category_wares_price) ;

        }
    }


}
