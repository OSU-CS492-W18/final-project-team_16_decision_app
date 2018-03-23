package edu.oregonstate.choosy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class FinalDecisionActivity extends AppCompatActivity {

    private DecisionUtils.decisionObject mDecisionData;
    private DecisionUtils.winnerData mWinnerData;
    private TextView mDecisionTitle;
    private TextView mWinningResult;
    private TextView mWinningPercentage;
    private TextView mLosingPercentage;
    private TextView mLosingResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_decision);

        mDecisionTitle = findViewById(R.id.TV_final_intro_2);
        mWinningResult = findViewById(R.id.TV_final_result);
        mWinningPercentage = findViewById(R.id.TV_final_percentage);
        mLosingPercentage = findViewById(R.id.TV_losing_percentage);
        mLosingResult = findViewById(R.id.TV_losing_result);

        //Get intent data. Maybe error check this.
        Intent intent = getIntent();
        mDecisionData = (DecisionUtils.decisionObject)intent.getSerializableExtra(DecisionUtils.decisionObject.EXTRA_DECISION_OBJECT);
        mDecisionTitle.setText( mDecisionData.getString() );

        mWinnerData = (DecisionUtils.winnerData)intent.getSerializableExtra(DecisionUtils.winnerData.EXTRA_WINNER_OBJECT);

        if(mWinnerData != null) { //Prevent crash if no factors.
            //Update text
            mWinningResult.setText(mWinnerData.winner + "!");
            mLosingResult.setText(mWinnerData.loser + "...");
            mWinningPercentage.setText( mWinnerData.percent + "%");
            mLosingPercentage.setText( mWinnerData.percentLoser + "%");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
