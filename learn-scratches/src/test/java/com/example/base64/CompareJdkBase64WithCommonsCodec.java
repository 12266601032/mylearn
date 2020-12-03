package com.example.base64;

import java.util.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.junit.Test;

public class CompareJdkBase64WithCommonsCodec {

  @Test
  public void compare() {
    String s = "JAVA在使用BASE64编码的时候,是如何处理中文的? - 知乎\n"
        + "2017年12月26日 - Java 编程 JAVA在使用BASE64编码的时候,是如何处理中文的? 关注者1 被浏览57 关注问题\u200B写回答 \u200B邀请回答 \u200B好问题 \u200B添加评论 \u200B分享 \u200B暂时...\n"
        + "知乎 - 百度快照\n"
        + "java base64编码和解码 - 简单的幸福 - ITeye博客\n"
        + "2019年3月15日 - java 中使用base64编码和解码: 第一种方式: 通过反射使用java 中不对外公开的类: /*** * encode by Base64 */ public static String encodeBase64(byt...\n"
        + "hw1287789687.iteye.com/blog/19... - 百度快照\n"
        + "关于用Base64解决中文乱码的问题-CSDN论坛\n"
        + "9条回复 - 发帖时间: 2012年8月7日\n"
        + "2012年8月7日 - filename=<文件名的Base64编码> 点击链接通过后台的Action类处理,将<文件名的Base64编码>进行解码,用System.out.println输出无乱码\n"
        + "CSDN技术社区 - 百度快照\n"
        + "java base64编码 加密和解密(切记注意乱码问题)\n"
        + "  java base64编码.解码 CreationTime--2018年7月24日10点38分 Author:Marydon 1.方式一:DatatypeConverter 说明:使用jdk自带的 ... Java实现MD5加密及解密...\n"
        + "www.bbsmax.com/A/Gkz17g1N... - 百度快照";
    String base64 = StringUtils.newStringUtf8(Base64.getEncoder()
        .encode(s.getBytes()));
    String commonsBase64 = org.apache.commons.codec.binary.Base64
        .encodeBase64String(s.getBytes());
    System.out.println(base64);
    System.out.println(commonsBase64);
  }
}
