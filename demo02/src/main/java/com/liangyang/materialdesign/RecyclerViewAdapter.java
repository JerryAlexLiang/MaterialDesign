package com.liangyang.materialdesign;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 创建日期：2017/3/22 on 下午4:56
 * 描述:1.先创建ViewHolder(继承RecyclerView.ViewHolder)
 * 2.创建适配器(继承RecyclerView.Adapter<RecyclerView.ViewHolder>)
 * 3.注意：RecyclerView没有提供setOnItemClickListener这个回调，自己在Adapter中添加这个回调接口(可选)
 * 作者:yangliang
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<DataInfo> dataInfoList;
    private LayoutInflater mLayoutInflater;

    public RecyclerViewAdapter(Context mContext, List<DataInfo> dataInfoList) {
        this.mContext = mContext;
        this.dataInfoList = dataInfoList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 创建适配器的缓存容器
     * 1、创建一个ViewHolder，并且需要继承RecyclerView.ViewHolder
     * 2、其实创建的是一个适配器
     * Created by Jerry
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView imageView;
        TextView nameTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            imageView = (ImageView) itemView.findViewById(R.id.iv_image);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    /**
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.lv_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
//
//        //设置点击监听事件
//        /*
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Fruit fruit = mFruitList.get(position);
//                Intent intent = new Intent(mContext, FruitActivity.class);
//                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
//                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
//                mContext.startActivity(intent);
//            }
//        });
//         */
////        myViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText(mContext, "你点击了图片", Toast.LENGTH_SHORT).show();
////            }
////        });
        return myViewHolder;
    }

    /**
     * 将数据绑定到ViewHolder，设置值
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //数据映射
        DataInfo dataInfo = dataInfoList.get(position);
        holder.nameTextView.setText(dataInfo.getName());
        //加载图片
        Picasso.with(mContext).load(dataInfo.getImageId()).into(holder.imageView);


    }

    /**
     * 获取总的条目数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return dataInfoList.size();
    }
}
