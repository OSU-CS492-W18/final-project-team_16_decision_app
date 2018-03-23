package edu.oregonstate.choosy;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by tarrenengberg on 3/22/18.
 */

public class DecisionUtils {

    public static class decisionObject implements Serializable {
        public static String EXTRA_DECISION_OBJECT = "EXTRA.DECISION";
        public String firstOption;
        public String secondOption;

        public decisionObject(String first, String second) {
            firstOption = first;
            secondOption = second;
        }

        public String getString() {
            String line = firstOption + " vs " + secondOption;
            return line;
        }
    }

    public static class factorObject implements Serializable {
        public static String EXTRA_FACTOR_OBJECT = "EXTRA.FACTOR";
        public String name;
        public String comp;
        public int pro;
        public int weight;

        public factorObject(String factorName, String link, int isPro, int sliderWeight) {
            name = factorName;
            comp = link;
            pro = isPro;
            weight = sliderWeight;
        }
    }

    public static class winnerData implements Serializable {
        public static String EXTRA_WINNER_OBJECT = "EXTRA.WINNER";
        public String winner;
        public String loser;
        public String percent;
        public String percentLoser;
    }

    public static winnerData getWinnerAndPercent(decisionObject decision, ArrayList<factorObject> factors) {
        winnerData result = new winnerData();
        //Return if size 0
        if(factors.size() == 0) {
            return null;
        }

        //Sum factors
        int sumFirst = 0;
        int sumSecond = 0;

        for(factorObject factor : factors) {
            if( factor.pro == 1) {
                sumFirst += (100 - factor.weight);
                sumSecond += factor.weight;
                Log.d("DecisionUtils", "SumFirst: "+sumFirst+" sumSecond: "+sumSecond);
            }
            else {
                sumFirst += factor.weight;
                sumSecond += (100 - factor.weight);
            }
        }

        //Determine percentage
        int percentage = -1;
        if(sumFirst > sumSecond) {
            //First option won
            percentage = (sumFirst / (factors.size()));
            result.winner = decision.firstOption;
            result.loser = decision.secondOption;
            result.percent = Integer.toString(percentage);
            result.percentLoser = Integer.toString(100-percentage);
        }
        else {
            //Second option won
            percentage = (sumSecond / (factors.size()));
            result.winner = decision.firstOption;
            result.loser = decision.secondOption;
            result.percent = Integer.toString(percentage);
            result.percentLoser = Integer.toString(100 - percentage);
        }

        return result;
    }

}
