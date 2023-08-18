package com.befoodly.be.clients;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class AwsSnsClient {

    public static final String AWS_ACCESS_KEY_ID = "aws.accessKeyId";
    public static final String AWS_SECRET_KEY = "aws.secretKey";
    static {
        System.setProperty(AWS_ACCESS_KEY_ID, "AKIATETRKW2HQFXUWJHA");
        System.setProperty(AWS_SECRET_KEY, "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");
    }

    public void sendOTPMessage (String message, String phoneNumber) {
        try {
            AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Regions.AP_SOUTH_1).build();
            Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();

            smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                    .withStringValue("BeFoodly")
                    .withDataType("String"));
            smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                    .withStringValue("Transactional")
                    .withDataType("String"));

            PublishResult result = snsClient.publish(new PublishRequest()
                    .withMessageAttributes(smsAttributes)
                    .withMessage(message)
                    .withPhoneNumber(phoneNumber));

            log.info("Successfully sent OTP message on phone: {}, with message id: {}", phoneNumber, result.getMessageId());
        } catch (AmazonSNSException e) {
            log.error("Failed to send otp for phone number: {} with error: {}", phoneNumber, e.getMessage());
            throw e;
        }
    }

}
