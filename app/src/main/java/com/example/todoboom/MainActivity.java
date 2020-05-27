package com.example.todoboom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/***
 * the main activity of the app. in this activity the user can add todos and mark them as done.
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<TodoItem> items = new ArrayList<>();

    TodoAdapter adapter;
    TodoItem curItem;
//    TodoFirestoreManager firestoreManager;
    FirebaseFirestore db;
    private HashMap<String, TodoItem> allTodos = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        firestoreManager = new TodoFirestoreManager();
        refreshDataWithOneTimeQuery();

    }

    public void initAdapter(){
        recyclerView = findViewById(R.id.todo_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new TodoAdapter();
//        items = TodoListPreferences.restoreSavedData(this);
        HashMap dbItems = getAllTodos();
        for (Object entry: dbItems.values()) {
            items.add((TodoItem)entry);
        }
        adapter.setTodos(items);
        Log.e("number of todo items ", String.valueOf(items.size()));
        recyclerView.setAdapter(adapter);


        adapter.onTodoListener = new TodoAdapter.OnTodoListener() {
            @Override
            public void onTodoClick(TodoItem item) {
                Intent intent = new Intent(MainActivity.this, EditTodoActivity.class);
                intent.putExtra("todo_content", item.getTodoDescription());
                intent.putExtra("todo_done", item.isTodoDone());
                intent.putExtra("last_modified", item.getUpdateDateTime());
                intent.putExtra("creation_date", item.getCreationDateTime());
                curItem = item;
                startActivityForResult(intent, 123);
            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                if(data.getBooleanExtra("todo_deleted", false)){
                    items.remove(curItem);
                    adapter.setTodos(items);
                    deleteTodo(curItem);
                    TodoListPreferences.saveTodoList(this, items);

                }
                else{
                    curItem.changeTodoDescription(data.getStringExtra("todo_content"));
                    curItem.setUpdateDate();
                    if(data.getBooleanExtra("todo_done", false)){
                        curItem.markAsDone();
                        adapter.setTodos(items);
                        updateTodo(curItem);
                        TodoListPreferences.saveTodoList(this, items);
                    }
                    else{
                        curItem.markAsNotDone();
                        adapter.setTodos(items);
                        updateTodo(curItem);
                        TodoListPreferences.saveTodoList(this, items);
                    }
                }
            }

        }
    }

    /***
     * this method adds a new to-do item to list of todos
     * @param item a string added by the user
     */
    private void addTodoItem(TodoItem item){
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
            TodoItem item = new TodoItem(value);
            addTodoItem(item);
            addTodo(item);
        }
        inputText.getText().clear();
    }

    /*-------------------------firestore section----------------------------*/

    HashMap<String, TodoItem> getAllTodos(){
        return new HashMap<>(allTodos);
    }

    public void addTodo(TodoItem todo){
        allTodos.put(todo.getId(), todo);
        db = FirebaseFirestore.getInstance();
        db.collection("todoItems").document(todo.getId()).set(todo);
    }

    public void updateTodo(TodoItem todo){
        allTodos.put(todo.getId(), todo);
        db = FirebaseFirestore.getInstance();
        db.collection("todoItems").document(todo.getId()).set(todo);
    }

    public void deleteTodo(TodoItem todo){
        allTodos.remove(todo.getId());
        db = FirebaseFirestore.getInstance();
        db.collection("todoItems").document(todo.getId()).delete();
    }

    private void refreshDataWithOneTimeQuery(){
        db = FirebaseFirestore.getInstance();
        db.collection("todoItems").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot result = task.getResult();
                if(task.isSuccessful() && result != null){
                    allTodos.clear();
                    for (DocumentSnapshot document: result) {
                        String docId = document.getId();
                        TodoItem item = document.toObject(TodoItem.class);
                        allTodos.put(docId, item);
                    }
                    initAdapter();

                }
            }
        });
    }

}
