package edu.oregonstate.choosy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayDeque;

public class MainActivity extends AppCompatActivity {

    private TextView mSavedDecisionsTV;
    private EditText mSavedDecisionsEntryET;

    private ArrayDeque<String> mSavedDecisionsAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSavedDecisionsAD = new ArrayDeque<String>();
        mSavedDecisionsTV = (TextView)findViewById(R.id.tv_saved_decision);
        mSavedDecisionsEntryET = (EditText)findViewById(R.id.et_temp_add_saved_decision);

        Button addSavedDecisionBtn = (Button)findViewById(R.id.btn_temp_add_saved_decision);
        addSavedDecisionBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String savedDecisionText = mSavedDecisionsEntryET.getText().toString();
                if(!TextUtils.isEmpty(savedDecisionText)){
                    mSavedDecisionsAD.push(savedDecisionText);
                    mSavedDecisionsTV.setText("");
                    for(String decision : mSavedDecisionsAD) {
                        mSavedDecisionsTV.append(decision + "\n\n");
                    }
                    mSavedDecisionsEntryET.setText("");
                }
            }
        });
    }
}
