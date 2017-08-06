package com.example.calvin.workout;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calvin.workout.data.Contract;

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
        void onItemClick(int pos, String name, long id, String time);
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
        String exercise;
        TextView exerciseTime;
        String time;
        long id;

        ItemHolder(View view) {
            super(view);
            exerciseName = (TextView) view.findViewById(R.id.exerciseName);
            exerciseTime = (TextView)view.findViewById(R.id.exerciseTime);

            view.setOnClickListener(this);
        }

        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_USER_WORKOUT._ID));

            exercise = cursor.getString(cursor.getColumnIndex(Contract.TABLE_USER_WORKOUT.COLUMN_NAME_EXERCISE));
            exerciseName.setText(exercise);

            time = cursor.getString(cursor.getColumnIndex(Contract.TABLE_USER_WORKOUT.COLUMN_NAME_TIME));
            exerciseTime.setText(time+" Seconds");

            holder.itemView.setTag(id);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos,exercise,id,time);
        }
    }

}
