package com.example.spring.integration.aggregate;

import com.example.spring.integration.file.LoggingJavaApplication;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.Payloads;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.RendezvousChannel;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.endpoint.IntegrationConsumer;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.router.MessageRouter;
import org.springframework.integration.router.PayloadTypeRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Date:     2018年10月24日 13:42 <br/>
 *
 * @author lcc
 * @see
 * @since
 */
@SpringBootApplication
public class AggregateApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context =
        new SpringApplicationBuilder(AggregateApplication.class)
            .web(false)
            .run(args);

    MessageChannel channel = context
        .getBean("inputMessageChannel", MessageChannel.class);
    channel.send(MessageBuilder
        .withPayload(Lists.newArrayList("123"))
        .build());
    Map<String, String> map = Maps.newHashMap();
    map.put("123", "abc");
    channel.send(MessageBuilder
        .withPayload(map)
        .build());

  }

  @Bean
  public SubscribableChannel inputMessageChannel() {
    //发布订阅模式
    return new PublishSubscribeChannel();
  }

  //利用注解方式声明router，表示从inputMessageChannel获取Message，然后通过返回值决定路由到哪个channel
  @Router(inputChannel = "inputMessageChannel")
  public String messageRouter(Message<?> payload) {
    if (payload.getPayload() instanceof List) {
      return "listMessageChannel";
    } else if (payload.getPayload() instanceof Map) {
      return "mapMessageChannel";
    }
    //read defaultOutputChannel
    return null;
  }

  @Bean
  //EventDrivenConsumer在start时将payloadTypeRouter加入到inputMessageChannel的订阅列表
  public EventDrivenConsumer eventDrivenConsumer(SubscribableChannel inputMessageChannel,
      PayloadTypeRouter payloadTypeRouter) {
    return new EventDrivenConsumer(inputMessageChannel, payloadTypeRouter);
  }

  @Bean
  public PayloadTypeRouter payloadTypeRouter() {
    PayloadTypeRouter router = new PayloadTypeRouter();
    Map<String, String> channelMappings = Maps.newHashMap();
    channelMappings.put("java.util.List", "listMessageChannel");
    channelMappings.put("java.util.Map", "mapMessageChannel");
    router.setChannelMappings(channelMappings);
    return router;
  }


  @Bean
  @ServiceActivator(inputChannel = "listMessageChannel")
  public MessageHandler listMessageHandler() {
    LoggingHandler loggingHandler = new LoggingHandler(Level.ERROR);
    loggingHandler.setLogExpressionString("payload");
    loggingHandler.setLoggerName("LIST_LOGGER");
    return loggingHandler;
  }

  @Bean
  @ServiceActivator(inputChannel = "mapMessageChannel")
  public MessageHandler mapMessageHandler() {
    LoggingHandler loggingHandler = new LoggingHandler(Level.ERROR);
    loggingHandler.setLogExpressionString("payload");
    loggingHandler.setLoggerName("MAP_LOGGER");
    return loggingHandler;
  }

  @Bean
  public MessageChannel listMessageChannel() {
    return new DirectChannel();
  }

  @Bean
  public MessageChannel mapMessageChannel() {
    return new DirectChannel();
  }


}
