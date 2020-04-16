package com.example.todoboom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTodoListener {

    RecyclerView recyclerView;
    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.todo_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TodoAdapter(this, items, this));
    }

    /***
     * this method adds a new to-do item to list of todos
     * @param item a string added by the user
     */
    public void addTodoItem(String item){
        items.add(item);
        recyclerView.getAdapter().notifyDataSetChanged();
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
            addTodoItem(value);
        }
        inputText.getText().clear();
    }

    /***
     * this method marks a to-do as 'done' by changing the alpha, adding a V sign, and pops up a
     * toast that says that the certain to-do is done.
     * @param position the position of the to-do item in the recycler
     */
    @Override
    public void onTodoClick(int position) {
        View view = recyclerView.getLayoutManager().findViewByPosition(position);
        ImageView doneImage = view.findViewById(R.id.done_image);
        doneImage.setVisibility(View.VISIBLE);
        TextView todoText = view.findViewById(R.id.todo_text);
        todoText.setAlpha(0.4f);
        Toast.makeText(getApplicationContext(),"TODO "+todoText.getText()+" is now done. BOOM!",
                Toast.LENGTH_LONG).show();
    }
}
