package de.fhstralsund.rmeier.mytodolist.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import de.fhstralsund.rmeier.mytodolist.MyRecycleViewAdapter;
import de.fhstralsund.rmeier.mytodolist.R;
import de.fhstralsund.rmeier.mytodolist.RecyclerViewClickListener;
import de.fhstralsund.rmeier.mytodolist.TodoViewModel;
import de.fhstralsund.rmeier.mytodolist.data.model.Priorität;

/**
 * Aktivitität, über die Prioritäten angezeigt werden
 *
 * @author Robert Meier
 */
public class PriorityListActivity extends AppCompatActivity implements RecyclerViewClickListener {

    public static final int NEW_PRIORITY_ACTIVITY_REQUEST_CODE = 2;

    private MyRecycleViewAdapter<Priorität> mAdapter;
    private TodoViewModel mTodoModel;
    private SharedPreferences.OnSharedPreferenceChangeListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority_list);

        RecyclerView rv = findViewById(R.id.recyclerview);
        mAdapter = new MyRecycleViewAdapter<>(this,this);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mTodoModel = new ViewModelProvider(this).get(TodoViewModel.class);
        mTodoModel.getAllPrioritäten().observe(this, new Observer<List<Priorität>>() {
            @Override
            public void onChanged(List<Priorität> prioritäten) {
                mAdapter.setItems(prioritäten);
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mListener = ((sharedPreferences, key) -> {
           setFontSize(sharedPreferences);
        });
        setFontSize(prefs);
        prefs.registerOnSharedPreferenceChangeListener(mListener);

        FloatingActionButton fab = findViewById(R.id.priority_fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(PriorityListActivity.this, NewPriorityItemActivity.class);
            startActivityForResult(intent, NEW_PRIORITY_ACTIVITY_REQUEST_CODE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PRIORITY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Priorität item = (Priorität) Objects.requireNonNull(data).getSerializableExtra(NewPriorityItemActivity.EXTRA_ITEM);
            switch (data.getIntExtra(NewPriorityItemActivity.EXTRA_STATE,0)) {
                case R.string.itemAdd:
                    mTodoModel.insert(item);
                    break;
                case R.string.itemUpdate:
                    mTodoModel.update(item);
                    break;
                case R.string.itemDelete:
                    mTodoModel.delete(item);
                    break;
                case R.string.itemCancel:
                    break;
            }

        }
    }

    private void setFontSize(SharedPreferences prefs) {
        int textSize = Integer.parseInt(prefs.getString((String)getText(R.string.prefListFontSize),(String)getText(R.string.prefListFontSizeDefault)));
        mAdapter.setTextSize(textSize);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Priorität p = mAdapter.getItem(position);
        if (p != null) {
            Intent intent = new Intent(PriorityListActivity.this, NewPriorityItemActivity.class);
            intent.putExtra(NewPriorityItemActivity.EXTRA_ITEM, p);
            startActivityForResult(intent, NEW_PRIORITY_ACTIVITY_REQUEST_CODE);
        }
    }
}
