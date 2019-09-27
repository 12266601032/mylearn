package com.example.algorithm.matchengine;

import java.math.BigDecimal;

/**
 * Date:     2019年07月28日 10:30 <br/>
 *
 * 交易订单（匹配结果）
 *
 * @author lcc
 * @since 1.0.0
 */
public class TradeOrder {

  //卖方账户
  private String sellerAccountNo;
  //买房账户
  private String buyerAccountNo;
  //交易数量
  private BigDecimal quantity;
  //交易单价
  private BigDecimal price;


  public String getSellerAccountNo() {
    return sellerAccountNo;
  }

  public void setSellerAccountNo(String sellerAccountNo) {
    this.sellerAccountNo = sellerAccountNo;
  }

  public String getBuyerAccountNo() {
    return buyerAccountNo;
  }

  public void setBuyerAccountNo(String buyerAccountNo) {
    this.buyerAccountNo = buyerAccountNo;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
