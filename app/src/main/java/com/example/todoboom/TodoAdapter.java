package com.example.todoboom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/***
 * this class is an adapter for the "to do" recycler view
 */
public class TodoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> items;
    private OnTodoListener onTodoListener;

    public TodoAdapter(Context context, ArrayList<String> items, OnTodoListener onTodoListener){
        this.context = context;
        this.items = items;
        this.onTodoListener = onTodoListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.todo_row, parent, false);
        return new TodoItem(row, onTodoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TodoItem)holder).todoText.setText(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /***
     * this class represents a single to-do item in the recycler view
     */
    public class TodoItem extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView todoText;
        OnTodoListener onTodoListener;
        boolean isDone = false;

        public TodoItem(@NonNull View itemView, OnTodoListener onTodoListener) {
            super(itemView);
            todoText = itemView.findViewById(R.id.todo_text);
            this.onTodoListener = onTodoListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (!isDone){
                onTodoListener.onTodoClick(getAdapterPosition());
                isDone = true;
            }
        }
    }

    /***
     * this is an interface for a clickable item in the recycler view
     */
    public interface OnTodoListener{
        void onTodoClick(int position);
    }
}
