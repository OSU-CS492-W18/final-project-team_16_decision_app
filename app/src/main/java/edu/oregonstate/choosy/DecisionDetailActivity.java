package edu.oregonstate.choosy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class DecisionDetailActivity extends AppCompatActivity {

    private Spinner mProConSpinner;

    private RecyclerView mFactorRV;
    private FactorAdapter mFactorAdapter;

    private static final String[] tempFactorData = {
            "Tastiness",
            "Cooking Time",
            "Factor 3",
            "Factor 4"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_detail);

        mProConSpinner = (Spinner)findViewById(R.id.pro_con_spinner);
        ArrayAdapter<CharSequence> proConAdapter = ArrayAdapter.createFromResource(this, R.array.pro_con_spinner_items, android.R.layout.simple_spinner_dropdown_item);
        mProConSpinner.setAdapter(proConAdapter);

        mFactorRV = (RecyclerView)findViewById(R.id.rv_factors_list);
        mFactorRV.setLayoutManager(new LinearLayoutManager(this));
        mFactorRV.setHasFixedSize(true);
        mFactorAdapter = new FactorAdapter();
        mFactorRV.setAdapter(mFactorAdapter);
        mFactorAdapter.updateFactorData(new ArrayList<String>(Arrays.asList(tempFactorData)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_decision_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.button_see_result:
                seeResultActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void seeResultActivity(){
        Intent seeResultIntent = new Intent(this, FinalDecisionActivity.class);
        startActivity(seeResultIntent);
    }

}
