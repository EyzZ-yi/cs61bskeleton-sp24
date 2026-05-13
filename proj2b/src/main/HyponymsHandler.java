package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import edu.princeton.cs.algs4.In;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet WordNet1;
    Graph Graph1;

    public HyponymsHandler(String synsetFile, String hyponymFile){
        WordNet1=new WordNet(synsetFile);
        Graph1=new Graph(hyponymFile);
    }
    HashSet<Integer> ids;

    public Set<String> helpOfhandle(String word) {
        Graph1.resOfId.clear();
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
        Set<String> res=helpOfhandle(words.getFirst());
        if(words.size()>1) {
            for (int i=1;i<words.size();i++) {
                res.retainAll(helpOfhandle(words.get(i)));
            }
        }
        List<String> list=new ArrayList<>();
        list.addAll(res);
        Collections.sort(list);
        return list.toString();
    }
}
