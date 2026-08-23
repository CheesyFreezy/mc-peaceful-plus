package com.orangecheese.peacefulplus.utility;

import java.util.ArrayList;
import java.util.List;

public class StringUtility {
    private StringUtility() {}

    public static String[] wrapText(String input, int maxLength) {
        List<String> lines = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int currentLength = 0;

        for (String word : input.split("\\s+")) {
            int visibleLength = word.replaceAll("<[^>]*>", "").length();

            int separatorLength = currentLength > 0 ? 1 : 0;

            if (currentLength + separatorLength + visibleLength <= maxLength) {
                if (separatorLength > 0) {
                    current.append(" ");
                }

                current.append(word);
                currentLength += separatorLength + visibleLength;
            } else {
                if (!current.isEmpty()) {
                    lines.add(current.toString());
                }

                current = new StringBuilder(word);
                currentLength = visibleLength;
            }
        }

        if (!current.isEmpty()) {
            lines.add(current.toString());
        }

        return lines.toArray(String[]::new);
    }
}