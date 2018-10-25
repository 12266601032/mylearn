package com.example.spring.integration.file;

import java.io.File;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.messaging.MessageChannel;

/**
 * Date:     2018年10月23日 20:32 <br/>
 *
 * @author lcc
 * @see
 * @since
 */
@SpringBootApplication
public class LoggingJavaApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context =
        new SpringApplicationBuilder(LoggingJavaApplication.class)
            .web(false)
            .run(args);
    MyGateway gateway = context.getBean(MyGateway.class);
    //通过gateway发送的数据会到logChannel中，并会被LoggingHandler处理
    gateway.sendToLogger("foo");
  }

  @Bean
  public MessageChannel logChannel() {
    return new DirectChannel();
  }

  @Bean
  //将业务处理类handler与通道绑定
  //如果不存在logChannel的话，将会被org.springframework.integration.config.annotation.AbstractMethodAnnotationPostProcessor.createEndpoint
  //catch掉并创建一个DirectChannel
  //因此可只关注输入和输出部分的channel的创建，中间处理过程用到的channel如果没有特殊需求
  //使用默认的DirectChannel即可
  @ServiceActivator(inputChannel = "logChannel")
  public LoggingHandler logging() {
    LoggingHandler adapter = new LoggingHandler(Level.ERROR);
    adapter.setLoggerName("TEST_LOGGER");
    adapter.setLogExpressionString("headers.id + ': ' + payload");
    return adapter;
  }

  //注解风格的配置，相当于mvc中的controller，负责通道业务处理与数据请求间的绑定
  @MessagingGateway(defaultRequestChannel = "logChannel")
  public interface MyGateway {

    void sendToLogger(String data);
  }


  @Bean
  public StandardIntegrationFlow integrationFlow() {
    //配置通道以及filter、router、transformer等等
    return IntegrationFlows
        //设置消息源通道
        .from(messageSources -> messageSources
                .file(new File(this.getClass().getClassLoader().getResource("").getPath())),
            endpointConfigurer -> endpointConfigurer.poller(Pollers.fixedDelay(1000)))
        //设置消息目标通道
        .channel("logChannel")
        //DslIntegrationConfigurationInitializer会针对IntegrationFlow类型的对象作解析并创建相应的通道等等
        .get();
  }
}
