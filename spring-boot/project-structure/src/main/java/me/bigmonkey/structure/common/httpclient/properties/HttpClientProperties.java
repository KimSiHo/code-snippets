/*
 * Copyright 2021 Etoos Education. All rights Reserved.
 * Etoos PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.bigmonkey.structure.common.httpclient.properties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "http-client")
public class HttpClientProperties {

    @DurationUnit(ChronoUnit.MILLIS)
    private final Duration connectTimeout;

    @DurationUnit(ChronoUnit.MILLIS)
    private final Duration socketTimeout;

    @DurationUnit(ChronoUnit.MILLIS)
    private final Duration connectRequestTimeout;

    @DurationUnit(ChronoUnit.MILLIS)
    private final Duration responseTimeout;

    @DurationUnit(ChronoUnit.MILLIS)
    private final Duration readTimeout;

    @DurationUnit(ChronoUnit.MILLIS)
    private final Duration writeTimeout;

    @DataSizeUnit(DataUnit.BYTES)
    private final DataSize maxMemoryBufferSize;

    private final int maxConnect;

    private final int maxConnectPerRoute;
}
