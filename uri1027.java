import java.util.*;

import static java.lang.Integer.max;


public class Main {

    public static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        while (reader.hasNextInt()) {
            List<Point> list = new ArrayList<>();
            int[] top = new int[100000];
            int[] bottom = new int[100000];
            Integer n = reader.nextInt();
            for (int j = 0; j < n; j++) {
                Integer x = reader.nextInt();
                Integer y = reader.nextInt();
                list.add(new Point(x, y));
            }
            top[0] = 1;
            bottom[0] = 1;
            Collections.sort(list, (o1, o2) -> {
                if (o1.getX() > o2.getX()) {
                    return 1;
                } else if (o1.getX() == (o2.getX())) {
                    return 0;
                } else {
                    return -1;
                }
            });

            for (int i = 1; i < n; i++) {
                int localMaxTop = 1;
                int localMaxBot = 1;
                for (int j = 0; j < i; j++) {

                    if (list.get(i).getX() > list.get(j).getX() && list.get(i).getY() == list.get(j).getY() + 2)
                        localMaxTop = max(localMaxTop, bottom[j] + 1);

                    if (list.get(i).getX() > list.get(j).getX() && list.get(i).getY() == list.get(j).getY() - 2)
                        localMaxBot = max(localMaxBot, top[j] + 1);

                }
                top[i] = localMaxTop;
                bottom[i] = localMaxBot;
            }

            int maximo = 1;
            for (int i = 0; i < n; i++) {
                maximo = max(maximo, top[i]);
                maximo = max(maximo, bottom[i]);
            }
            System.out.println(maximo);

        }

    }
}

