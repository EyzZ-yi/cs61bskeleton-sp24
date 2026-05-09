package ngrams;

import edu.princeton.cs.algs4.Heap;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
/**
 * 一个用于将年份数字（例如 1996）映射到数值数据的对象。
 * 提供了一些对数据分析有用的实用方法。
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    /** 如果有助于提高代码运行速度，您可以假设传递给 NGramMap 的年份参数介于 1400 到 2100 之间。
     * 我们已将这些值存储为常量 MIN_YEAR 和 MAX_YEAR。*/
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    /**
     * 创建 TS 的副本，但仅包含 STARTYEAR 和 ENDYEAR 之间的部分，
     * 包括起始年份和结束年份。
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for(int i=startYear;i<=endYear;i++){
            put(i,ts.get(i));
        }
        // TODO: Fill in this constructor.
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    /**
     * 返回此时间序列中的所有年份（顺序不限）。
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        Integer[] j=keySet().toArray(new Integer[0]);
        List<Integer> k=new ArrayList<>();
        for(int i=0;i< j.length;i++){
            k.add(j[i]);
        }
        return k;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    /**
     * 返回此时间序列的所有数据（顺序不限）。
     * 数据顺序必须与 years() 方法返回的年份顺序一致。
     */
    public List<Double> data() {

        // TODO: Fill in this method.
        Integer[] j=keySet().toArray(new Integer[0]);
        List<Double> k=new ArrayList<>();
        for(int i=0;i< j.length;i++){
            k.add(this.get(j[i]));
        }
        return k;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    /**
     * 返回此时间序列与给定时间序列按年份相加的结果。换句话说，对于每一年，将此时间序列的数据与给定时间序列的数据相加。
     * 应返回一个新的时间序列（不会修改此时间序列）。
     *
     * 如果两个时间序列都不包含任何年份，则返回一个空的时间序列。
     * 如果一个时间序列包含另一个时间序列不包含的年份，则返回的时间序列应存储包含该年份的时间序列的值。
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        ArrayList<Integer>tsOfyear = (ArrayList<Integer>) ts.years();
        ArrayList<Integer>selfOfyear = (ArrayList<Integer>) this.years();
        if(tsOfyear==null &&selfOfyear==null) return new TimeSeries();
        else {
            TimeSeries returnseries=new TimeSeries();
            for(int i=MIN_YEAR;i<=MAX_YEAR;i++){
                if(!ts.containsKey(i) && !this.containsKey(i)) continue;
            if(ts.containsKey(i) && this.containsKey(i)) {
                returnseries.put(i,ts.get(i)+this.get(i));
            }
            if(ts.containsKey(i) && !this.containsKey(i)){
                returnseries.put(i,ts.get(i));
                }
            if(!ts.containsKey(i) && this.containsKey(i)){
                returnseries.put(i,this.get(i));
            }
        }
        return returnseries;}
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    /**
     * 返回此 TimeSeries 中每年值除以 TS 中同年值的商。应返回一个新的 TimeSeries（不会修改此 TimeSeries）。
     *
     * 如果 TS 缺少此 TimeSeries 中存在的年份，则抛出 IllegalArgumentException 异常。
     * 如果 TS 包含此 TimeSeries 中不存在的年份，则忽略该年份。
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries returnseries=new TimeSeries();
        for(int i=MIN_YEAR;i<=MAX_YEAR;i++){
            if(!this.containsKey(i) && !ts.containsKey(i)) continue;
            if(this.containsKey(i) && !ts.containsKey(i)) throw new IllegalArgumentException();
            if(this.containsKey(i) && ts.containsKey(i)){
                returnseries.put(i,this.get(i)/ ts.get(i));
            }
        }
        return returnseries;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
