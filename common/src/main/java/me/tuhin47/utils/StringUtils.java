package me.tuhin47.utils;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class StringUtils {

    public static String camelCaseToSentence(String camelCase) {
        if (isEmpty(camelCase)) {
            return "";
        }

        StringBuilder sentence = new StringBuilder();
        sentence.append(Character.toUpperCase(camelCase.charAt(0)));

        for (int i = 1; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                sentence.append(' ');
            }
            sentence.append(currentChar);
        }

        return sentence.toString();
    }
}
