package de.fhstralsund.rmeier.mytodolist.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Stream;

import de.fhstralsund.rmeier.mytodolist.MyRecycleViewAdapter;
import de.fhstralsund.rmeier.mytodolist.R;
import de.fhstralsund.rmeier.mytodolist.TodoViewModel;
import de.fhstralsund.rmeier.mytodolist.data.model.Priorität;
import de.fhstralsund.rmeier.mytodolist.data.model.Todo;

/**
 * Activity, über welche neue TodoItems erfasst werden bzw. bestehende bearbeitet werden können.
 *
 * @author Robert Meier
 */
public class NewTodoItemActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_ITEM = "de.fhStralsund.rmeier.mytodolist.REPLY_TODO_ITEM";
    public static final String EXTRA_REPLY_STATE = "de.fhStralsund.rmeier.mytodolist.REPLY_TODO_STATE";

    private EditText mTodoTitle;
    private EditText mTodoDescription;
    private EditText mTodoAblaufdatum;
    private Spinner  mSpinPrio;

    private TodoViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo_item);

        mTodoTitle = findViewById(R.id.edit_todoTitle);
        mTodoDescription = findViewById(R.id.edit_todoDescription);
        mTodoAblaufdatum = findViewById(R.id.edit_todoDate);
        Todo td = null;

        mViewModel = new TodoViewModel(getApplication());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            td = (Todo)extras.getSerializable(EXTRA_REPLY_ITEM);

            mTodoTitle.setText(td.getTitle());
            mTodoDescription.setText(td.getDescription());
            mTodoAblaufdatum.setText(td.getDatetime());
        }
        Todo finalTd = td; // wird benötigt, da sonst Fehler von Android Studio ausgeworfen wird bezüglich Zugriff auf TodoItem

        //Spinner initialisieren
        mSpinPrio = (Spinner) findViewById(R.id.spinner_TodoPriority);
        mViewModel.getAllPrioritäten().observe(this, (p) -> {
            ArrayAdapter<Priorität> arrayAdapter;
            int i=0;
            if (finalTd != null) {
                for (Priorität prio : p) {
                    if (prio.getId() == finalTd.getPriority_id()) {
                        break;
                    }
                    i++;
                }
            }
            arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, p);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinPrio.setAdapter(arrayAdapter);
            if (finalTd != null && p.size() > i) {
                mSpinPrio.setSelection(i);
            }
        });

        final Button btnSichern = findViewById(R.id.button_save);
        btnSichern.setOnClickListener((View v) -> {
            Intent reIntent = new Intent();
            Priorität prio;
            if (TextUtils.isEmpty(mTodoTitle.getText())) {
                Toast.makeText(getApplicationContext(),
                        R.string.Todo_empty_not_saved,
                        Toast.LENGTH_LONG).show();
            } else {
                if (finalTd == null) {
                    final Todo todo = new Todo(mTodoTitle.getText().toString(),
                            mTodoDescription.getText().toString(), mTodoAblaufdatum.getText().toString());
                    prio = (Priorität) mSpinPrio.getSelectedItem();
                    if (prio == null) {
                        todo.setPriority_id(0);
                    } else {
                        todo.setPriority_id(prio.getId());
                    }
                    todo.setPriority_id(prio.getId());
                    reIntent.putExtra(EXTRA_REPLY_ITEM, todo);
                    reIntent.putExtra(EXTRA_REPLY_STATE, R.string.itemAdd);
                } else {
                    finalTd.setTitle(mTodoTitle.getText().toString());
                    finalTd.setDescription(mTodoDescription.getText().toString());
                    finalTd.setDatetime(mTodoAblaufdatum.getText().toString());
                    prio = (Priorität) mSpinPrio.getSelectedItem();
                    if (prio == null) {
                        finalTd.setPriority_id(0);
                    } else {
                        finalTd.setPriority_id(prio.getId());
                    }

                    reIntent.putExtra(EXTRA_REPLY_ITEM, finalTd);
                    reIntent.putExtra(EXTRA_REPLY_STATE, R.string.itemUpdate);
                }
                setResult(RESULT_OK, reIntent);
                finish();
            }

        });

        final Button btnCancel = findViewById(R.id.button_cancel);

        //Button Bezeichnung wechseln, sofern es sich um ein neues Item handelt
        int retCancel = R.string.itemDelete;
        if (td == null) {
            btnCancel.setText(getText(R.string.button_cancel));
            retCancel = R.string.itemCancel;
        }

        int finalRetCancel = retCancel;
        btnCancel.setOnClickListener((View v) -> {
            Intent reIntent = new Intent();
            if (finalTd != null) {
                reIntent.putExtra(EXTRA_REPLY_ITEM,finalTd);
            }
            reIntent.putExtra(EXTRA_REPLY_STATE, finalRetCancel);
            setResult(RESULT_OK, reIntent);
            finish();
        });

    }
}
