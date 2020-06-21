package de.fhstralsund.rmeier.mytodolist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.fhstralsund.rmeier.mytodolist.data.model.Todo;

@Dao
/**
 * Data-Access-Schnittstelle für TodoItems
 * @author Robert Meier
 * */
public interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Todo todo);

    @Delete
    void deleteTodo(Todo... todos);

    @Query("DELETE FROM todo")
    void deleteAll();

    @Query("SELECT * FROM todo")
    LiveData<List<Todo>> getAllTodos();

    @Update
    void update(Todo t);
}
