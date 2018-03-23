package edu.oregonstate.choosy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.util.Log;

import java.io.IOException;
import java.net.URL;

public class AddNewDecisionActivity extends AppCompatActivity {
    private TextView quoteView;
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_decision);
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
    }
}
