package com.example.todoboom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/***
 * the main activity of the app. in this activity the user can add todos and mark them as done.
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<TodoItem> items;
    AlertDialog deleteDialog;
    TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.todo_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new TodoAdapter();
        items = TodoListPreferences.restoreSavedData(this);
        adapter.setTodos(items);
        Log.e("number of todo items ", String.valueOf(items.size()));
        recyclerView.setAdapter(adapter);

        adapter.onTodoListener = new TodoAdapter.OnTodoListener() {
            @Override
            public void onTodoClick(TodoItem item) {
                if (!item.isTodoDone()){
                    item.markAsDone();
                    adapter.setTodos(items);
                    Toast.makeText(getApplicationContext(), "TODO " + item.getTodoDescription() + " is now done. BOOM!",
                            Toast.LENGTH_SHORT).show();
                    TodoListPreferences.saveTodoList(MainActivity.this, items);
                }

            }

            @Override
            public void onTodoLongClick(TodoItem item) {
                final TodoItem pos = item;
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setMessage("Delete this todo?");
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        items.remove(pos);
                        adapter.setTodos(items);
                    }
                });
                TodoListPreferences.saveTodoList(MainActivity.this, items);
                deleteDialog= dialog.create();
                deleteDialog.show();

            }
        };
    }


    /***
     * this method adds a new to-do item to list of todos
     * @param item a string added by the user
     */
    private void addTodoItem(TodoItem item){
        Log.i("add item: ", item.getTodoDescription() + item.isTodoDone());
        items.add(item);
        adapter.setTodos(items);
        TodoListPreferences.saveTodoList(this, items);
    }

    /***
     * this method is activated when the user clicks the "create" button.
     * adds a new to-do item to the recycler
     * @param v the button view
     */
    public void onCreateButtonClicked(View v){
        EditText inputText = findViewById(R.id.input_text);
        String value = inputText.getText().toString();
        if (value.isEmpty()){
            Toast.makeText(getApplicationContext(),"Oops! you can't create an empty todo",Toast.LENGTH_SHORT).show();
        }
        else{
            addTodoItem(new TodoItem(value));
        }
        inputText.getText().clear();
    }

}
