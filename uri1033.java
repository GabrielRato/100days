package com.gabriel;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.*;

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

    private static boolean theresIsEmoticon(String phrase, int minSize, int maxSize, Set<String> emoticons) {
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

        if (emoticons.contains(potencial.substring(potencial.length() - minSize)) || emoticons.contains(potencial)) {
            return true;
        }
        if (maxSize <= potencial.length()) {
            if (emoticons.contains(potencial.substring(potencial.length() - maxSize))) {
                return true;
            }
        }
        if (potencial.length() - minSize - 1 > 0) {
            if (emoticons.contains(potencial.substring(potencial.length() - minSize - 1))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmoticon(String potencial, int minSize, int maxSize, Set<String> emoticons) {
        //eh melhor comparar de tras para frente, ultimo char first
        if (potencial.length() < minSize) {
            return false;
        }
        for (int i = potencial.length(); i >= 0; i--) {
            for (int j = 0; j <= maxSize && i - j >= 0; j = j + minSize) {
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
            while (theresIsEmoticon(copyPhrase.toString(), minSizeEmoticon, maxSizeEmoticon, emoticons)) {
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

    public static BigInteger fibCalls(int n) {
        BigInteger f[] = new BigInteger[n + 2];
        int i;
        HashSet baseCase = new HashSet();
        baseCase.add(BigInteger.ZERO);
        baseCase.add(BigInteger.ONE);
        f[0] = BigInteger.ONE;
        f[1] = BigInteger.ONE;


        for (i = 2; i <= n; i++) {
            f[i] = f[i - 1].add(f[i - 2]);
            if (!baseCase.contains(BigInteger.valueOf(i - 1))) {
                f[i] = f[i].add(BigInteger.ONE);
            }
            if (!baseCase.contains(BigInteger.valueOf(i - 2))) {
                f[i] = f[i].add(BigInteger.ONE);
            }
        }

        return f[n];
    }

    public static long pisano(long m) {
        long prev = 0;
        long curr = 1;
        long res = 0;

        for (int i = 0; i < m * m; i++) {
            long temp = 0;
            temp = curr;
            curr = (prev + curr) % m;
            prev = temp;

            if (prev == 0 && curr == 1)
                return i + 1;
        }
        return res;
    }

    public static long pisado2(long modulo) {
        Map<Long, BigInteger> fibFirstMap = new HashMap<Long, BigInteger>();



        long periodLength = 0;
        boolean periodFound = false;
        long[] period = new long[1000000];
        period[0] = 0;
        period[1] = 1;
        period[2] = 1;
        Long i = 3L;
        BigInteger cons = BigInteger.valueOf(modulo);
        BigInteger nextFib;
        while (!periodFound) {

            if (i >= fibFirstMap.size()) {
                fibFirstMap.put(i,fibFirstMap.get(i - 2).add(fibFirstMap.get(i - 1)));
            }
            nextFib = fibFirstMap.get(i);

            period[Math.toIntExact(i)] = nextFib.mod(cons).longValue();
            if (i == 100000) {
                periodFound = true;
                periodLength = i - 1;
            }
            else if (period[(int) (i - 1)] == 1 && period[(int) (i - 2)] == 0) {
                periodFound = true;
                periodLength = i - 2;
            }
            i++;
        }
        return periodLength;
    }

    public static Long fibCalls(Long n, long base) {
//        BigInteger f[] = new BigInteger[n+2];
        if (n < 2) {
            return n;
        }
        Map<Long, Long> f = new HashMap<Long, Long>();
        Long i = 2L;
        HashSet baseCase = new HashSet();
        baseCase.add(0L);
        baseCase.add(1L);
//        f[0] = BigInteger.ONE;
//        f[1] = BigInteger.ONE;
        f.put(0L, 1L);
        f.put(1L, 1L);


//        for (i = 2; i <= n; i++)
        while (i <= n) {

            f.put(i, (f.get(i-1) + f.get(i-2))% base);
            if (!baseCase.contains((i - 1))) {
                f.put(i, (f.get(i) + 1) % base);
            }
            if (!baseCase.contains((i - 2))) {
                f.put(i, (f.get(i) + 1) % base);
            }
            i++;
        }

        return f.get(n);
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int i = 0;

        while (true) {
            i++;
            BigInteger n = reader.nextBigInteger();
            long base = reader.nextLong();
            if (base == 0 && n.equals(BigInteger.ZERO))
                break;

            long period = pisano(base);
            Long num = fibCalls(n.mod(BigInteger.valueOf(period)).longValue(), base);
            if (num == 1) {
                System.out.println(String.format("Case %s: %s %s %s", i, n, base, num));
            } else
                System.out.println(String.format("Case %s: %s %s %s", i, n, base, num + 1));

        }
    }
}

