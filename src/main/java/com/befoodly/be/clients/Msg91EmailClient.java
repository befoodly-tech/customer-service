package com.befoodly.be.clients;

import com.befoodly.be.model.request.EmailBodyRequest;
import com.befoodly.be.utils.JacksonUtils;
import com.squareup.okhttp.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class Msg91EmailClient {

    public void sendOTPMail (String otp, String email) {
        try {
            OkHttpClient client = new OkHttpClient();

            EmailBodyRequest bodyRequest = EmailBodyRequest.builder()
                    .recipients(List.of(EmailBodyRequest.EmailTo.builder()
                            .to(List.of(EmailBodyRequest.EmailData.builder()
                                    .email(email)
                                    .build()))
                            .variables(EmailBodyRequest.EmailVariable.builder()
                                    .company_name("BeFoodly")
                                    .otp(otp).build())
                            .build()))
                    .from(EmailBodyRequest.EmailData.builder()
                            .name("BeFoodly")
                            .email("no-reply@mail.befoodly.com")
                            .build())
                    .domain("mail.befoodly.com")
                    .template_id("global_otp")
                    .build();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, JacksonUtils.objectToString(bodyRequest));
            Request request = new Request.Builder()
                    .url("https://control.msg91.com/api/v5/email/send")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("authkey", "402792AO40VlVXFx264ca629cP1")
                    .build();

            Response response = client.newCall(request).execute();

            log.info("Successfully! sent the otp mail to email: {}", email);
        } catch (Exception e) {
            log.error("Failed to send the otp mail to mail id: {} with error: {}",
                    email, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
