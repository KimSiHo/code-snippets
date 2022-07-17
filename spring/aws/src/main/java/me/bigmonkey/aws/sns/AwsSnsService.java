package me.bigmonkey.aws.sns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsSnsService {

    private final AmazonSNSClient amazonSNSClient;

    private final ObjectMapper objectMapper;

    @Value("${cloud.aws.sns.test-topic-arn}")
    private String topicArn;

    public String publish(SnsPublishRequestDto requestDto, List<String> serviceList) {
        String message, strServiceList = "";
        try {
            message = objectMapper.writeValueAsString(requestDto);
            strServiceList = objectMapper.writeValueAsString(serviceList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info(message);
        log.info(strServiceList);

        PublishRequest publishRequest = new PublishRequest();
        publishRequest.setMessage(message);
        publishRequest.setTopicArn(topicArn);

        Map<String, MessageAttributeValue> snsAttrs = new HashMap<String, MessageAttributeValue>();
        snsAttrs.put("function", new MessageAttributeValue().withDataType("String")
            .withStringValue(requestDto.getFunction()));
        snsAttrs.put("service", new MessageAttributeValue().withDataType("String.Array")
            .withStringValue(strServiceList));
        publishRequest.setMessageAttributes(snsAttrs);

        PublishResult publishResult = amazonSNSClient.publish(publishRequest);
        return publishResult.getMessageId();
    }
}