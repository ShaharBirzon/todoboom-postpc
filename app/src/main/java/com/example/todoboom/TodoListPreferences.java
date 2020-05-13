package com.example.todoboom;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TodoListPreferences {
    public static void saveTodoList(Context context, ArrayList<TodoItem> todoItems){
        SharedPreferences sp = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String listJson = gson.toJson(todoItems);
        editor.putString("todo list", listJson);
        editor.apply();
    }

    public static ArrayList<TodoItem> restoreSavedData(Context context){
        ArrayList<TodoItem> todoItems = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        String listJson = sp.getString("todo list", null);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<TodoItem>>() {}.getType();
        if (listJson != null){
            todoItems = gson.fromJson(listJson, listType);
        }

        return todoItems;
    }
}
