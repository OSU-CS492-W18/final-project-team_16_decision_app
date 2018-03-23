package edu.oregonstate.choosy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSavedDecisionsRV = (RecyclerView)findViewById(R.id.rv_main_saved_decisions);

        mSavedDecisionsRV.setLayoutManager(new LinearLayoutManager(this));
        mSavedDecisionsRV.setHasFixedSize(true);

        mSavedDecisionsAdapter = new SavedDecisionAdapter(this);
        mSavedDecisionsRV.setAdapter(mSavedDecisionsAdapter);

        ChoosyDatabase db = new ChoosyDatabase(this);

        //Try to get added data from database
        ArrayList<DecisionUtils.decisionObject> testDec = db.getDecisions();
        mSavedDecisionsAdapter.updateSavedDecisionsData(testDec);

        //Swiping
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ((SavedDecisionAdapter.SavedDecisionViewHolder)viewHolder).removeDecision();
            }
        };

        //Attach to recyclerview  Modified class lecture code
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mSavedDecisionsRV);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.button_add_new_decision:
                openNewDecisionActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openNewDecisionActivity(){
        Intent addNewDecisionIntent = new Intent(this, AddNewDecisionActivity.class);
        startActivity(addNewDecisionIntent);
    }

    @Override
    public void onSavedDecisionClick(DecisionUtils.decisionObject decision) {
        Intent detailedDecisionIntent = new Intent(this, DecisionDetailActivity.class);
        detailedDecisionIntent.putExtra(DecisionUtils.decisionObject.EXTRA_DECISION_OBJECT, decision);
        startActivity(detailedDecisionIntent);
    }
}
