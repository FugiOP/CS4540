package com.example.calvin.workout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calvin.workout.models.FoodItem;

import java.util.ArrayList;

/**
 * Created by leona on 8/8/2017.
 */

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.FoodsItemViewHolder> {


    ArrayList<FoodItem> foodsData;
    ItemClickListener listener;
    View.OnLongClickListener longListener;

    public FoodsAdapter(ArrayList<FoodItem> foodsData, ItemClickListener listener) {
        this.foodsData = foodsData;
        this.listener =listener;
    }

    public interface ItemClickListener {
        void onItemClick(int itemIndex);
    }
    public interface OnLongClickListener {
        void onLongClick(int itemIndex);
    }


    @Override
    public FoodsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //checkout calorieitem
        View view = inflater.inflate(R.layout.food_item,parent,false);
        FoodsItemViewHolder holder = new FoodsItemViewHolder(view);
        return holder;


    }

    @Override
    public void onBindViewHolder (FoodsItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {

        return foodsData.size();

    }

    public  class FoodsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView item_name;
        TextView brand_name;
        TextView nf_calories;

        //  ImageView urlToImage;

        FoodsItemViewHolder(View view){
            super(view);
            item_name = (TextView)view.findViewById(R.id.item_name);
            brand_name= (TextView)view.findViewById(R.id.brand_name);
            nf_calories = (TextView)view.findViewById(R.id.nf_calories);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            FoodItem hits = foodsData.get(pos);
            item_name.setText(hits.getItem_name());
            brand_name.setText(hits.getBrand_name());
            nf_calories.setText(hits.getNf_calories());
            //nf_calories.setText(Math.round(Double.parseDouble(hits.getNf_calories()))+" cal");

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }
}