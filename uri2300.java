package com.gabriel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Main {

    public static String getMinOpen(Map<String, Integer> shortestPath, Set<String> alreadyClosed) {
        return shortestPath.entrySet().stream().filter(node -> !alreadyClosed.contains(node.getKey())).min(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
    }

    private static void dijkstra(Graph graph, Node source) {
        source.initiateDist(graph.nodes);
        source.shortestPath.put(source.name, 0);
        Set<String> alreadyClosed = new HashSet<>();

        int i = 0;
        while (i < graph.nodes.size()) {
            String name = getMinOpen(source.shortestPath, alreadyClosed);

            Node currNode = graph.getNode(name);
            if (currNode == null) {
                i++;
                alreadyClosed.add(name);
                continue;
            }

            for (Map.Entry<String, Integer> adjacencyPair : currNode.getAdjacentNodes().entrySet()) {
                if (source.shortestPath.get(currNode.name) != Integer.MAX_VALUE && adjacencyPair.getValue() + source.shortestPath.get(currNode.name) < source.shortestPath.getOrDefault(adjacencyPair.getKey(), Integer.MAX_VALUE)) {
                    source.shortestPath.put(adjacencyPair.getKey(), adjacencyPair.getValue() + source.shortestPath.get(currNode.name));
                }
            }
            alreadyClosed.add(currNode.name);
            i++;
        }
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        Integer n = reader.nextInt();
        Integer e = reader.nextInt();
        while (n != 0) {

            Graph graph = new Graph();
            for (int i = 0; i < e; i++) {
                String source = String.valueOf(reader.nextInt());
                String destination = String.valueOf(reader.nextInt());
                Node node = graph.getNode(source);
                if (node == null) {
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

                graph.addNodeIfNew(node);
            }

            int k = reader.nextInt();
            Set<String> computedAlready = new HashSet<>();
            for (int i = 0; i < k; i++) {
                String source = String.valueOf(reader.nextInt());
                String destination = String.valueOf(reader.nextInt());
                if (graph.getNode(source) == null) {
                    System.out.println("Nao e possivel entregar a carta");
                } else {
                    Node goPoint = graph.getNode(source);
                    if(!computedAlready.contains(goPoint.name)) {
                        dijkstra(graph, goPoint);
                        computedAlready.add(goPoint.name);
                    }
                    System.out.println(goPoint.shortestPath.get(destination) != Integer.MAX_VALUE ? goPoint.shortestPath.get(destination) : "Nao e possivel entregar a carta");
                }
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

    public static class Node {

        Map<String, Integer> adjacentNodes = new HashMap<>();
        private String name;
        private Map<String, Integer> shortestPath = new HashMap<>();

        public Node(String name) {
            this.name = name;
        }

        public void addDestination(String destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

        // tenho q saber todos Nos daquele grafo...
        public Map<String, Integer> initiateDist(List<Node> nodes) {
            nodes.forEach(node -> shortestPath.put(node.name, Integer.MAX_VALUE));
            return this.shortestPath;
        }

        public Map<String, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }
    }

    public static class Graph {

        private List<Node> nodes = new ArrayList<>();
        private Map<String, Integer> nameToIdx = new HashMap<>();

        public Node getNode(String source) {
            if (nameToIdx.get(source) == null) return null;
            return nodes.get(nameToIdx.get(source));
        }

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

        public void addNodeIfNew(Node nodeA) {
            if (nameToIdx.get(nodeA.name) == null) {
                nameToIdx.put(nodeA.name, nodes.size());
                nodes.add(nodeA);
            }
        }
    }
}

