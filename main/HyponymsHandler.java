package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;

    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int kValue = q.k();

        Set<String> commonHyponyms = new HashSet<>(wn.hyponyms(words.get(0)));
        for (int i = 1; i < words.size(); i++) {
            commonHyponyms.retainAll(wn.hyponyms(words.get(i)));
        }

        if (kValue != 0) {
            Map<String, Double> wordCounts = new HashMap<>();

            for (String hyponym : commonHyponyms) {
                TimeSeries ts = ngm.countHistory(hyponym, startYear, endYear);
                double totalCount = 0;
                for (Double count : ts.values()) {
                    totalCount += count;
                }
                if (totalCount > 0) {
                    wordCounts.put(hyponym, totalCount);
                }
            }

            List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>(wordCounts.entrySet());
            sortedEntries.sort(new Comparator<>() {
                @Override
                public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) {
                    int valueComparison = e2.getValue().compareTo(e1.getValue());
                    if (valueComparison != 0) {
                        return valueComparison;
                    } else {
                        return e1.getKey().compareTo(e2.getKey());
                    }
                }
            });

            Set<String> topHyponyms = new HashSet<>();
            for (int i = 0; i < Math.min(kValue, sortedEntries.size()); i++) {
                topHyponyms.add(sortedEntries.get(i).getKey());
            }

            commonHyponyms = topHyponyms;
        }

        List<String> sortedHyponyms = new ArrayList<>(commonHyponyms);
        Collections.sort(sortedHyponyms);

        return "[" + String.join(", ", sortedHyponyms) + "]";
    }
}
