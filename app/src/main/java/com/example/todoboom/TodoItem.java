package com.example.todoboom;

public class TodoItem {
    private String description;
    private boolean isDone = false;

    public TodoItem(String desc){
        description = desc;
    }

    void markAsDone(){
        isDone = true;
    }

    void markAsNotDone(){
        isDone = false;
    }

    boolean isTodoDone(){
        return isDone;
    }

    String getTodoDescription(){
        return description;
    }

    void changeTodoDescription(String newDesc){
        description = newDesc;
    }

}
