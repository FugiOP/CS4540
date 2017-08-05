package com.example.calvin.workout;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by FugiBeast on 8/5/2017.
 */

public class RoutineListAdapter extends RecyclerView.Adapter<RoutineListAdapter.ItemHolder>{
    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "routinelistadapter";
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int pos);
    }

    public RoutineListAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView exerciseName;
        ImageView exerciseImage;
        TextView exerciseCalories;
        long id;

        ItemHolder(View view) {
            super(view);
            exerciseName = (TextView) view.findViewById(R.id.exerciseName);
            exerciseImage = (ImageView) view.findViewById(R.id.exerciseImage);
            exerciseCalories = (TextView)view.findViewById(R.id.exerciseCalories);
            view.setOnClickListener(this);
        }

        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));
            Log.d(TAG, "deleting id: " + id);

            exerciseName.setText(cursor.getString(cursor.getColumnIndex(Contract)));
            String img = cursor.getString(cursor.getColumnIndex(Contract));
            exerciseCalories.setText(cursor.getString(cursor.getColumnIndex(Contract)));
            if(img != null){
                Picasso.with(context).load(exerciseImage).into(exerciseImage);
            }
            holder.itemView.setTag(id);

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(cursor, pos);
        }
    }

}
