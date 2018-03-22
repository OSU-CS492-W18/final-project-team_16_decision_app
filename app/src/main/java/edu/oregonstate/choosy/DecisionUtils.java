package edu.oregonstate.choosy;

import java.io.Serializable;

/**
 * Created by tarrenengberg on 3/22/18.
 */

public class DecisionUtils {

    public static class decisionObject implements Serializable {
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

}
