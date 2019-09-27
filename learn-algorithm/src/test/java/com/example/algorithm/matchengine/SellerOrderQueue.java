package com.example.algorithm.matchengine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Date:     2019年07月28日 10:23 <br/>
 *
 * 卖方订单队列
 */
public class SellerOrderQueue {

  private PriorityBlockingQueue<TradeMessage> sellerMessageQueue = new PriorityBlockingQueue<>(200,
      new Comparator<TradeMessage>() {
        @Override
        public int compare(TradeMessage o1, TradeMessage o2) {
          //价格优先，价格越低越靠前
          int r1 = o1.getPrice().compareTo(o2.getPrice());
          if (r1 != 0) {
            return r1;
          }
          //时间优先，时间越早越靠前
          return Long.compare(o1.getOrderTimestamp(), o2.getOrderTimestamp());
        }
      });


  public List<TradeOrder> match(BigDecimal buyerQuantity, BigDecimal buyerPrice) {

    //剩余数量
    BigDecimal remainQuantity = buyerQuantity;
    Iterator<TradeMessage> iterator = sellerMessageQueue.iterator();
    List<TradeOrder> orderList = new ArrayList<>();
    while (iterator.hasNext()) {
      TradeMessage sellerMessage = iterator.next();
      //判断买入价<卖出价则表示没有符合的了
      if (buyerPrice.compareTo(sellerMessage.getPrice()) < 0) {
        break;
      }
      //剩余买入数量<=卖出数量
      if (remainQuantity.compareTo(sellerMessage.getQuantity()) <= 0) {
        TradeOrder order = new TradeOrder();
        break;
      } else {
        //已买完，从队列移除
        sellerMessageQueue.remove(sellerMessage);

      }
    }
    return null;
  }

}
