package edu.oregonstate.choosy;

import android.content.Context;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

public class QuoteUtils {
    private final static String QUOTE_BASE_URL = "https://quotes.rest/qod.json";
    private final static String QUOTE_QUERY_PARAM = "&category=";

    public static String buildQuoteURL(String quotetype) {
        return Uri.parse(QUOTE_BASE_URL).buildUpon()
                .appendQueryParameter(QUOTE_QUERY_PARAM, quotetype)
                .build()
                .toString();
    }

    public static String parseQuoteJSON(String quoteJSON) {
        try {
            JSONObject quoteObj = new JSONObject(quoteJSON);
            JSONObject quoteObj2 = quoteObj.getJSONObject("contents");
            JSONArray quoteArray = quoteObj2.getJSONArray("quotes");
            String quote = "shrug";
            return quote;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
