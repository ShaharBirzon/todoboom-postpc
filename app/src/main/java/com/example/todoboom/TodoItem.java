package com.example.todoboom;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private static long idCount = 0;
    public long id;
    public String description;
    public boolean isDone = false;
    public String creationDate;
    public String updateDate;

    public TodoItem(String desc){
        id = idCount;
        idCount += 1;
        description = desc;
        Date creationDateTemp = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy | HH:mm");
        creationDate = formatter.format(creationDateTemp);
        updateDate = creationDate;
    }

    public TodoItem(){};

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

    String getCreationDateTime(){
        return creationDate;
    }

    String getUpdateDateTime(){
        return updateDate;
    }

    void setUpdateDate(){
        Date updateDateTemp = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy | HH:mm");
        updateDate = formatter.format(updateDateTemp);
    }

    String getId(){return String.valueOf(id);}



}
