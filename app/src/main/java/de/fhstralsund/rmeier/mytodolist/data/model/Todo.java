package de.fhstralsund.rmeier.mytodolist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import de.fhstralsund.rmeier.mytodolist.data.RecycleViewAdapterTitle;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_NULL;

@Entity
public class Todo implements Serializable, RecycleViewAdapterTitle {
    static final long serialVersionUID = 42L;


    @NonNull
    private String title;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;
    private String description;
    private String datetime;

    @ForeignKey(entity = Priorität.class,parentColumns = "_id",childColumns = "priority_id", onUpdate = CASCADE, onDelete = SET_NULL)
    private int priority_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority_id() {
        return priority_id;
    }

    public void setPriority_id(int priority_id) {
        this.priority_id = priority_id;
    }



    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Todo(@NonNull String title, String description, String datetime) {
        this.title = title;
        this.description = description;
        this.datetime = datetime;
    }

    @Override
    public String getViewAdapterTitle() {
        return getTitle();
    }
}
