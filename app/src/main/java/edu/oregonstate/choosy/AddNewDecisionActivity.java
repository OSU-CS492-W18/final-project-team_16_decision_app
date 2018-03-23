package edu.oregonstate.choosy;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.Random;

public class AddNewDecisionActivity extends AppCompatActivity {

    private EditText mOption1;
    private EditText mOption2;
    private Button mSubmit;
    private TextView quoteView;
    private ProgressBar mLoadingProgressBar;
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_decision);

        mOption1 = (EditText)findViewById(R.id.ET_new_option_1);
        mOption2 = (EditText)findViewById(R.id.ET_new_option_2);
        mSubmit = (Button)findViewById(R.id.button_submit);

        quoteView = (TextView)findViewById(R.id.TV_quote);
        mLoadingProgressBar = (ProgressBar)findViewById(R.id.PB_load);
        getQuote();

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
                        Intent success = new Intent();
                        setResult(Activity.RESULT_OK, success);
                        finish();
                    }
                    else {
                        //Already exists Display toast?
                    }
                }
            }
        });
    }

    private void getQuote() {
        Random r =  new Random();
        int rand = r.nextInt(4);
        String quotetype = "";
        if (rand == 3) {
            quotetype = "management";
        }
        if (rand == 2) {
            quotetype = "life";
        }
        if (rand == 1) {
            quotetype = "students";
        }
        if (rand == 0) {
            quotetype = "inspire";
        }
        Log.d(TAG, "quote type: " + quotetype);
        String quoteURL = QuoteUtils.buildQuoteURL(quotetype);
        Log.d(TAG, "url generated: " + quoteURL);
        new QuoteFetchTask().execute(quoteURL);
    }

    public class QuoteFetchTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgressBar.setVisibility(View.VISIBLE);
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
            mLoadingProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}