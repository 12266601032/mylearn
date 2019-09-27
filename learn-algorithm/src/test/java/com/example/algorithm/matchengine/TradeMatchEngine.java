package com.example.algorithm.matchengine;

import java.util.List;

/**
 * Date:     2019年07月28日 10:22 <br/>
 *
 * 交易撮合引擎
 *
 * @author lcc
 * @since 1.0.0
 */
public class TradeMatchEngine {

  private SellerOrderQueue sellerOrderQueue;

  private BuyerOrderQueue buyerOrderQueue;


  public List<TradeOrder> consumeTradeMessage(TradeMessage message) {
    switch (message.getTradeType()) {
      case buying:

      case selling:
    }
    return null;
  }

}
