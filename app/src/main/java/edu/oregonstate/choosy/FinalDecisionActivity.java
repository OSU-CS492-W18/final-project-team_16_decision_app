package edu.oregonstate.choosy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class FinalDecisionActivity extends AppCompatActivity {

    private DecisionUtils.decisionObject mDecisionData;
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

        //Determine winning result
        //get winner from intent
        boolean firstWon = true;
        int winningPercent = -1;
        int losingPercent = -1;
        String winningResult = "Nothing";
        String losingResult = "Nothing";

        if(firstWon) {
            //Fake values. Replace these with intent data.
            winningPercent = 50;
            losingPercent = 50;
            winningResult = mDecisionData.firstOption;
            losingResult = mDecisionData.secondOption;
        }
        else {
            winningPercent = 50;
            losingPercent = 50;
            winningResult = mDecisionData.secondOption;
            losingResult = mDecisionData.firstOption;
        }

        //Update text
        mWinningResult.setText(winningResult + "!");
        mLosingResult.setText(losingResult + "...");
        mWinningPercentage.setText( Integer.toString(winningPercent));
        mLosingPercentage.setText( Integer.toString(losingPercent));

    }
}
