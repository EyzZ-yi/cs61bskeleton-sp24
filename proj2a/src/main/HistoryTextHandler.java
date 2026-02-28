package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.List;

import static utils.Utils.SHORT_WORDS_FILE;
import static utils.Utils.TOTAL_COUNTS_FILE;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap cmp;
    public HistoryTextHandler(NGramMap map){
    cmp=map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response=new String();
        for(int i=0;i<words.size();i++){
            List<Double> wordsOfweight=cmp.weightHistory(words.get(i),startYear,endYear).data();
            int k=0;
            response+=words.get(i)+": {";
            for(int j=startYear;j<=endYear;j++){
                    if(k<wordsOfweight.size() && wordsOfweight.get(k)>0.0){
                    response+=j+"="+wordsOfweight.get(k);
                    if(j<endYear) response+=", ";
                    }
                    k++;
            }
            response+="}";
            if(i<words.size()-1) response+="\n";
        }
        return response;
    }
}
