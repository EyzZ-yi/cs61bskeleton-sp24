# Project2B

##WordNet   读取输入的words转化为id，调用Graph返回结果
*map<String,List<Integer>> 利用word映射id（同一个 word 可能属于多个 synset，对应多个 id）

###List<Integer> Convert(String word) 利用word映射id（同一个 word 可能属于多个 synset，对应多个 id）
###Set<String> GetResult(Set IdResult) 把id集合转化成word数组的最终答案

##Graph     构建图，遍历图
*map<Integer，List<Integer>> 利用id构建图

###map<Integer，List<Integer>> CreatGraph()       构建图
###Set<Integer> Find(int id)     遍历图表，返回id“下位词”的id集合
