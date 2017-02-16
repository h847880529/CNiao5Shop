package com.example.hwg.cniao5shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hwg.cniao5shop.R;
import com.example.hwg.cniao5shop.bean.Category;

import java.util.List;

/**
 * Created by hwg on 2017/2/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private LayoutInflater mInflater ;

    private List<Category> mList ;

    public CategoryAdapter(List<Category> list){
        mList = list ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext()) ;
        View view = mInflater.inflate(R.layout.template_category_list, null) ;

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Category category = mList.get(position) ;

        holder.mCategoryText.setText(category.getName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mCategoryText ;

        public ViewHolder(View itemView) {
            super(itemView);

            mCategoryText = (TextView) itemView.findViewById(R.id.category_text) ;
        }
    }
}
