package de.fhstralsund.rmeier.mytodolist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import de.fhstralsund.rmeier.mytodolist.data.RecycleViewAdapterTitle;

@Entity(tableName = "priority")
public class Priorität implements Serializable, RecycleViewAdapterTitle {
    static final long serialVersionUID = 100L;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Priorität(String name) {
        this.name = name;
    }

    @Override
    public String getViewAdapterTitle() {
        return getName();
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }
}
