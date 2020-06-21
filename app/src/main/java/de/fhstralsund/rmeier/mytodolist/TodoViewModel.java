package de.fhstralsund.rmeier.mytodolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.fhstralsund.rmeier.mytodolist.data.PrioritätRepository;
import de.fhstralsund.rmeier.mytodolist.data.TodoRepository;
import de.fhstralsund.rmeier.mytodolist.data.model.Priorität;
import de.fhstralsund.rmeier.mytodolist.data.model.Todo;

/***
 *  Erstellung von ViewModel zur Verwendung durch Activity
 *
 * @author Robert Meier
 */
public class TodoViewModel extends AndroidViewModel {

    private TodoRepository mTodoRepo;
    private PrioritätRepository mPrioRepo;
    private LiveData<List<Todo>> mAllTodos;
    private LiveData<List<Priorität>> mAllPrioritäten;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        mTodoRepo = new TodoRepository(application);
        mPrioRepo = new PrioritätRepository(application);
        mAllTodos = mTodoRepo.getAllTodos();
        mAllPrioritäten = mPrioRepo.getAllPriorities();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return mAllTodos;
    }
    public LiveData<List<Priorität>> getAllPrioritäten() { return mAllPrioritäten; }

    //Methoden zum Einfügen in Datenbank
    public void insert(Todo t) {
        mTodoRepo.insert(t);
    }
    public void insert(Priorität t) { mPrioRepo.insert(t) ;}

    //Methoden zur aktualisierung der Datenbank
    public void update(Todo item) { mTodoRepo.update(item);}
    public void update(Priorität item) { mPrioRepo.update(item);}

    //Methoden zum Löschen aus der Datenbank
    public void delete(Todo item) {
        mTodoRepo.delete(item);
    }
    public void delete(Priorität item) { mPrioRepo.delete(item);}
}
