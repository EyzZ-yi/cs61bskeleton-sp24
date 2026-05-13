package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import edu.princeton.cs.algs4.In;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet WordNet1;
    Graph Graph1;
    Set<String> res;

    public HyponymsHandler(String synsetFile, String hyponymFile){
        WordNet1=new WordNet(synsetFile);
        Graph1=new Graph(hyponymFile);
    }
    HashSet<Integer> ids;

    public Set<String> helpOfhandle(String word) {
       ids=WordNet1.ConvertOfWToM(word);
       for(Integer id :ids){
            Graph1.Find(id);
       }
       return WordNet1.GetResult(Graph1.resOfId);
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words=q.words();
        int startYear=q.startYear();
        int endYear=q.endYear();
        int k=q.k();
            res=helpOfhandle(words.getFirst());
        if(words.size()>1) {
            for (String word : words) {
                res.retainAll(helpOfhandle(word));
            }
        }
        return res.toString();
    }
}
