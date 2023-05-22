package com.kindsonthegenius.social.Comment;

public class Encoder {
    public static String encodeHTML(String input) {
        StringBuilder encoded = new StringBuilder();

        for (char c : input.toCharArray()) {
            switch (c) {
                case '&':
                    encoded.append("&amp;");
                    break;
                case '<':
                    encoded.append("&lt;");
                    break;
                case '>':
                    encoded.append("&gt;");
                    break;
                case '"':
                    encoded.append("&quot;");
                    break;
                case '\'':
                    encoded.append("&#x27;");
                    break;
                default:
                    encoded.append(c);
                    break;
            }
        }

        return encoded.toString();
    }
}
