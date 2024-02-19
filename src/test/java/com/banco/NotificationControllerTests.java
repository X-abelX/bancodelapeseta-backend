package com.banco;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.banco.dtos.AuthenticationRequestDto;
import com.banco.entities.Entity;
import com.banco.repositories.EntityRepository;
import com.banco.security.JwtService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import software.amazon.awssdk.core.SdkResponse;
import software.amazon.awssdk.http.SdkHttpResponse;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NotificationControllerTests {

    @MockBean
    private EntityRepository entityRepository;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AmazonSimpleEmailService amazonSimpleEmailService;
    @MockBean
    private SnsClient snsClient;
    @Autowired
    private JwtService jwtService;




    @WithMockUser
    @Test
    @Transactional
    public void testSimpleEmailServiceOk() throws Exception {
        when(amazonSimpleEmailService.sendEmail(any())).thenReturn(null);
        when(entityRepository.findByTaxId(any())).thenReturn(Optional.of(Entity.builder().emailConfirmed(false).phoneConfirmed(false).build()));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/send/email/verification/code").header("Authorization", jwtService.generateToken(new Entity())))
                .andExpect(status().isOk());
    }
    @WithMockUser
    @Test
    @Transactional
    public void testSMSServiceOk() throws Exception {
        SdkHttpResponse response = SdkHttpResponse.builder().statusCode(HttpStatus.OK.value()).build();
        when(snsClient.publish((PublishRequest) any())).thenReturn((PublishResponse) PublishResponse.builder().sdkHttpResponse(response).build());
        when(entityRepository.findByTaxId(any())).thenReturn(Optional.of(Entity.builder().emailConfirmed(false).phoneConfirmed(false).build()));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/send/phone/verification/code").header("Authorization", jwtService.generateToken(new Entity())))
                .andExpect(status().isOk());
    }

}
