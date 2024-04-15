package encode;

public class Encoder {

    // Main method to determine the encoding strategy
    public static String encode(String input, String encoding) {
        switch (encoding) {
            case "Numeric": return encodeToNumeric(input);
            case "ROT13": return encodeToROT13(input);
            default: return input; // Default case for unrecognized encoding
        }
    }

    // Converts each character to its numeric ASCII value handling spaces specially
    private static String encodeToNumeric(String input) {
        StringBuilder output = new StringBuilder();
        char[] chars = input.toCharArray();
        
        for (int i = 0, n = chars.length; i < n; i++) {
            if (chars[i] == ' ') {
                output.append("-.");
            } else if (chars[i] == '\n') {
                // If the character is a newline, append it directly
                output.append("\n");
            } else {
                // Append the ASCII value and a dot
                output.append((int) chars[i]).append(".");
            }
        }
        
        // Remove the trailing dot if it's not followed by a newline
        if (output.length() > 0 && output.charAt(output.length() - 1) == '.' && output.charAt(output.length() - 2) != '\n') {
            output.deleteCharAt(output.length() - 1);
        }

        return output.toString();
    }



    // Applies ROT13 encoding and rotating alphabetic characters 13 steps
    private static String encodeToROT13(String input) {
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            output.append(Character.isAlphabetic(c) ? rotateChar(c, 13) : c);
        }
        return output.toString();
    }

    // Helper to rotate character within the alphabet and preserving case
    private static char rotateChar(char c, int positions) {
        if (!Character.isAlphabetic(c)) return c;
        int base = Character.isLowerCase(c) ? 'a' : 'A';
        return (char) (((c - base + positions) % 26) + base);
    }
}
