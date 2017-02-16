package com.example.hwg.cniao5shop.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hwg.cniao5shop.R;
import com.example.hwg.cniao5shop.bean.Wares;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

/**
 * Created by hwg on 2017/2/15.
 */

public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.ViewHolder> {

    private LayoutInflater mInflater ;

    private List<Wares> mList ;

    public HotWaresAdapter(List<Wares> list){
        mList = list ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext()) ;
        View view = mInflater.inflate(R.layout.template_hot_wares , null) ;


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wares wares = getData(position);

//        holder.draweeView.setImageURI(Uri.parse(wares.getImgUrl()));
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(wares.getImgUrl()))
                .setProgressiveRenderingEnabled(true)
                .build() ;

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .build() ;
        holder.draweeView.setController(controller);

        holder.hot_title.setText(wares.getName());
        holder.hot_price.setText("￥"+wares.getPrice());

    }

    private Wares getData(int position){
        return mList.get(position) ;
    }

    public List<Wares> getDatas(){

        return  mList;
    }

    public void clearData(){

        mList.clear();
        notifyItemRangeRemoved(0,mList.size());
    }

    public void addData(List<Wares> datas){

        addData(0,datas);
    }

    public void addData(int position,List<Wares> datas){

        if(datas !=null && datas.size()>0) {
            mList.addAll(datas);
            notifyItemRangeChanged(position, mList.size());
        }
    }


    @Override
    public int getItemCount() {
        if(mList!=null && mList.size()>0)
            return mList.size();

        return 0;
    }




    class ViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView draweeView ;
        TextView hot_title ;
        TextView hot_price ;

        public ViewHolder(View itemView) {
            super(itemView);

            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.drawee_view) ;
            hot_title = (TextView) itemView.findViewById(R.id.hot_title) ;
            hot_price = (TextView) itemView.findViewById(R.id.hot_price) ;
        }
    }
}
