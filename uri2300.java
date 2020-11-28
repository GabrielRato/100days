package com.gabriel;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int testCase = 0;
        Integer n = reader.nextInt();
        Integer e = reader.nextInt();
        while (e != 0) {
            testCase++;
            Graph graph = new Graph();

            for (int i = 0; i < e; i++) {
                String source = String.valueOf(reader.nextInt());
                String destination = String.valueOf(reader.nextInt());
                Node nodeSource;
                Node nodeDestination;
                if (graph.getNode(source) != null) {
                    nodeSource = graph.getNode(source);
                } else {
                    nodeSource = new Node(source);
                }

                if (graph.nameToIdx.get(destination) != null) {
                    nodeDestination = graph.getNode(destination);
                } else {
                    nodeDestination = new Node(destination);
                }

                // nao importa o peso
                nodeSource.addDestination(destination, 1);
                nodeDestination.addDestination(source, 1);

                if (graph.nameToIdx.get(source) == null) {
                    graph.addNode(nodeSource);
                }
                if (graph.nameToIdx.get(destination) == null) {
                    graph.addNode(nodeDestination);
                }
            }

            if (n > graph.nodes.size()) {
                System.out.println("Teste " + testCase);
                System.out.println("falha");
                System.out.println();
            } else {
                System.out.println("Teste " + testCase);
                System.out.println(graph.isConnected(n) ? "normal" : "falha");
                System.out.println();
            }

            n = reader.nextInt();
            if (n == 0) {
                break;
            }
            e = reader.nextInt();

        }

    }

    public static class Node {

        Map<String, Integer> adjacentNodes = new HashMap<>();
        private String name;

        public Node(String name) {
            this.name = name;
        }

        public void addDestination(String destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

        public Map<String, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }
    }

    public static class Graph {

        private List<Node> nodes = new ArrayList<>();
        private Map<String, Integer> nameToIdx = new HashMap<>();

        public boolean isConnected(int qtdNodes) {
            AtomicInteger qtd = new AtomicInteger();
            nodes.forEach(node -> qtd.addAndGet(canReachAllNodes(node, qtdNodes) ? 1 : 0));
            return qtd.intValue() == nodes.size();
        }

        public Node getNode(String source) {
            if (nameToIdx.get(source) == null) return null;
            return nodes.get(nameToIdx.get(source));
        }

        public boolean canReachAllNodes(Node source, int qtdNodes) {
            Queue<Node> nodeQueue = new ArrayDeque<>();
            Set<String> visited = new HashSet<>();

            visited.add(source.name);
            nodeQueue.add(source);

            while (!nodeQueue.isEmpty()) {
                Node currNode = nodeQueue.remove();
                for (Map.Entry<String, Integer> adjacencyPair : currNode.getAdjacentNodes().entrySet()) {
                    if (!visited.contains(adjacencyPair.getKey())) {
                        visited.add(adjacencyPair.getKey());
                        nodeQueue.add(nodes.get(nameToIdx.get(adjacencyPair.getKey())));
                    }
                }
            }
            return qtdNodes == visited.size();
        }

        public void addNode(Node nodeA) {
            if (nameToIdx.get(nodeA.name) == null)
                nameToIdx.put(nodeA.name, nodes.size());
            nodes.add(nodeA);
        }

    }
}
