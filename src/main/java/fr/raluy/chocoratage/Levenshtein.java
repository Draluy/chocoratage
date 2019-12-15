package fr.raluy.chocoratage;

public class Levenshtein {

    public static int distance(String a, String b) {
        return distanceSameCase(a.toLowerCase(), b.toLowerCase());
    }

    private static int distanceSameCase(String a, String b) {
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    public static boolean isThisSimilarEnoughToThat (String dictWord, String b){
        return isThisSimilarEnoughToThat(dictWord, b, false);
    }

    public static boolean isThisSimilarEnoughToThat (String dictWord, String challenge, boolean alignCase){
        int maxDistance = dictWord.length()/4;
        return isThisSimilarEnoughToThat(dictWord, challenge, maxDistance, alignCase);
    }

    public static boolean isThisSimilarEnoughToThat(String dictWord, String b, int maxDistance, boolean alignCase) {
        return (alignCase ? distance(dictWord, b) : distanceSameCase(dictWord, b)) <= maxDistance;
    }
}