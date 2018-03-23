package edu.oregonstate.choosy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class DecisionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_detail);
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
        //Pass on previous intent data. Maybe error check this. Could also move it on onCreate if needed.
        Intent decisionData = getIntent();
        seeResultIntent.putExtra(DecisionUtils.decisionObject.EXTRA_DECISION_OBJECT,
                (DecisionUtils.decisionObject)decisionData.getSerializableExtra(DecisionUtils.decisionObject.EXTRA_DECISION_OBJECT));
        //Perhaps pass something else here. Winner vs loser? And by how much (percentage).
        startActivity(seeResultIntent);
    }


}
