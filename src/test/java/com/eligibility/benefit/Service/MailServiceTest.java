package com.eligibility.benefit.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailServices mailServices;

    @Test
    void whenSendMailCalledAndNoErrorShouldSendMail() throws Exception {
        Mockito.doNothing().when(mailSender).send(Mockito.any(SimpleMailMessage.class));

        mailServices.sendMail(jsonNode());
    }

    private JsonNode jsonNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("to", "mail@mail.com");
        node.put("subject", "subject");
        node.put("message", "message");
        return node;
    }
}
