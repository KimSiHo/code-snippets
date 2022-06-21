package me.bigmonkey.structure.common.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import com.etoos.member.common.enums.ServiceCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "member")
public class MemberProperties {

    private final List<ServiceCode> requiredPerson;
    private final int authCodeLength;
    private final int possessionTimeoutMinute;
    private final int retrySessionkeyGeneration;
    private final int withdrawValidIdDays;
    private final int phoneValidIdDays;
    private final int withdrawPeriod;
}
