package com.example.spring.spel;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Date:     2019年08月13日 15:14 <br/>
 *
 * @author lcc
 * @since 1.0.0
 */
public class SpelDemo {

  private Map<?, ?> dataMap;

  @Before
  public void setUp() throws Exception {
    Map<String, List<Map<String, String>>> map = Maps.newHashMap();
    List<Map<String, String>> records = new ArrayList<>();
    map.put("records", records);
    Map<String, String> data = Maps.newHashMap();
    data.put("customerCode", "181");
    data.put("customerName", "aaaaaaaaa");
    data.put("deliveryAddress", "bbbbbbbb");
    records.add(data);
    Map<String, String> data1 = Maps.newHashMap();
    data1.put("customerCode", "182");
    data1.put("customerName", "cccccccccc");
    data1.put("deliveryAddress", "cccccccc");
    records.add(data1);
    Map<String, String> data2 = Maps.newHashMap();
    data2.put("customerCode", "183");
    data2.put("customerName", "dddddddd");
    data2.put("deliveryAddress", "ddddddd");
    records.add(data2);
    this.dataMap = map;
  }

  @Test
  public void collectionSelection() {
    SpelExpressionParser parser = new SpelExpressionParser();
    //根据字段值来过滤集合元素
    //[records]表示从根map中取records对应的value
    //?. 表示空安全的方式访问其下级属性
    //?[...]表示对集合元素进行过滤
    Object value = parser.parseExpression(
        "[records]?.?[[customerCode] == '183']"
    ).getValue(dataMap);
    System.out.println(JSON.toJSONString(value));
  }

  @Test
  public void collectionProjection() {
    SpelExpressionParser parser = new SpelExpressionParser();
    //提取字段形成一个新的集合
    Object value = parser.parseExpression(
        "[records]?.![[customerCode]]"
    ).getValue(dataMap);
    System.out.println(JSON.toJSONString(value));
  }

  @Test
  public void name() {
    SpelExpressionParser parser = new SpelExpressionParser();
    parser.parseExpression("#this").getValue(Lists.newArrayList("abc", "123"));
  }

  @Test
  public void mapSelection() {
    Map<String, Integer> map = Maps.newHashMap();
    map.put("a", 123);
    map.put("b", 20);
    map.put("c", 99);
    SpelExpressionParser parser = new SpelExpressionParser();
    Object value = parser.parseExpression("#this.?[value<100]").getValue(map);
    System.out.println(JSON.toJSONString(value));
  }

}
