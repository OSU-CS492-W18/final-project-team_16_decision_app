package edu.oregonstate.choosy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.ArrayList;

public class DecisionDetailActivity extends AppCompatActivity {

    private Spinner mProConSpinner;
    private Button mButtonAdd;
    private EditText mFactorTitle;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_detail);

        mProConSpinner = (Spinner)findViewById(R.id.pro_con_spinner);
        ArrayAdapter<CharSequence> proConAdapter = ArrayAdapter.createFromResource(this, R.array.pro_con_spinner_items, android.R.layout.simple_spinner_dropdown_item);
        mProConSpinner.setAdapter(proConAdapter);

        mFactorTitle = (EditText)findViewById(R.id.ET_factor_title);
        mSeekBar = (SeekBar)findViewById(R.id.simpleSeekBar);

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

                    Log.d("DecisionDetailActivity","Pro: "+pro+" Weight: "+weight);
                    DecisionUtils.factorObject newFactor = new DecisionUtils.factorObject(title, decision.firstOption, pro, weight);

                    //Add new factor to db
                    db.addFactor(newFactor);

                    //Reset ui
                    mFactorTitle.setText("");
                    mSeekBar.setProgress(50);
                }
            }
        });

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
