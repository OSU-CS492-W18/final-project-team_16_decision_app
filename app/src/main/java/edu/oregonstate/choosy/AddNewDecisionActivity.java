package edu.oregonstate.choosy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.util.Log;

import java.io.IOException;
import java.net.URL;
=======
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
>>>>>>> 9599acdd23b1f2ccaf590f90f1192c5b62334905

public class AddNewDecisionActivity extends AppCompatActivity {
    private TextView quoteView;
    private final static String TAG = MainActivity.class.getSimpleName();

    private EditText mOption1;
    private EditText mOption2;
    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_decision);
<<<<<<< HEAD
        quoteView = (TextView)findViewById(R.id.TV_quote);
        getQuote();
    }

    private void getQuote() {
        String quoteURL = QuoteUtils.buildQuoteURL("inspire");
        new QuoteFetchTask().execute(quoteURL);
    }

    public class QuoteFetchTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String quoteURL = urls[0];
            String quote = null;
            try {
                quote = NetworkUtils.doHTTPGet(quoteURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return quote;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                String quote = QuoteUtils.parseQuoteJSON(s);
                Log.d(TAG, "quote fetched: " + quote);
                quoteView.setText(quote);
            }
        }
=======

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
>>>>>>> 9599acdd23b1f2ccaf590f90f1192c5b62334905
    }
}
