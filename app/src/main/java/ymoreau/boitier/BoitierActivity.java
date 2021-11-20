package ymoreau.boitier;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

import ymoreau.boitier.data.Answer;
import ymoreau.boitier.data.DataModel;
import ymoreau.boitier.data.Settings;

/**
 * Manages the Boitier user interface. Set the answers through DataModel and call results activity in the end.
 *
 * @author Yoann Moreau
 */
public class BoitierActivity extends Activity {
    private boolean isPedagogic;

    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;
    private Button buttonClear;
    private Button buttonValidate;
    private ImageButton buttonHome;
    private TextView currentQuestionTextView;
    private TextView currentAnswerATextView;
    private TextView currentAnswerBTextView;
    private TextView currentAnswerCTextView;
    private TextView currentAnswerDTextView;

    private AlertDialog.Builder pedagogicDialog;

    public BoitierActivity() {
        super();
    }

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Default values
        isPedagogic = false;

        setContentView(R.layout.boitier_classic);
        initializeViewReferences();
        initializeListeners();

        // Pedagogic dialog initialization
        pedagogicDialog = new AlertDialog.Builder(this);

        pedagogicDialog.setNegativeButton("Incorrect", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                DataModel.ref().currentAnswer().setCorrect(false);
                endQuestion();
            }
        });
        pedagogicDialog.setPositiveButton("Correct", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                DataModel.ref().currentAnswer().setCorrect(true);
                endQuestion();
            }
        });
    }

    public void onStart() {
        super.onStart();

        if (!DataModel.ref().isStarted()) {
            finish();
            return;
        }

        isPedagogic = Settings.isPedagogic(this);

        // Initialize the Boitier
        refreshCurrentQuestion();
        clearCurrentAnswerView();

        Answer currentAnswer = DataModel.ref().currentAnswer();
        if (currentAnswer.A())
            currentAnswerATextView.setText("A");
        if (currentAnswer.B())
            currentAnswerBTextView.setText("B");
        if (currentAnswer.C())
            currentAnswerCTextView.setText("C");
        if (currentAnswer.D())
            currentAnswerDTextView.setText("D");
    }

    private void initializeViewReferences() {
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);
        buttonClear = findViewById(R.id.buttonClearBoitier);
        buttonValidate = findViewById(R.id.buttonValidateBoitier);
        buttonHome = findViewById(R.id.buttonHome);

        currentQuestionTextView = findViewById(R.id.currentQuestion);
        currentAnswerATextView = findViewById(R.id.answerA);
        currentAnswerBTextView = findViewById(R.id.answerB);
        currentAnswerCTextView = findViewById(R.id.answerC);
        currentAnswerDTextView = findViewById(R.id.answerD);
    }

    private void initializeListeners() { // TODO use onClick xml property instead?
        buttonA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickA();
            }
        });
        buttonB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickB();
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickC();
            }
        });
        buttonD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickD();
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clear();
            }
        });
        buttonValidate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validate();
            }
        });
        buttonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    // View management
    private void refreshCurrentQuestion() {
        currentQuestionTextView.setText(String.format(Locale.FRENCH, "%02d", DataModel.ref().currentAnswerNumber()));
    }

    private void clearCurrentAnswerView() {
        currentAnswerATextView.setText(" ");
        currentAnswerBTextView.setText(" ");
        currentAnswerCTextView.setText(" ");
        currentAnswerDTextView.setText(" ");
    }

    // Actions
    private void clickA() {
        DataModel.ref().currentAnswer().setA();
        currentAnswerATextView.setText("A");
    }

    private void clickB() {
        DataModel.ref().currentAnswer().setB();
        currentAnswerBTextView.setText("B");
    }

    private void clickC() {
        DataModel.ref().currentAnswer().setC();
        currentAnswerCTextView.setText("C");
    }

    private void clickD() {
        DataModel.ref().currentAnswer().setD();
        currentAnswerDTextView.setText("D");
    }

    private void clear() {
        DataModel.ref().currentAnswer().clear();
        clearCurrentAnswerView();
    }

    private void validate() {
        if (DataModel.ref().currentAnswer().isEmpty())
            return;

        if (isPedagogic) {
            pedagogicDialog.setMessage("Question " + DataModel.ref().currentAnswerNumber()
                    + "\nVotre réponse : "
                    + DataModel.ref().currentAnswer().toString());
            pedagogicDialog.show();
        } else {
            endQuestion();
        }
    }

    private void endQuestion() {
        if (DataModel.ref().currentAnswerNumber() < DataModel.ref().answersCount()) {
            DataModel.ref().nextAnswer();
            refreshCurrentQuestion();
            clearCurrentAnswerView();
            return;
        }

        DataModel.ref().stop();

        // No more question
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
        finish();
    }
}
