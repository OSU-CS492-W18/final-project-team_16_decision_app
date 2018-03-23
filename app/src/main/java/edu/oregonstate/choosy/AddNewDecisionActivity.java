package edu.oregonstate.choosy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewDecisionActivity extends AppCompatActivity {

    private EditText mOption1;
    private EditText mOption2;
    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_decision);

        mOption1 = (EditText)findViewById(R.id.ET_new_option_1);
        mOption2 = (EditText)findViewById(R.id.ET_new_option_2);
        mSubmit = (Button)findViewById(R.id.button_submit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = mOption1.getText().toString();
                String second = mOption2.getText().toString();

                if( !TextUtils.isEmpty(first) && !TextUtils.isEmpty(second)) {
                    ChoosyDatabase db = new ChoosyDatabase(getApplicationContext());
                    DecisionUtils.decisionObject toAdd = new DecisionUtils.decisionObject(first, second);
                    if(db.addDecision(toAdd) ) {
                        //Added successfully
                    }
                    else {
                        //Already exists
                    }
                    //Display a toast or notification? Or even navigate back to main activity

                    //Clear edit text fields
                    mOption1.setText("");
                    mOption2.setText("");
                }
            }
        });
    }
}
