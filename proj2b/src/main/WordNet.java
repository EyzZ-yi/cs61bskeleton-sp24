package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordNet {
    //List<int[]> TxtListOfWords=new ArrayList<>();
    HashSet<String> ResOfWords=new HashSet<>();
    HashMap<String, HashSet<Integer>> TxtListMeanToWords=new HashMap<>();
    HashMap<Integer,HashSet<String>> TxtListWordToMean=new HashMap<>();
   public Set<Integer> ConvertOfWToM(String word){
        return TxtListMeanToWords.get(word);
   }
   public Set<String> ConvertOfMToW(Integer id){
       return TxtListWordToMean.get(id);
   }
   public Set<String> GetResult(Set<Integer> IdResult){
       for(Integer id :IdResult){
           ResOfWords.addAll(ConvertOfMToW(id));
       }
       return ResOfWords;
   }

    public void ReadTxtExample(){
        String filePath = "synsets.txt"; // 替换为你的文件路径
        List<String[]> resultList = new ArrayList<>();

        // 使用 try-with-resources 自动关闭资源
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // 1. 逐行读取
            while ((line = br.readLine()) != null) {
                // 2. 数据分割 (例如：使用逗号分割)
                String[] data = line.split(","); // 可用正则表达式如 "[,\\s]+" 分割多个空格或逗号

                // 3. 处理分割后的数据
                resultList.add(data);
                TxtListWordToMean.computeIfAbsent(Integer.valueOf(data[1]),k->new HashSet<>()).add(data[0]);
                TxtListMeanToWords.computeIfAbsent(data[0],k->new HashSet<>()).add(Integer.valueOf(data[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
