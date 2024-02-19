package com.zerobase.shopping;

import com.zerobase.shopping.commons.dto.SearchOption;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShoppingApplicationTests {
  @Test
  void testSearch() {
    SearchOption searchOption = new SearchOption();
    String type = searchOption.getType();
    String word = searchOption.getWord();
    System.out.println(type != null);
    System.out.println(word != null);
  }
}
