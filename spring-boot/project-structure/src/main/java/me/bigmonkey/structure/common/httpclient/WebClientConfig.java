/*
 * Copyright 2021 Etoos Education. All rights Reserved.
 * Etoos PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.bigmonkey.structure.common.httpclient;

import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebClientConfig {
    // @Bean
    // public WebClient webClient(HttpClientProperties properties) {
    //     final WebClient webClient = WebClient.builder()
    //             .clientConnector(new ReactorClientHttpConnector(configuredHttpClient(properties)))
    //             .exchangeStrategies(configuredExchangeStrategies(properties))
    //             .defaultHeader("from", "member-service")
    //             .defaultCookie("httpclient", "web-client")
    //             // .filters(filters -> filters.add(0, Class))
    //             // .filter((request, next) -> {
    //             //     final ClientRequest clientRequest = ClientRequest.from(request)
    //             //             .header("from", "member-service")
    //             //             .build();
    //             //     return next.exchange(clientRequest);
    //             // })
    //             // .filter(ExchangeFilterFunction.ofRequestProcessor(
    //             //         clientRequest -> {
    //             //             log.debug("REQUEST: {} {}", clientRequest.method(), clientRequest.url());
    //             //             clientRequest.headers().forEach(
    //             //                     (name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
    //             //             return Mono.just(clientRequest);
    //             //         }))
    //             // .filter(ExchangeFilterFunction.ofResponseProcessor(
    //             //         clientResponse -> {
    //             //             log.debug("RESPONSE STATUS: {}", clientResponse.statusCode());
    //             //             if (clientResponse.statusCode() != null
    //             //                     && (clientResponse.statusCode().is4xxClientError()
    //             //                     || clientResponse.statusCode().is5xxServerError())) {
    //             //                 clientResponse.bodyToMono(String.class)
    //             //                         .flatMap(body -> {
    //             //                             log.debug("RESPONSE BODY: {}", body);
    //             //                             return Mono.just(clientResponse);
    //             //                         });
    //             //             }
    //             //             clientResponse.headers().asHttpHeaders().forEach(
    //             //                     (name, values) -> values.forEach(value -> log.info("{} : {}", name, value)));
    //             //             return Mono.just(clientResponse);
    //             //         }))
    //             .build();
    //     log.info("WebClient initialized");
    //
    //     return webClient;
    // }

    // private HttpClient configuredHttpClient(HttpClientProperties properties) {
    //     // Connected Time Options
    //     final HttpClient httpClient = HttpClient.create()
    //             .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int)properties.getConnectTimeout().toMillis())
    //             .responseTimeout(properties.getResponseTimeout())
    //             .doOnConnected(connection -> connection
    //                     .addHandlerLast(new ReadTimeoutHandler((int)properties.getReadTimeout().getSeconds()))
    //                     .addHandlerLast(new WriteTimeoutHandler((int)properties.getWriteTimeout().getSeconds())));
    //     httpClient.warmup().block();
    //
    //     return httpClient;
    // }
    //
    // private ExchangeStrategies configuredExchangeStrategies(HttpClientProperties properties) {
    //     // Memory Buffer Size(Default 256KB)
    //     final ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
    //             .codecs(configurer -> configurer
    //                     .defaultCodecs().maxInMemorySize((int)properties.getMaxMemoryBufferSize().toBytes()))
    //             .build();
    //     // Enable Header or Form Data Logging(TRACE)
    //     exchangeStrategies.messageWriters().stream()
    //             .filter(LoggingCodecSupport.class::isInstance)
    //             .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));
    //
    //     return exchangeStrategies;
    // }
}
