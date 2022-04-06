package com.example.b10.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.b10.Animal;
import com.example.b10.AnimalActivity;
import com.example.b10.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    final private Context mContext;
    final private List<Animal> mData;
    RequestOptions option;

    public RecyclerViewAdapter(Context mContext, List<Animal> mData){
        this.mContext = mContext;
        this.mData = mData;

        // yêu cầu thiết lại trong glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.animal_row_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AnimalActivity.class);
                intent.putExtra("animal_creature_name",mData.get(viewHolder.getAdapterPosition()).getCreature_name());
                intent.putExtra("animal_price",mData.get(viewHolder.getAdapterPosition()).getPrice());
                intent.putExtra("animal_content",mData.get(viewHolder.getAdapterPosition()).getContet());
                intent.putExtra("animal_image",mData.get(viewHolder.getAdapterPosition()).getImage());

                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_rating.setText(mData.get(position).getCreature_name());
        holder.tv_studio.setText(mData.get(position).getPrice());
        //Tải hình ảnh biểu mẫu trên internet và thiết lập thành chế độ xem hình ảnh bằng cách sử dụng lướt
        Glide.with(mContext).load(mData.get(position).getImage()).apply(option).into(holder.img_thumbnall);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_rating;
        TextView tv_studio;
        ImageView img_thumbnall;
        LinearLayout view_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_rating = itemView.findViewById(R.id.rating);
            tv_studio = itemView.findViewById(R.id.studio);
            img_thumbnall = itemView.findViewById(R.id.thumbnail);
            view_container = itemView.findViewById(R.id.containers);

        }
    }
}
