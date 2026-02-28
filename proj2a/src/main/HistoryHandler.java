package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap p;
    public HistoryHandler(NGramMap map){
    p=map;}

    @Override
    public String handle(NgordnetQuery q) {
        //parabola.put(x,y)
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int n=words.size();
        ArrayList<TimeSeries>word = new ArrayList<>();
        ArrayList<String> name=new ArrayList<>();
        for(int i=0;i<n;i++){
            TimeSeries cmp=p.weightHistory(words.get(i),startYear,endYear);
            name.add(words.get(i));
            word.add(cmp);
        }
        XYChart chart = Plotter.generateTimeSeriesChart(name,word);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;

    }
}
