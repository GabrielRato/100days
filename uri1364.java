import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String input;
        List<Integer> sol = new ArrayList<>();
        do {
            Map<String, Boolean> notEmoticon = new HashMap<>();
            int minSize = 1000000000, maxSize = 0;
            input = reader.nextLine();
            int qtdEmoticons = Integer.parseInt(input.split(" ")[0]);
            int qtdPhrases = Integer.parseInt(input.split(" ")[1]);
            Map<String, Boolean> emoticons = new HashMap<>();
            if (qtdEmoticons == 0 && qtdPhrases == 0) {
                break;
            }

            for (int i = 0; i < qtdEmoticons; i++) {
                String emoticon = reader.nextLine();
                emoticons.put(emoticon, true);
                if (emoticon.length() > maxSize) {
                    maxSize = emoticon.length();
                }
                if (emoticon.length() < minSize) {
                    minSize = emoticon.length();
                }
            }

            int solution = 0;
            for (int i = 0; i < qtdPhrases; i++) {
                String nextPhrase = reader.nextLine();
                solution += bestCase(nextPhrase, minSize, maxSize, emoticons, notEmoticon);
            }

            sol.add(solution);

        } while (true);

        sol.forEach(System.out::println);

    }

    private static boolean isEmoticon(String potencial, int minSize, int maxSize, Map<String, Boolean> emoticons, Map<String, Boolean> notEmoticon) {
        if (potencial.length() < minSize) {
            return false;
        }

        for (int i = potencial.length(); i >= 0; i--) {
            for (int j = 0; j <= maxSize && i - j >= 0; j++) {
                String substring = potencial.substring(i - j, i);
                if (substring.length() < minSize || substring.length() > maxSize || notEmoticon.getOrDefault(substring, false)) {
                    continue;
                }
                if (emoticons.getOrDefault(substring, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static int bestCase(String phrase, int minSizeEmoticon, int maxSizeEmoticon, Map<String, Boolean> emoticons, Map<String, Boolean> notEmoticon) {
        int solution = 100000000;

        for (int i = 0; i < minSizeEmoticon; i++) {
            int localSolution = 0;
            StringBuilder copyPhrase = new StringBuilder(phrase);
            while (isEmoticon(copyPhrase.toString(), minSizeEmoticon, maxSizeEmoticon, emoticons, notEmoticon) && localSolution <= solution) {
                int actualIndex = 0;
                for (int j = 0; j < copyPhrase.length() && localSolution <= solution; j++) {
                    String substring = copyPhrase.substring(actualIndex, j + 1);
                    if (isEmoticon(substring, minSizeEmoticon, maxSizeEmoticon, emoticons, notEmoticon)) {
                        actualIndex = j + 1;
                        copyPhrase.setCharAt(j - i, ' ');
                        localSolution++;
                    } else {
                        notEmoticon.put(substring, true);
                    }
                }
            }
            if (localSolution < solution) {
                solution = localSolution;
            }
        }
        return solution;
    }

}
