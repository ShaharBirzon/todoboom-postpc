package com.example.todoboom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditTodoActivity extends AppCompatActivity {

    Intent intentBack = new Intent();
    EditText todoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        Intent intentFromMain = getIntent();
        String todoDesc = intentFromMain.getStringExtra("todo_content");
        todoEditText = findViewById(R.id.todo_edit_text);
        todoEditText.setText(todoDesc);
        boolean isTodoDone = intentFromMain.getBooleanExtra("todo_done", false);
        if(isTodoDone){
            Button btn = findViewById(R.id.undone_btn);
            btn.setVisibility(View.VISIBLE);
        }
        else{
            Button btn = findViewById(R.id.mark_done_btn);
            btn.setVisibility(View.VISIBLE);
        }
        String creationDateStr = "created at: " + intentFromMain.getStringExtra("creation_date");
        String modifiedDateStr = "last modified at: " + intentFromMain.getStringExtra("last_modified");
        TextView creationText = findViewById(R.id.creation_txt);
        TextView modifiedText = findViewById(R.id.updated_txt);
        creationText.setText(creationDateStr);
        modifiedText.setText(modifiedDateStr);
    }

    public void markAsDoneOnClick(View v){
        intentBack.putExtra("todo_done",true);
        Toast.makeText(getApplicationContext(),"this todo is now DONE. Boom!", Toast.LENGTH_SHORT).show();
    }

    public void markAsNotDoneOnClick(View v){
        intentBack.putExtra("todo_done",false);
        Toast.makeText(getApplicationContext(),"this todo is yet to be done!", Toast.LENGTH_SHORT).show();
    }

    public void applyChangesOnClick(View v){
        intentBack.putExtra("todo_content", todoEditText.getText().toString());
        intentBack.putExtra("modified", true);
        setResult(RESULT_OK, intentBack);
        finish();
    }

    public void deleteTodoOnClick(View v){
        AlertDialog.Builder dialog = new AlertDialog.Builder(EditTodoActivity.this);
        dialog.setMessage("Delete this todo?");
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intentBack.putExtra("todo_deleted", false);
            }
        });

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intentBack.putExtra("todo_deleted", true);
                finish();
            }
        });
        AlertDialog deleteDialog = dialog.create();
        deleteDialog.show();
        setResult(RESULT_OK, intentBack);
    }
}
