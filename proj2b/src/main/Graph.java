package main;

import net.sf.saxon.trans.SymbolicName;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Graph {
    List<int[]> TxtList=new ArrayList<>();
    HashMap<Integer,Set<Integer>> graph=new HashMap();
    public Graph(String hyponymsFile){
        ReadTxtExample(hyponymsFile);
        CreatGraph(); // 在构造函数中直接构建图
    }
    public HashMap<Integer, Set<Integer>>CreatGraph(){//初始化图
        for(int[] list: TxtList ) {
                int length = list.length;
                Set<Integer> set = new HashSet<>();
                for (int i = 1; i < length; i++) {
                    graph.computeIfAbsent(list[0], k -> new HashSet<>()).add(list[i]);
                }
        }
        return graph;
    }
    HashSet<Integer> resOfId=new HashSet<>();
    public void Find(int id){
        resOfId.add(id);
        if(!graph.containsKey(id)){
            return ;
        }
        for(Integer ids :graph.get(id)){
            if(!resOfId.contains(ids)){  // 防止死循环
                Find(ids);
            }
        }
    }
    private void ReadTxtExample(String hyponymsFile){
        String filePath = hyponymsFile; // 替换为你的文件路径
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
                int[] intArray = Arrays.stream(data)
                        .mapToInt(Integer::parseInt) // 将String转换为int
                        .toArray();
                TxtList.add(intArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
