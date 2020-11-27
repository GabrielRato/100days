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
                fibFirstMap.put(i, fibFirstMap.get(i - 2).add(fibFirstMap.get(i - 1)));
            }
            nextFib = fibFirstMap.get(i);

            period[Math.toIntExact(i)] = nextFib.mod(cons).longValue();
            if (i == 100000) {
                periodFound = true;
                periodLength = i - 1;
            } else if (period[(int) (i - 1)] == 1 && period[(int) (i - 2)] == 0) {
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

            f.put(i, (f.get(i - 1) + f.get(i - 2)) % base);
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

    public static class Vertice {
        Integer idxLabel;
        List<Integer> aresta;

        public Vertice(Integer idxLabel, List<Integer> aresta) {
            this.idxLabel = idxLabel;
            this.aresta = aresta;
        }
    }

    public static class Grafo {
        Map<Integer, Vertice> vertices;

        public Grafo() {
            this.vertices = new HashMap<>();
        }

        public void insere(int in, int out) {
            if (this.vertices.get(in) == null) {
                this.vertices.put(in, new Vertice(in, new ArrayList<>()));
            }

            if (this.vertices.get(out) == null) {
                this.vertices.put(out, new Vertice(out, new ArrayList<>()));
            }
            this.vertices.get(in).aresta.add(out);
            this.vertices.get(out).aresta.add(in);
        }

        public void resolve(Integer k) {
            //this.vertices.forEach(x -> x.aresta.size());
            List<Integer> sizes = new ArrayList<>();
            this.vertices.forEach((key, v) -> sizes.add(v.aresta.size()));

            int qtd = 0;
            sizes.sort(Comparator.reverseOrder());

            for (int i = 0; i < sizes.size(); i++) {
                qtd += sizes.get(i);
                if (qtd == k) {
                    System.out.println("S");
                    return;
                }
            }
            System.out.println("N");
        }
    }


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

    //
//    public static boolean canInclude(Point curr, List<Point> points) {
//        List<Point> candidate = new ArrayList<>();
//        //dumb
//        for (int i = 0; i < points.size(); i++) {
//            if (points.get(i).x == curr.x + 1) {
//                candidate.add(points.get(i));
//            }
//        }
//    }
    public static class Node {

        private String name;


        private Map<String, Integer> shortestPath = new HashMap<>();

        private Integer distance = Integer.MAX_VALUE;

        Map<String, Integer> adjacentNodes = new HashMap<>();

        public void addDestination(String destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

//        public void updateDestination(String destination, int distance) {
//            adjacentNodes.put(destination, distance);
//        }

        public Node(String name) {
            this.name = name;
        }

        // tenho q saber todos Nos daquele grafo...
        public Map<String, Integer> initiateDist(List<Node> nodes) {
            nodes.forEach(node -> shortestPath.put(node.name, Integer.MAX_VALUE));
            return this.shortestPath;
        }

        public void relax(String destination, Integer custo) {
            shortestPath.put(destination, custo);
//            this.shortestPath = shortestPath;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Map<String, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }
// getters and setters
    }

    public static class Graph {

        private List<Node> nodes = new ArrayList<>();
        private Map<String, Integer> nameToIdx = new HashMap<>();


        public int sameCountry(String destination, String source) {
            for (int i = 0; i < nodes.size(); i++) {
                if (nodes.get(i).name.equals(destination)) {
                    if (nodes.get(i).adjacentNodes.get(source) != null) {
                        return i;
                    }
                }

            }
            return -1;
        }

        public Node getMinOpen(List<String> alreadyClosed) {
            return nodes.stream().filter(x -> !alreadyClosed.contains(x.name)).min(Comparator.comparingInt(x -> x.distance)).get();
        }

        public void addNode(Node nodeA) {
            if (nameToIdx.get(nodeA.name) == null)
                nameToIdx.put(nodeA.name, nodes.size());
            nodes.add(nodeA);
        }

        // getters and setters
    }


    public static String getMinOpen(Map<String, Integer> shortestPath, Set<String> alreadyClosed) {
        return shortestPath.entrySet().stream().filter(node -> !alreadyClosed.contains(node.getKey())).min(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();

    }

    private static Map<String, Integer> dijkstra(Graph graph, Node source) {
        source.initiateDist(graph.nodes);
        Map<String, Integer> shortestPath = new HashMap<>(source.initiateDist(graph.nodes));
        shortestPath.put(source.name, 0);
        Set<String> alreadyClosed = new HashSet<>();

        int i = 0;
        while (i < graph.nodes.size()) {
            String name = getMinOpen(shortestPath, alreadyClosed);
            Integer idxCurr = graph.nameToIdx.get(name);
            if (idxCurr == null) {
                i++;
                alreadyClosed.add(name);
                continue;
            }
            Node currNode = graph.nodes.get(idxCurr);

            //iterar sobre mapa
            for (Map.Entry<String, Integer> adjacencyPair : currNode.getAdjacentNodes().entrySet()) {
//                if (adjacencyPair.getValue() + shortestPath.get(currNode.name) < shortestPath.getOrDefault(adjacencyPair.getKey(), Integer.MAX_VALUE -1)) {
                 if (shortestPath.get(currNode.name) != Integer.MAX_VALUE && adjacencyPair.getValue() + shortestPath.get(currNode.name) < shortestPath.getOrDefault(adjacencyPair.getKey(),Integer.MAX_VALUE )) {
                    shortestPath.put(adjacencyPair.getKey(), adjacencyPair.getValue() + shortestPath.get(currNode.name));
                }
            }
            alreadyClosed.add(currNode.name);
            i++;
        }
        return shortestPath;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        Integer n = reader.nextInt();
        Integer e = reader.nextInt();
        while (n != 0) {

            Graph graph = new Graph();
            // carrega arestas considerar ter uma estrutura de <name,idx>
            for (int i = 0; i < e; i++) {
                String source = String.valueOf(reader.nextInt());
                String destination = String.valueOf(reader.nextInt());
                Node node;
                if (graph.nameToIdx.get(source) != null) {
                    node = graph.nodes.get(graph.nameToIdx.get(source));
                } else {
                    node = new Node(source);
                }
                int idx = graph.sameCountry(destination, node.name);
                if (idx != -1) {
                    Node possible = graph.nodes.get(idx);
                    reader.nextInt();
                    node.addDestination(destination, 0);
                    possible.addDestination(source, 0);

                } else {
                    node.addDestination(destination, reader.nextInt());
                }
                if (graph.nameToIdx.get(source) == null) {
                    graph.addNode(node);
                }
            }

            //test case
            int k = reader.nextInt();
//            System.out.println("curr k: " + k);
//
//            if (e == 0) {
//                System.out.println("Nao e possivel entregar a carta");
//                k = -1;
//            }
            Map<String, Map<String, Integer>> solutions = new HashMap<>();
            for (int i = 0; i < k; i++) {
                String source = String.valueOf(reader.nextInt());
                String destination = String.valueOf(reader.nextInt());
                if (graph.nameToIdx.get(source) == null) {
                    System.out.println("Nao e possivel entregar a carta");
                } else {
                    Node goPoint = graph.nodes.get(graph.nameToIdx.get(source));
                    if (solutions.get(source) == null) {
                        Map<String, Integer> paths = dijkstra(graph, goPoint);
                        solutions.put(source, paths);
                    }
                    System.out.println(solutions.get(source).get(destination) != Integer.MAX_VALUE ? solutions.get(source).get(destination) : "Nao e possivel entregar a carta");
                }
                //System.out.println(calculateMinimumDistance(goPoint,););
            }
            n = reader.nextInt();
            if (n == 0) {
                System.out.println();
                break;
            }
            e = reader.nextInt();
            System.out.println();
        }

    }
}

