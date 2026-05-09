package tetris;

import java.util.ArrayList;
import java.util.Random;

/**
 *  一个精确的俄罗斯方块随机生成器。
 *  这可以防止同一个俄罗斯方块形状连续多次出现。
 *
 *  @author Erik Nelson
 */

public class BagRandomizer {

  private Random random;

  // “袋子”中当前值的列表。
  ArrayList<Integer> values;

  // 袋子的总容量。
  int capacity;

  public BagRandomizer(Random r, int n) {
    this.random = r;
    this.capacity = n;

    refillValues();
  }

  /**
   * 将袋子的值重置为包含从 0（含）到 capacity（不含）的整数。
   */
  private void refillValues() {
    ArrayList<Integer> newValues = new ArrayList<>();
    for (int i = 0; i < this.capacity; i++) {
      newValues.add(i);
    }
    values = newValues;
  }

  /**
   * 从袋子中抓取并移除一个随机物品。如果袋子是空的，则重新装满。
   * @return 被移除的整数
   */
  public int getValue() {
    if (values.isEmpty()) {
      refillValues();
    }

    int randomIndex = random.nextInt(values.size());
    int randomValue = values.get(randomIndex);

    values.remove(randomIndex);
    return randomValue;
  }
}
