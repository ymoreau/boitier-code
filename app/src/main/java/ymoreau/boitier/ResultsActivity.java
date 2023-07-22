package ymoreau.boitier;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import ymoreau.boitier.data.DataModel;

/**
 * Manages the results user interface.
 *
 * @author Yoann Moreau
 */
public class ResultsActivity extends Activity {
    private Button buttonValidate;
    private TextView errorsCountTextView;
    private ListView listView;

    public ResultsActivity() {
    }

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        initializeViewReferences();
        initializeListeners();
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onStart() {
        super.onStart();

        if (!DataModel.ref().isStopped()) {
            finish();
            return;
        }

        String[] answers = DataModel.ref().resultStrings();
        int answersCount = answers.length;

        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, answers));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        for (int i = 0; i < answersCount; ++i)
            listView.setItemChecked(i, DataModel.ref().answer(i).isCorrect());

        refreshErrorsCountText();
    }

    private void initializeViewReferences() {
        buttonValidate = findViewById(R.id.buttonValidateResults);
        errorsCountTextView = findViewById(R.id.errorsCountText);
        listView = findViewById(R.id.listView);
    }

    private void initializeListeners() {
        buttonValidate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validate();
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = view.findViewById(android.R.id.text1);

                DataModel.ref().answer(position).setCorrect(checkedTextView.isChecked());

                refreshErrorsCountText();
            }
        });
    }

    private void refreshErrorsCountText() {
        errorsCountTextView.setText(Integer.toString(DataModel.ref().errorsCount()));
    }

    private void validate() {
        finish();
    }
}
