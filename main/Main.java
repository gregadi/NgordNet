package main;

import browser.NgordnetServer;
import ngrams.NGramMap;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";

        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";

        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wn = new WordNet(synsetFile, hyponymFile);

        hns.startUp();
        hns.register("history", new DummyHistoryHandler(ngm));
        hns.register("historytext", new DummyHistoryTextHandler(ngm));
        hns.register("hyponyms", new HyponymsHandler(wn, ngm));
        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
