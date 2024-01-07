package com.example.tablelingdingdong.application;

import com.example.tablelingdingdong.client.MailgunClient;

import com.example.tablelingdingdong.client.mailgun.SendMailForm;
import com.example.tablelingdingdong.domain.SignUpForm;
import com.example.tablelingdingdong.domain.model.Customer;
import com.example.tablelingdingdong.domain.model.Manager;
import com.example.tablelingdingdong.service.CustomerService;
import com.example.tablelingdingdong.service.ManagerService;
import com.example.tablelingdingdong.type.UserType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final ManagerService managerService;
    private final CustomerService customerService;
    @Transactional
    public String signUpManager(SignUpForm signUpForm) {
        managerService.isExistEmail(signUpForm.getEmail());
        Manager manager = managerService.signUp(signUpForm);
        String code = getRandomCode();
        managerService.changeValidateEmail(manager.getId(),code);
        sendMail(signUpForm,UserType.MANAGER,code);
        return manager.getEmail();
    }
    @Transactional
    public String signUpCustomer(SignUpForm signUpForm) {
        customerService.isExistEmail(signUpForm.getEmail());
        Customer customer = customerService.signUp(signUpForm);
        String code = getRandomCode();
        customerService.changeValidateEmail(customer.getId(),code);
        sendMail(signUpForm,UserType.CUSTOMER,code);
        return customer.getEmail();
    }
    private void sendMail(SignUpForm form, UserType userType, String code){
        String usertype = userType.toString().toLowerCase(Locale.ROOT);
        LocalDateTime now = LocalDateTime.now();
        SendMailForm sendMailForm = SendMailForm.builder()
                .from("tester@lingdingdong.com")
                .to(form.getEmail())
                .subject("Verification Email")
                .text(getVerificationEmailBody(
                        form.getEmail(),form.getName(),usertype,code))
                .build();
        mailgunClient.sendEmail(sendMailForm);
    }

    private String getRandomCode(){
        return RandomStringUtils.random(10,true,true);
    }
    private String getVerificationEmailBody(String email, String name, String type, String code){
        StringBuilder sb = new StringBuilder();
        return sb.append("Hello ").append(name)
                .append("Please Click Link for verification\n\n")
                .append("https://localhost:7070/signup/")
                .append(type)
                .append("/verify?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }


    public void verifyManager(String email, String code) {
        managerService.verifyEmail(email,code);

    }
    public void verifyCustomer(String email, String code) {
        customerService.verifyEmail(email,code);

    }
}
