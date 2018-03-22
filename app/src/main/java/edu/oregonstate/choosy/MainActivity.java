package edu.oregonstate.choosy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;




public class MainActivity extends AppCompatActivity implements SavedDecisionAdapter.OnSavedDecisionClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mSavedDecisionsRV;
    private EditText mSavedDecisionsEntryET;

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

        mSavedDecisionsEntryET = (EditText)findViewById(R.id.et_temp_add_saved_decision);

        Button addSavedDecisionBtn = (Button)findViewById(R.id.btn_temp_add_saved_decision);
        addSavedDecisionBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String savedDecisionText = mSavedDecisionsEntryET.getText().toString();
                if(!TextUtils.isEmpty(savedDecisionText)){
                    mSavedDecisionsAdapter.addSavedDecision(savedDecisionText);
                    mSavedDecisionsEntryET.setText("");
                }
            }
        });
    }

    @Override
    public void onSavedDecisionClick(String itemText) {
        Log.d(TAG, "WOOHOO !!!!! THE ITEM IS CLICKABLE!!!! !!!! !!!");
        Intent detailedDecisionIntent = new Intent(this, DecisionDetailActivity.class);
        detailedDecisionIntent.putExtra("temporary", itemText);
        startActivity(detailedDecisionIntent);
    }
}
