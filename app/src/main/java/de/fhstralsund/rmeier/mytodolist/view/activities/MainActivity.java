package de.fhstralsund.rmeier.mytodolist.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

import de.fhstralsund.rmeier.mytodolist.MyRecycleViewAdapter;
import de.fhstralsund.rmeier.mytodolist.R;
import de.fhstralsund.rmeier.mytodolist.RecyclerViewClickListener;
import de.fhstralsund.rmeier.mytodolist.TodoViewModel;
import de.fhstralsund.rmeier.mytodolist.data.model.Todo;

/**
 * Startaktivität für die App
 *
 * @author Robert Meier
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

    public static final int NEW_TODO_ITEM_ACTIVITY_REQUEST_CODE = 1;

    private MyRecycleViewAdapter<Todo> mAdapter;
    private TodoViewModel mTodoViewModel;
    private SharedPreferences.OnSharedPreferenceChangeListener mListener; //Referenz für Lebensdauer der Aktivitität aufrecht halten

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabTodoItem);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewTodoItemActivity.class);
            startActivityForResult(intent, NEW_TODO_ITEM_ACTIVITY_REQUEST_CODE);
        });

        RecyclerView rv = findViewById(R.id.recyclerview);
        mAdapter = new MyRecycleViewAdapter<Todo>(this, this);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mTodoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        //Koppelung mit Datenbank schaffen
        mTodoViewModel.getAllTodos().observe(this, todos -> mAdapter.setItems(todos));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mListener = (sharedPreferences, key) -> {
            setFontSize(sharedPreferences);
        };
        setFontSize(prefs);
        prefs.registerOnSharedPreferenceChangeListener(mListener);
    }

    private void setFontSize(SharedPreferences prefs) {
        int textSize = Integer.parseInt(prefs.getString((String)getText(R.string.prefListFontSize),(String)getText(R.string.prefListFontSizeDefault)));
        mAdapter.setTextSize(textSize);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TODO_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Todo item = (Todo) Objects.requireNonNull(data).getSerializableExtra(NewTodoItemActivity.EXTRA_REPLY_ITEM);
            switch (data.getIntExtra(NewTodoItemActivity.EXTRA_REPLY_STATE,0)) {
                case R.string.itemAdd:
                    mTodoViewModel.insert(item);
                    break;
                case R.string.itemUpdate:
                    mTodoViewModel.update(item);
                    break;
                case R.string.itemDelete:
                    mTodoViewModel.delete(item);
                    break;
                case R.string.itemCancel:
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
            // Einstellungen öffnen
        } else if (id == R.id.action_categories) {
            // Kategorien öffnen
            return true;
        } else if (id == R.id.action_priorities) {
            // Prioritäten öffnen
            Intent intent = new Intent(MainActivity.this, PriorityListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Todo t = mAdapter.getItem(position);
        if (t != null) {
            Intent intent = new Intent(MainActivity.this, NewTodoItemActivity.class);
            intent.putExtra(NewTodoItemActivity.EXTRA_REPLY_ITEM, t);
            startActivityForResult(intent, NEW_TODO_ITEM_ACTIVITY_REQUEST_CODE);
        }
    }

}
