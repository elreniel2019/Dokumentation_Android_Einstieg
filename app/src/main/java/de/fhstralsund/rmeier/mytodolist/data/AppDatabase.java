package de.fhstralsund.rmeier.mytodolist.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.fhstralsund.rmeier.mytodolist.data.model.Priorität;
import de.fhstralsund.rmeier.mytodolist.data.model.Todo;

/**
 *  Initialisiert eine Roomdatenbank für die App
 *
 *  @author Robert Meier
 *
 *  auf Basis von: https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#6
 */
@Database(entities = {Todo.class, Priorität.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TodoDao todoDao();
    public abstract PrioritätDao prioritätDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "myAppDatabase")
                                .addCallback(sRoomDatabaseCallback)
                                .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Callback hinzufügen, welcher beim ersten Start der App ausgeführt wird (Initialisierung der Datenbank)
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            dbWriteExecutor.execute(() -> {
                TodoDao dao = INSTANCE.todoDao();
                dao.deleteAll();
                Todo todo;
                todo = new Todo("Hello World", "dies ist ein Test", "01.05.2020");
                dao.insert(todo);
            });
        }
    };

}
