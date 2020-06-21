package de.fhstralsund.rmeier.mytodolist.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.fhstralsund.rmeier.mytodolist.data.model.Todo;

/**
 * Repository kapselt Datenbankzugriffe in einzelner Klasse
 * gemäß Dokumentation eine Best-Practise für Datenzugriffe
 *
 * @author Robert Meier
 *
 * auf Basis von: https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7
 */
public class TodoRepository {

    private TodoDao mTodoDao;
    private LiveData<List<Todo>> mAllTodos;

    public TodoRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTodoDao = db.todoDao();

        mAllTodos = mTodoDao.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return mAllTodos;
    }

    public void insert(Todo todo) {
        AppDatabase.dbWriteExecutor.execute(() -> {
            mTodoDao.insert(todo);
        });
    }

    public void update(Todo item) {
        AppDatabase.dbWriteExecutor.execute(() -> {
            mTodoDao.update(item);
        });

    }

    public void delete(Todo item) {
        AppDatabase.dbWriteExecutor.execute(() -> {
            mTodoDao.deleteTodo(item);
        });
    }
}
