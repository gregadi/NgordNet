package main;

import java.util.*;

public class Graph<T> {
    private HashMap<T, HashSet<T>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addVertex(T node) {
        adjacencyList.putIfAbsent(node, new HashSet<>());
    }

    public void addEdge(T from, T to) {
        if (!adjacencyList.containsKey(from)) {
            addVertex(from);
        }
        adjacencyList.get(from).add(to);
    }

    public HashSet<T> getNeighbors(T node) {
        return adjacencyList.getOrDefault(node, new HashSet<>());
    }

    public HashSet<T> getVertices() {
        return new HashSet<>(adjacencyList.keySet());
    }

    public boolean hasVertex(T node) {
        return adjacencyList.containsKey(node);
    }

    public boolean hasEdge(T from, T to) {
        return adjacencyList.containsKey(from) && adjacencyList.get(from).contains(to);
    }
}
