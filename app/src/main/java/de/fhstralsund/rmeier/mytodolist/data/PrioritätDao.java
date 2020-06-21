package de.fhstralsund.rmeier.mytodolist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.fhstralsund.rmeier.mytodolist.data.model.Priorität;

@Dao
/**
 * Schnittstelle für DAO-Objeckte der Tabelle priority
 *
 * @author Robert Meier
 */
public interface PrioritätDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Priorität item);

    @Delete
    void deletePrios(Priorität... items);

    @Query("DELETE FROM priority")
    void deleteAll();

    @Query("SELECT * FROM priority")
    LiveData<List<Priorität>> getAllPriorities();

    @Update
    void update(Priorität t);
}
