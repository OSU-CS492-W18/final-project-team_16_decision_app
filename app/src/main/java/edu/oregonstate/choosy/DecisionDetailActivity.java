package edu.oregonstate.choosy;

import android.content.Intent;
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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.ArrayList;


public class DecisionDetailActivity extends AppCompatActivity implements FactorAdapter.OnFactorClickListener {

    private Spinner mProConSpinner;
    private Button mButtonAdd;
    private EditText mFactorTitle;
    private SeekBar mSeekBar;

    private RecyclerView mFactorRV;
    private FactorAdapter mFactorAdapter;
    private ArrayList<DecisionUtils.factorObject> factors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_detail);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mProConSpinner = (Spinner)findViewById(R.id.pro_con_spinner);
        ArrayAdapter<CharSequence> proConAdapter = ArrayAdapter.createFromResource(this, R.array.pro_con_spinner_items, android.R.layout.simple_spinner_dropdown_item);
        mProConSpinner.setAdapter(proConAdapter);


        mFactorRV = (RecyclerView)findViewById(R.id.rv_factors_list);
        mFactorRV.setLayoutManager(new LinearLayoutManager(this));
        mFactorRV.setHasFixedSize(true);
        mFactorAdapter = new FactorAdapter(this);
        mFactorRV.setAdapter(mFactorAdapter);

        mFactorTitle = (EditText)findViewById(R.id.ET_factor_title);
        mSeekBar = (SeekBar)findViewById(R.id.simpleSeekBar);

        //Get data from intent
        Intent decisionData = getIntent();
        DecisionUtils.decisionObject decision = (DecisionUtils.decisionObject)decisionData.getSerializableExtra(DecisionUtils.decisionObject.EXTRA_DECISION_OBJECT);

        //Get data from database
        ChoosyDatabase db = new ChoosyDatabase(this);
        factors = db.getFactors(decision.firstOption);
        mFactorAdapter.updateFactorData(factors);

        mButtonAdd = (Button)findViewById(R.id.add_factor_button);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mFactorTitle.getText().toString();
                int weight = mSeekBar.getProgress();
                String procon = mProConSpinner.getSelectedItem().toString();
                int pro = -1;

                //get intent data
                Intent decisionData = getIntent();
                DecisionUtils.decisionObject decision = (DecisionUtils.decisionObject)decisionData.getSerializableExtra(DecisionUtils.decisionObject.EXTRA_DECISION_OBJECT);

                if( !TextUtils.isEmpty(title)) {
                    //Create factor object
                    ChoosyDatabase db = new ChoosyDatabase(getApplicationContext());
                    if(procon.equals("Pro")) {
                        pro = 1;
                    }
                    else
                        pro = 0;

                    //Log.d("DecisionDetailActivity","Pro: "+pro+" Weight: "+weight);
                    DecisionUtils.factorObject newFactor = new DecisionUtils.factorObject(title, decision.firstOption, pro, weight);

                    //Add new factor to db
                    db.addFactor(newFactor);

                    //Reset ui
                    mFactorTitle.setText("");
                    mSeekBar.setProgress(50);

                    factors = db.getFactors(decision.firstOption);
                    mFactorAdapter.updateFactorData(factors);
                }
            }
        });

        //Swiping
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ((FactorAdapter.FactorViewHolder)viewHolder).removeFactor();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mFactorRV);
    }

    @Override
    public void onFactorClick(DecisionUtils.factorObject fact) {
        //not currently used
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
        ChoosyDatabase db = new ChoosyDatabase(getApplicationContext());

        //Pass on previous intent data. Maybe error check this. Could also move it on onCreate if needed.
        Intent decisionData = getIntent();
        DecisionUtils.decisionObject decision = (DecisionUtils.decisionObject)decisionData.getSerializableExtra(DecisionUtils.decisionObject.EXTRA_DECISION_OBJECT);

        seeResultIntent.putExtra(DecisionUtils.decisionObject.EXTRA_DECISION_OBJECT, decision);
        //Perhaps pass something else here. Winner vs loser? And by how much (percentage).
        DecisionUtils.winnerData winner = DecisionUtils.getWinnerAndPercent( decision, db.getFactors(decision.firstOption) );
        seeResultIntent.putExtra(DecisionUtils.winnerData.EXTRA_WINNER_OBJECT, winner);

        startActivity(seeResultIntent);
    }

}
