package edu.oregonstate.choosy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements SavedDecisionAdapter.OnSavedDecisionClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mSavedDecisionsRV;
    private EditText mSavedDecisionsEntryET;
    private SQLiteDatabase mDB;

    private SavedDecisionAdapter mSavedDecisionsAdapter;


    private static final String[] tempSavedDecisionsData = {
            "Cars > Trucks",
            "Pie > Cake",
            "Camaro > Mustang",
            "bikes > quads"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSavedDecisionsRV = (RecyclerView)findViewById(R.id.rv_main_saved_decisions);

        mSavedDecisionsRV.setLayoutManager(new LinearLayoutManager(this));
        mSavedDecisionsRV.setHasFixedSize(true);

        mSavedDecisionsAdapter = new SavedDecisionAdapter(this);
        mSavedDecisionsRV.setAdapter(mSavedDecisionsAdapter);
        //mSavedDecisionsAdapter.updateSavedDecisionsData(new ArrayList<String>(Arrays.asList(tempSavedDecisionsData)));

        //Try to add data to database --Remove this later--
        ChoosyDatabase db = new ChoosyDatabase(this);
        DecisionUtils.decisionObject test1 = new DecisionUtils.decisionObject();
        DecisionUtils.decisionObject test2 = new DecisionUtils.decisionObject();
        DecisionUtils.decisionObject test3 = new DecisionUtils.decisionObject();
        test1.firstOption = "Cake";
        test1.secondOption = "Pie";
        test2.firstOption = "Pizza";
        test2.secondOption = "Hotdogs";
        test3.firstOption = "Hiking";
        test3.secondOption = "Skiing";
        db.addDecision(test1);
        db.addDecision(test2);
        db.addDecision(test3);
        //Try to get added data from database
        ArrayList<String> testVals = new ArrayList<>();
        ArrayList<DecisionUtils.decisionObject> testDec = db.getDecisions();

        //Messy way of passing strings to adapter
        for(DecisionUtils.decisionObject dec : testDec) {
            testVals.add(dec.getString());
        }
        Log.d("Main","----Decision 1: "+testDec.get(0).getString() + " --Decision 2: "+testDec.get(1).getString());
        mSavedDecisionsAdapter.updateSavedDecisionsData(testVals);
    }

    @Override
    public void onSavedDecisionClick(String itemText) {
        Log.d(TAG, "WOOHOO !!!!! THE ITEM IS CLICKABLE!!!! !!!! !!!");
        Intent detailedDecisionIntent = new Intent(this, DecisionDetailActivity.class);
        detailedDecisionIntent.putExtra("temporary", itemText);
        startActivity(detailedDecisionIntent);
    }
}
