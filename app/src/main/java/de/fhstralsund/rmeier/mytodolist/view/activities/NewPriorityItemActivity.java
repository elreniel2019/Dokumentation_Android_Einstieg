package de.fhstralsund.rmeier.mytodolist.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.fhstralsund.rmeier.mytodolist.R;
import de.fhstralsund.rmeier.mytodolist.data.model.Priorität;

/**
 * Aktivität, in der neue Prioritäten eingegeben werden
 *
 * @author Robert Meier
 */
public class NewPriorityItemActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM = "de.fhStralsund.rmeier.myTodoList.REPLY_PRIORITY_ITEM";
    public static final String EXTRA_STATE = "de.fhStralsund.rmeier.myTodoList.REPLY_PRIORITY_STATE";
    private EditText mEditWordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_priority_item);
        mEditWordView = findViewById(R.id.edit_PriorityName);

        final Button btnSave = findViewById(R.id.button_prio_save);
        final Button btnCancel = findViewById(R.id.button_prio_cancel);
        Priorität prio = null;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            prio = (Priorität)extras.getSerializable(EXTRA_ITEM);
            mEditWordView.setText(prio.getName());
        }

        Priorität finalPrio = prio;
        btnSave.setOnClickListener((View view) -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditWordView.getText())) {
                Toast.makeText(getApplicationContext(),R.string.Todo_empty_not_saved, Toast.LENGTH_LONG);
            } else {
                if (finalPrio == null) {
                    final Priorität item = new Priorität(mEditWordView.getText().toString());
                    replyIntent.putExtra(EXTRA_ITEM, item);
                    replyIntent.putExtra(EXTRA_STATE, R.string.itemAdd);
                } else {
                    finalPrio.setName(mEditWordView.toString());
                    replyIntent.putExtra(EXTRA_ITEM, finalPrio);
                    replyIntent.putExtra(EXTRA_STATE, R.string.itemUpdate);
                }
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });

        int retCancel = R.string.itemDelete;
        if (prio == null) {
            btnCancel.setText(R.string.button_cancel);
            retCancel = R.string.itemCancel;
        }

        int finalRetCancel = retCancel;
        btnCancel.setOnClickListener((View view) -> {
            Intent replyIntent = new Intent();
            if (finalPrio != null) {
                replyIntent.putExtra(EXTRA_ITEM, finalPrio);
            }
            replyIntent.putExtra(EXTRA_STATE, finalRetCancel);
            setResult(RESULT_OK, replyIntent);
            finish();
        });

    }
}
