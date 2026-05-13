package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordNet {
    //HashSet<String> ResOfWords=new HashSet<>();
    HashMap<Integer,HashSet<String>>  TxtListMeanToWords=new HashMap<>();
    HashMap<String, HashSet<Integer>> TxtListWordToMean=new HashMap<>();
    public WordNet(String synsetsFile) {
        ReadTxtExample(synsetsFile);   // 构造时读一次

    }
   public HashSet<Integer> ConvertOfWToM(String word){
        return TxtListWordToMean.get(word);
   }
   public HashSet<String> ConvertOfMToW(Integer id){
       return TxtListMeanToWords.get(id);
   }
   public Set<String> GetResult(Set<Integer> IdResult){
      // ResOfWords.clear();
       Set<String> result = new HashSet<>();
       for(Integer id :IdResult){
           result.addAll(ConvertOfMToW(id));
       }
       return result;
   }

    public void ReadTxtExample(String synsetsFile){
        String filePath = synsetsFile; // 替换为你的文件路径
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
                String[] words=data[1].split("\\s+");
                for(String word :words) {
                    TxtListWordToMean.computeIfAbsent(word, k -> new HashSet<>()).add(Integer.valueOf(data[0]));
                    TxtListMeanToWords.computeIfAbsent(Integer.valueOf(data[0]), k -> new HashSet<>()).add(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
