import java.util.ArrayList;

public class Utils {
    public static ArrayList <String> toStringArray(String lineToArray) {
        ArrayList<String> listOfString = new ArrayList<>();
        String strForDigit = "";
        for (int i = 0; i < lineToArray.length(); i++) {
            if (Character.isDigit(lineToArray.charAt(i)) || lineToArray.charAt(i) == '.') {
                strForDigit += lineToArray.charAt(i);
            } else {
                listOfString.add(strForDigit);
                String s = String.valueOf(lineToArray.charAt(i));
                listOfString.add(s);
                strForDigit = "";
            }
        }
        return listOfString;
    }

    public static boolean isDigit(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean haveSinglePoint(String lineForCalculate) {
        if (lineForCalculate.endsWith(".")) return true;
        if (lineForCalculate.startsWith(".")) return true;
        char [] charsForFindSinglePoint = lineForCalculate.toCharArray();
        for (int i = 0; i < charsForFindSinglePoint.length; i++) {
            if (charsForFindSinglePoint[i] == '.'){
                if (!Character.isDigit(charsForFindSinglePoint[i + 1])||!Character.isDigit(charsForFindSinglePoint[i - 1])){
                    return true;
                }
            }
        }
        return false;
    }
}
