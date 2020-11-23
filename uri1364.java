package com.gabriel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static class CountingSort {
        List<Gift> sort(int arr[], Wishlist wishlist) {
            int n = arr.length;
            List<Gift> newWish = new ArrayList(Collections.nCopies(n, new Gift("", 0, 0)));

            int count[] = new int[102];
            for (int i = 0; i < 100; i++)
                count[i] = 0;

            for (int i = 0; i < n; i++)
                ++count[arr[i]];

            for (int i = 1; i <= 101; i++)
                count[i] += count[i - 1];

            for (int i = n - 1; i >= 0; i--) {
                newWish.set(count[arr[i]] - 1, wishlist.giftList.get(i));
                --count[arr[i]];
            }

            return newWish;
        }
    }

    public static class Wishlist {
        private String owner;
        private List<Gift> giftList;

        public Wishlist(String owner, List<Gift> giftList) {
            this.owner = owner;
            this.giftList = giftList;
        }

        public void addWish(Gift gift) {
            this.giftList.add(gift);
        }

        public void sortList() {
            int preference[] = new int[giftList.size()];
            for (int i = 0; i < giftList.size(); i++) {
                preference[i] = giftList.get(i).getPreference();
            }

            CountingSort countingSort = new CountingSort();
            giftList.sort(Comparator.comparing(Gift::getPreference).thenComparing(Gift::getPrice, Comparator.reverseOrder()).thenComparing(Gift::getName));
            // aqui ja temos uma lista crescente de pref
            //this.giftList = countingSort.sort(preference, this);
        }

        public void showList() {
            NumberFormat formatter = NumberFormat.getInstance(Locale.US);

            int i;
            System.out.println("Lista de " + this.owner);
            for (i = giftList.size() - 1; i >= 0; i--) {
                List<Gift> samePref = new ArrayList<>();
                Gift gift = this.giftList.get(i);
                samePref.add((gift));
                Gift nextGift = null;
//                    if (i - 1 > 0) {
//                         nextGift = this.giftList.get(i - 1);
//                    } else {
//                        nextGift = null;
//                    }
//
//                    while (nextGift != null && gift.preference == nextGift.preference) {
//                        samePref.add(nextGift);
//                        i = i -1;
//
//                        gift = this.giftList.get(i);
//                        if (i - 1 < 0)
//                            break;
//                        nextGift = this.giftList.get(i - 1);
//                    }
//                    samePref.sort(Comparator.comparing(Gift::getPrice).thenComparing(Gift::getName));
//                    //samePref.sort(Comparator.comparing(Gift::getName));
                //samePref.forEach(gift1 -> {
                System.out.println(gift.name + " - " + String.format(Locale.US, "%.2f", gift.price));

                //});
//                    for (int j = 0; j < samePref.size(); j++) {
//                        Gift showGift = samePref.get(j);
//                        System.out.println(showGift.name + " - " + showGift.price);
//                    }

            }
        }
    }

    public static class Gift {
        private String name;
        private int preference;
        private double price;

        public Gift(String name, int preference, double price) {
            this.name = name;
            this.preference = preference;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPreference() {
            return preference;
        }

        public double getPrice() {
            return price;
        }
    }

    private static boolean theresIsEmoticon(String phrase,  int minSize, int maxSize, Set<String> emoticons) {
        String potEmoticon = "";
        for (int i = 0; i < phrase.length(); i++) {
            potEmoticon += phrase.charAt(i);
            if (isEmoticon2(potEmoticon, minSize, maxSize, emoticons)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmoticon2(String potencial, int minSize, int maxSize, Set<String> emoticons) {
        //eh melhor comparar de tras para frente, ultimo char first
        if (potencial.length() < minSize) {
            return false;

        }

//        if (potencial.length() >= minSize && potencial.length() <= maxSize) {
//            if (emoticons.contains(potencial)) {
//                return true;
//            }
//            else {
//                return false;
//            }
//        }

        if (emoticons.contains(potencial.substring(potencial.length() - minSize)) || emoticons.contains(potencial) ) {
            return true;
        }  if (maxSize <= potencial.length()) {
           if (emoticons.contains(potencial.substring(potencial.length() - maxSize))) {
               return true;
            }
        }  if (potencial.length() - minSize - 1 > 0) {
            if (emoticons.contains(potencial.substring(potencial.length() - minSize - 1))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmoticon(String potencial, int minSize, int maxSize, Set<String> emoticons) {
        //eh melhor comparar de tras para frente, ultimo char first
        if (potencial.length() < minSize ) {
            return false;
        }
        for (int i =  potencial.length(); i >= 0; i--) {
            for (int j = 0; j <= maxSize && i - j >= 0; j = j+minSize) {
                if (emoticons.contains(potencial.substring(i - j, i))) {
                    return true;
                }
            }
        }


//        for (int i = 0; i < potencial.length(); i++) {
//            for (int j = 0; j <= maxSize && j + i <= potencial.length(); j++) {
//                if (emoticons.contains(potencial.substring(i, i + j))) {
//                   return true;
//                }
//            }
//        }
        return false;
    }

    private static int bestCase(String phrase, int minSizeEmoticon, int maxSizeEmoticon, Set<String> emoticons) {
        int solution = 100;

        for (int i = 0; i < minSizeEmoticon; i++) {
            int localSolution = 0;
            StringBuilder copyPhrase = new StringBuilder(phrase);
            while (theresIsEmoticon(copyPhrase.toString(), minSizeEmoticon, maxSizeEmoticon, emoticons) ) {
                int actualIndex = 0;
                for (int j = 0; j < copyPhrase.length(); j++) {
                    if (isEmoticon2(copyPhrase.substring(actualIndex, j + 1), minSizeEmoticon, maxSizeEmoticon, emoticons)) {
                        actualIndex = j + 1;
                        copyPhrase.setCharAt(j - i, ' ');
                        localSolution++;
                    }
                    if (localSolution >= solution)
                        break;
                }
                if (localSolution >= solution)
                    break;
            }
            if (localSolution < solution) {
                solution = localSolution;
            }
        }
        return solution;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in).useLocale(Locale.US);
        String input;
        List<Integer> sol = new ArrayList<>();

        do {
            int minSize = 100, maxSize = 0;
            input = reader.nextLine();
            int qtdEmoticons = Integer.parseInt(input.split(" ")[0]);
            int qtdPhrases = Integer.parseInt(input.split(" ")[1]);
            Set<String> emoticons = new HashSet<>();
            if (qtdEmoticons == 0 && qtdPhrases == 0) {
                break;
            }

            for (int i = 0; i < qtdEmoticons; i++) {
                String emoticon = reader.nextLine();
                emoticons.add(emoticon);
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
                solution += bestCase(nextPhrase, minSize, maxSize, emoticons);
            }

            sol.add(solution);

        } while (true);
        sol.forEach(System.out::println);


    }
}

package com.gabriel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static class CountingSort {
        List<Gift> sort(int arr[], Wishlist wishlist) {
            int n = arr.length;
            List<Gift> newWish = new ArrayList(Collections.nCopies(n, new Gift("", 0, 0)));

            int count[] = new int[102];
            for (int i = 0; i < 100; i++)
                count[i] = 0;

            for (int i = 0; i < n; i++)
                ++count[arr[i]];

            for (int i = 1; i <= 101; i++)
                count[i] += count[i - 1];

            for (int i = n - 1; i >= 0; i--) {
                newWish.set(count[arr[i]] - 1, wishlist.giftList.get(i));
                --count[arr[i]];
            }

            return newWish;
        }
    }

    public static class Wishlist {
        private String owner;
        private List<Gift> giftList;

        public Wishlist(String owner, List<Gift> giftList) {
            this.owner = owner;
            this.giftList = giftList;
        }

        public void addWish(Gift gift) {
            this.giftList.add(gift);
        }

        public void sortList() {
            int preference[] = new int[giftList.size()];
            for (int i = 0; i < giftList.size(); i++) {
                preference[i] = giftList.get(i).getPreference();
            }

            CountingSort countingSort = new CountingSort();
            giftList.sort(Comparator.comparing(Gift::getPreference).thenComparing(Gift::getPrice, Comparator.reverseOrder()).thenComparing(Gift::getName));
            // aqui ja temos uma lista crescente de pref
            //this.giftList = countingSort.sort(preference, this);
        }

        public void showList() {
            NumberFormat formatter = NumberFormat.getInstance(Locale.US);

            int i;
            System.out.println("Lista de " + this.owner);
            for (i = giftList.size() - 1; i >= 0; i--) {
                List<Gift> samePref = new ArrayList<>();
                Gift gift = this.giftList.get(i);
                samePref.add((gift));
                Gift nextGift = null;
//                    if (i - 1 > 0) {
//                         nextGift = this.giftList.get(i - 1);
//                    } else {
//                        nextGift = null;
//                    }
//
//                    while (nextGift != null && gift.preference == nextGift.preference) {
//                        samePref.add(nextGift);
//                        i = i -1;
//
//                        gift = this.giftList.get(i);
//                        if (i - 1 < 0)
//                            break;
//                        nextGift = this.giftList.get(i - 1);
//                    }
//                    samePref.sort(Comparator.comparing(Gift::getPrice).thenComparing(Gift::getName));
//                    //samePref.sort(Comparator.comparing(Gift::getName));
                //samePref.forEach(gift1 -> {
                System.out.println(gift.name + " - " + String.format(Locale.US, "%.2f", gift.price));

                //});
//                    for (int j = 0; j < samePref.size(); j++) {
//                        Gift showGift = samePref.get(j);
//                        System.out.println(showGift.name + " - " + showGift.price);
//                    }

            }
        }
    }

    public static class Gift {
        private String name;
        private int preference;
        private double price;

        public Gift(String name, int preference, double price) {
            this.name = name;
            this.preference = preference;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPreference() {
            return preference;
        }

        public double getPrice() {
            return price;
        }
    }

    private static boolean theresIsEmoticon(String phrase,  int minSize, int maxSize, Set<String> emoticons) {
        String potEmoticon = "";
        for (int i = 0; i < phrase.length(); i++) {
            potEmoticon += phrase.charAt(i);
            if (isEmoticon2(potEmoticon, minSize, maxSize, emoticons)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmoticon2(String potencial, int minSize, int maxSize, Set<String> emoticons) {
        //eh melhor comparar de tras para frente, ultimo char first
        if (potencial.length() < minSize) {
            return false;

        }

//        if (potencial.length() >= minSize && potencial.length() <= maxSize) {
//            if (emoticons.contains(potencial)) {
//                return true;
//            }
//            else {
//                return false;
//            }
//        }

        if (emoticons.contains(potencial.substring(potencial.length() - minSize)) || emoticons.contains(potencial) ) {
            return true;
        }  if (maxSize <= potencial.length()) {
           if (emoticons.contains(potencial.substring(potencial.length() - maxSize))) {
               return true;
            }
        }  if (potencial.length() - minSize - 1 > 0) {
            if (emoticons.contains(potencial.substring(potencial.length() - minSize - 1))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmoticon(String potencial, int minSize, int maxSize, Set<String> emoticons) {
        //eh melhor comparar de tras para frente, ultimo char first
        if (potencial.length() < minSize ) {
            return false;
        }
        for (int i =  potencial.length(); i >= 0; i--) {
            for (int j = 0; j <= maxSize && i - j >= 0; j = j+minSize) {
                if (emoticons.contains(potencial.substring(i - j, i))) {
                    return true;
                }
            }
        }


//        for (int i = 0; i < potencial.length(); i++) {
//            for (int j = 0; j <= maxSize && j + i <= potencial.length(); j++) {
//                if (emoticons.contains(potencial.substring(i, i + j))) {
//                   return true;
//                }
//            }
//        }
        return false;
    }

    private static int bestCase(String phrase, int minSizeEmoticon, int maxSizeEmoticon, Set<String> emoticons) {
        int solution = 100;

        for (int i = 0; i < minSizeEmoticon; i++) {
            int localSolution = 0;
            StringBuilder copyPhrase = new StringBuilder(phrase);
            while (theresIsEmoticon(copyPhrase.toString(), minSizeEmoticon, maxSizeEmoticon, emoticons) ) {
                int actualIndex = 0;
                for (int j = 0; j < copyPhrase.length(); j++) {
                    if (isEmoticon2(copyPhrase.substring(actualIndex, j + 1), minSizeEmoticon, maxSizeEmoticon, emoticons)) {
                        actualIndex = j + 1;
                        copyPhrase.setCharAt(j - i, ' ');
                        localSolution++;
                    }
                    if (localSolution >= solution)
                        break;
                }
                if (localSolution >= solution)
                    break;
            }
            if (localSolution < solution) {
                solution = localSolution;
            }
        }
        return solution;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in).useLocale(Locale.US);
        String input;
        List<Integer> sol = new ArrayList<>();

        do {
            int minSize = 100, maxSize = 0;
            input = reader.nextLine();
            int qtdEmoticons = Integer.parseInt(input.split(" ")[0]);
            int qtdPhrases = Integer.parseInt(input.split(" ")[1]);
            Set<String> emoticons = new HashSet<>();
            if (qtdEmoticons == 0 && qtdPhrases == 0) {
                break;
            }

            for (int i = 0; i < qtdEmoticons; i++) {
                String emoticon = reader.nextLine();
                emoticons.add(emoticon);
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
                solution += bestCase(nextPhrase, minSize, maxSize, emoticons);
            }

            sol.add(solution);

        } while (true);
        sol.forEach(System.out::println);


    }
}

