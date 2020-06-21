package de.fhstralsund.rmeier.mytodolist.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.fhstralsund.rmeier.mytodolist.data.model.Priorität;
import de.fhstralsund.rmeier.mytodolist.data.model.Todo;

/**
 * Repository kapselt Datenbankzugriffe in einzelner Klasse
 * gemäß Dokumentation eine Best-Practise für Datenzugriffe
 *
 * @author Robert Meier
 *
 * auf Basis von: https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7
 */
public class PrioritätRepository {

    private PrioritätDao mPriorityDao;
    private LiveData<List<Priorität>> mAllPriorities;

    public PrioritätRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mPriorityDao = db.prioritätDao();

        mAllPriorities = mPriorityDao.getAllPriorities();
    }

    public LiveData<List<Priorität>> getAllPriorities() {
        return mAllPriorities;
    }

    public void insert(Priorität item) {
        AppDatabase.dbWriteExecutor.execute(() -> {
            mPriorityDao.insert(item);
        });
    }

    public void update(Priorität item) {
        AppDatabase.dbWriteExecutor.execute(() -> {
            mPriorityDao.update(item);
        });

    }

    public void delete(Priorität item) {
        AppDatabase.dbWriteExecutor.execute(() -> {
            mPriorityDao.deletePrios(item);
        });
    }
}
