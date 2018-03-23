package edu.oregonstate.choosy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements SavedDecisionAdapter.OnSavedDecisionClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mSavedDecisionsRV;

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
        mSavedDecisionsAdapter.updateSavedDecisionsData(new ArrayList<String>(Arrays.asList(tempSavedDecisionsData)));

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
    public void onSavedDecisionClick(String itemText) {
        Intent detailedDecisionIntent = new Intent(this, DecisionDetailActivity.class);
        detailedDecisionIntent.putExtra("temporary", itemText);
        startActivity(detailedDecisionIntent);
    }
}
