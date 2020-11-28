import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in).useLocale(Locale.US);
        String owner, giftName, input;
        List<Wishlist> allWishList = new ArrayList<>();
        double price;
        int preference;
        int qtd;

        while (reader.hasNext()) {
            input = reader.nextLine();
            owner = input.split(" ")[0];
            try {
                qtd = Integer.parseInt(input.split(" ")[1]);
            } catch (Exception e) {
                break;
            }
            Wishlist myWishList = new Wishlist(owner, new ArrayList<>());
            for (int i = 0; i < qtd; i++) {
                giftName = reader.nextLine();
                input = reader.nextLine();
                price = Float.parseFloat(input.split(" ")[0]);
                preference = Integer.parseInt(input.split(" ")[1]);
                Gift myGift = new Gift(giftName, preference, price);
                myWishList.addWish(myGift);

            }
            allWishList.add(myWishList);
        }

        allWishList.forEach(wishlist -> {
            wishlist.sortList();
            wishlist.showList();
            System.out.println();
        });

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
            this.giftList.sort(Comparator.comparing(Gift::getPreference).reversed().thenComparing(Gift::getPrice).thenComparing(Gift::getName).reversed());
        }

        public void showList() {
            System.out.println("Lista de " + this.owner);
            DecimalFormat df = new DecimalFormat("######.00");

            for (int i = this.giftList.size() - 1; i >= 0; i--) {
                Gift gift = this.giftList.get(i);
                System.out.println(gift.name + " - " + "R$" + df.format(gift.price));

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
}

