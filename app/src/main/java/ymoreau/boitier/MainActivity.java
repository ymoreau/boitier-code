package ymoreau.boitier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ymoreau.boitier.data.*;

public class MainActivity extends Activity
{
    private Button buttonContinue;
    private Button buttonResults;
    private Button buttonStart;
    private Button buttonSettings;
    private Button buttonHelp;

    // Called when the activity is first created.
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initializeViewReferences();
        initializeListeners();
    }

    // Called when the activity is shown again
    public void onStart()
    {
        super.onStart();
        if(DataModel.ref().isStarted())
            buttonContinue.setVisibility(View.VISIBLE);
        else
            buttonContinue.setVisibility(View.GONE);
        if(DataModel.ref().isStopped())
            buttonResults.setVisibility(View.VISIBLE);
        else
            buttonResults.setVisibility(View.GONE);
    }

    private void initializeViewReferences()
    {
        buttonContinue = findViewById(R.id.buttonContinue);
        buttonResults = findViewById(R.id.buttonResults);
        buttonStart = findViewById(R.id.buttonStart);
        buttonSettings = findViewById(R.id.buttonSettings);
        buttonHelp = findViewById(R.id.buttonHelp);
    }

    private void initializeListeners()
    {
        buttonContinue.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                continueSerie();
            }
        });
        buttonResults.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                showResults();
            }
        });
        buttonStart.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                startSerie();
            }
        });
        buttonSettings.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                openSettings();
            }
        });
        buttonHelp.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                openHelp();
            }
        });
    }

    // Actions
    private void continueSerie()
    {
        if(!DataModel.ref().isStarted())
        {
            buttonContinue.setVisibility(View.GONE);
            return;
        }

        Intent intent = new Intent(this, BoitierActivity.class);
        startActivity(intent);
    }

    private void showResults()
    {
        if(!DataModel.ref().isStopped())
        {
            buttonResults.setVisibility(View.GONE);
            return;
        }

        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

    private void startSerie()
    {
        DataModel.ref().initialize(Settings.questionCount(this));

        Intent intent = new Intent(this, BoitierActivity.class);
        startActivity(intent);
    }

    private void openSettings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openHelp()
    {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}
