package main;

import edu.princeton.cs.algs4.In;
import java.util.*;

public class WordNet {
    private final Graph<Integer> graph;
    private final HashMap<Integer, String> idToSynset;
    private final HashMap<String, Set<Integer>> wordToIds;

    public WordNet(String synsetFile, String hyponymFile) {
        graph = new Graph<>();
        idToSynset = new HashMap<>();
        wordToIds = new HashMap<>();

        In synsetIn = new In(synsetFile);
        while (!synsetIn.isEmpty()) {
            String line = synsetIn.readLine();
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String synset = parts[1];
            idToSynset.put(id, synset);

            for (String word : synset.split(" ")) {
                if (!wordToIds.containsKey(word)) {
                    wordToIds.put(word, new HashSet<>());
                }
                wordToIds.get(word).add(id);
            }
        }

        In hyponymIn = new In(hyponymFile);
        while (!hyponymIn.isEmpty()) {
            String line = hyponymIn.readLine();
            String[] parts = line.split(",");
            int hypernym = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                int hyponym = Integer.parseInt(parts[i]);
                graph.addEdge(hypernym, hyponym);
            }
        }
    }

    public Set<String> hyponyms(String word) {
        Set<Integer> originalIds = wordToIds.get(word);
        if (originalIds == null) {
            return Collections.emptySet();
        }

        Set<Integer> visitedIds = new HashSet<>(originalIds);
        Set<String> hyponymsSet = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>(originalIds);

        while (!queue.isEmpty()) {
            int currentId = queue.poll();
            String synset = idToSynset.get(currentId);
            hyponymsSet.addAll(Arrays.asList(synset.split(" ")));

            for (int hyponymId : graph.getNeighbors(currentId)) {
                if (!visitedIds.contains(hyponymId)) {
                    visitedIds.add(hyponymId);
                    queue.add(hyponymId);
                }
            }
        }
        return hyponymsSet;
    }
}
