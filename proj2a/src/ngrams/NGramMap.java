package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */

/**
 * * 一个提供实用方法的对象，用于对 Google NGrams 数据集（或其子集）进行查询。
 * *
 * * NGramMap 存储来自“单词文件”和“计数文件”的相关数据。它并非严格意义上的映射表，
 * * 但它确实提供了额外的功能。
 * *
 * * @author Josh Hug
 *
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    // TODO：添加任何必要的静态/实例变量。
    long[] counts=new long[10000];//1400 of index is 0 So index=n-1400 701
    TreeMap<String, HashMap<Integer,Long>> words=new TreeMap<>();//words,year,number
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    /**
     * 根据 WORDSFILENAME 和 COUNTSFILENAME 文件构建 NGramMap。
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In Count=new In(countsFilename);
        while (Count.hasNextLine()){
            String tmp=Count.readLine();
            String[] splittmp=tmp.split(",");
            int year=Integer.parseInt(splittmp[0]);
            long number=Long.parseLong(splittmp[1]);
            int index=year-1400;
            if(index>=0 &&index<=700) counts[index]=number;
        }
        In Words=new In(wordsFilename);
        while (Words.hasNextLine()){
            String tmp=Words.readLine();
            String[] splittmp=tmp.split("\t");
            String word=splittmp[0];
            int year=Integer.parseInt(splittmp[1]);
            long number=Long.parseLong(splittmp[2]);
            if (!words.containsKey(word)) {
                words.put(word, new HashMap<>());
            }
            words.get(word).put(year,number);
        }
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    /**
     * 提供单词 WORD 在 STARTYEAR 和 ENDYEAR 之间（包括首尾年份）的出现频率历史记录。
     * 返回的 TimeSeries 应该是副本，而不是指向此 NGramMap 的 TimeSeries 的链接。
     * 换句话说，对此函数返回的对象所做的更改不应影响 NGramMap。这也被称为“防御性复制”。
     * 如果该单词不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries cmp=new TimeSeries();
        if(!words.containsKey(word)) return cmp;
        for(int i=startYear;i<=endYear;i++){
            if(words.get(word).containsKey(i)) cmp.put(i,1.0*words.get(word).get(i));
        }
        return cmp;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    /**
     * 提供单词的历史数据。返回的 TimeSeries 应该是副本，而不是指向此 NGramMap 的 TimeSeries 的链接。
     * 换句话说，对此函数返回的对象所做的更改不应影响 NGramMap。这也被称为“防御性复制”。
     * 如果单词不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries cmp=new TimeSeries();
        cmp=countHistory(word,MIN_YEAR,MAX_YEAR);
        return cmp;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    /**
     * 返回所有卷册中每年记录的单词总数的副本（以防止外部修改）。
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        TimeSeries cmp=new TimeSeries();
        for(int i=MIN_YEAR;i<=MAX_YEAR;i++){
            cmp.put(i,(double)counts[i-1400]);
        }
        return cmp;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    /**
     * 提供一个时间序列，其中包含单词 WORD 在 STARTYEAR 和 ENDYEAR 之间（包括首尾年份）每年的相对频率。
     * 如果数据文件中不存在该单词，则返回一个空的时间序列。
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries cmp=new TimeSeries();
        if(!words.containsKey(word)) return cmp;
        for(int i=startYear;i<=endYear;i++){
            if(words.get(word).containsKey(i)) cmp.put(i,1.0*words.get(word).get(i)/counts[i-1400]);
        }
        return cmp;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    /**
     * 提供一个时间序列，其中包含单词 WORD 在每一年相对于当年所有单词的相对频率。
     * 如果该单词不在数据文件中，则返回一个空的时间序列。
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries cmp=new TimeSeries();
        cmp=weightHistory(word,MIN_YEAR,MAX_YEAR);
        return cmp;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    /**
     * * 计算 WORDS 列表中所有单词在 STARTYEAR 和 ENDYEAR（包括首尾年份）之间每年的相对频率总和。
     * * 如果某个单词在此时间范围内不存在，则忽略该单词，而不是抛出异常。
     *
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
//        TimeSeries cmp=new TimeSeries();
//        String[] n=words.toArray(new String[0]);
//        TimeSeries per=new TimeSeries();
//        for(int l=startYear;l<=endYear;l++) {
//            double sum = 0;
//            for (int i = 0; i < n.length; i++) {
//                per = weightHistory(n[i], startYear, endYear);}
//                List<Double> m = per.data();
//                for (int q = 0; q < m.size(); q++) sum += m.get(q);
//            cmp.put(l, sum);
//        }
//        return cmp;
        TimeSeries sumSeries = new TimeSeries();

        // 遍历每一个要求的单词 (比如 fish, dog)
        for (String word : words) {
            // 1. 算出这个词的权重曲线 (调用你写好的方法)
            TimeSeries wordWeights = weightHistory(word, startYear, endYear);

            // 2. 把这个词的每一个数据点，加到总和 sumSeries 里
            // (因为 TimeSeries 继承自 TreeMap，我们可以遍历它的 Key)
            for (int year : wordWeights.keySet()) {
                double currentWordWeight = wordWeights.get(year);

                // 拿出 sumSeries 里这一年原本的值（如果是第一次加，就是0）
                double existingSum = 0.0;
                if (sumSeries.containsKey(year)) {
                    existingSum = sumSeries.get(year);
                }

                // 累加
                sumSeries.put(year, existingSum + currentWordWeight);
            }
        }
        return sumSeries;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    /**
     * * 返回 WORDS 中所有单词在每年的相对频率总和。如果某个单词在此时间范围内不存在，则忽略该单词，而不是抛出异常。
     *
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.

       TimeSeries cmp=new TimeSeries();
       cmp=summedWeightHistory(words,MIN_YEAR,MAX_YEAR);
        return cmp;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
