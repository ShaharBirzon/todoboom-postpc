package com.example.todoboom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/***
 * this class is an adapter for the "to do" recycler view
 */
public class TodoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<TodoItem> items = new ArrayList<>();
    public OnTodoListener onTodoListener;

    public void setTodos(ArrayList<TodoItem> itemList){
        items.clear();
        items.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.todo_row, parent, false);
        return new TodoItemHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TodoItem item = items.get(position);
        ((TodoItemHolder)holder).todoText.setText(item.getTodoDescription());
        if (item.isTodoDone()){
            ((TodoItemHolder)holder).doneImg.setVisibility(View.VISIBLE);
            ((TodoItemHolder)holder).todoText.setAlpha(0.4f);

        }
        else {
            ((TodoItemHolder)holder).doneImg.setVisibility(View.INVISIBLE);
            ((TodoItemHolder)holder).todoText.setAlpha(1f);
        }
        
        ((TodoItemHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onTodoListener.onTodoClick(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /***
     * this class represents a single to-do item in the recycler view
     */
    public class TodoItemHolder extends RecyclerView.ViewHolder{
        TextView todoText;
        ImageView doneImg;

        public TodoItemHolder(@NonNull View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.todo_text);
            doneImg = itemView.findViewById(R.id.done_image);
        }

    }

    /***
     * this is an interface for a clickable item in the recycler view
     */
    public interface OnTodoListener{
        void onTodoClick(TodoItem item);
    }
}

