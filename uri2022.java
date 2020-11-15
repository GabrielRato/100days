package com.gabriel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

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
                 newWish.set(count[arr[i]] - 1,  wishlist.giftList.get(i));
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
                for (i = giftList.size() - 1; i >= 0 ; i--) {
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

        public static void main(String[] args) {
            Scanner reader = new Scanner(System.in).useLocale(Locale.US);
            String owner, giftName, input = null;
            List<Wishlist> allWishList = new ArrayList<>();
            double price;
            int preference;
            String next = null;

            input = reader.nextLine();
            do {
                if (next != null && next.equals("")) {
                    input = reader.nextLine();
                    input = reader.nextLine();
                    if (input.split(" ").length == 1)
                        break;
                }
                owner = input.split(" ")[0];

                int qtd = Integer.parseInt(input.split(" ")[1]);
                Wishlist myWishList = new Wishlist(owner, new ArrayList<>());
                for (int i = 0; i < qtd; i++) {
                    giftName = reader.nextLine();
                    price = reader.nextFloat();
                    preference = reader.nextInt();
                    Gift myGift = new Gift(giftName, preference, price);
                    myWishList.addWish(myGift);
                    next = reader.nextLine();
                }
                allWishList.add(myWishList);
            } while (true);

            allWishList.forEach(wishlist -> {
                wishlist.sortList();
                wishlist.showList();
                System.out.println();
            });

    }
}

