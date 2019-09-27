package com.example.algorithm.matchengine;

import java.math.BigDecimal;

/**
 * Date:     2019年07月28日 10:24 <br/>
 *
 * 交易报文
 */
public class TradeMessage {

  //交易类型
  private TradeType tradeType;
  //交易数量
  private BigDecimal quantity;
  //交易单价
  private BigDecimal price;
  //订单时间戳
  private long orderTimestamp;
  //账户
  private String accountNo;

  public TradeType getTradeType() {
    return tradeType;
  }

  public void setTradeType(TradeType tradeType) {
    this.tradeType = tradeType;
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

  public long getOrderTimestamp() {
    return orderTimestamp;
  }

  public void setOrderTimestamp(long orderTimestamp) {
    this.orderTimestamp = orderTimestamp;
  }

  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }

  enum TradeType {

    //卖出
    selling,
    //买入
    buying

  }
}
