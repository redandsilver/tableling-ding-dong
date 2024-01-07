package com.example.tablelingdingdong.service;

import com.example.tablelingdingdong.domain.Dto.UserVo;
import com.example.tablelingdingdong.domain.form.ReservationForm;
import com.example.tablelingdingdong.domain.form.SignUpForm;
import com.example.tablelingdingdong.domain.model.Customer;
import com.example.tablelingdingdong.domain.model.Reservation;
import com.example.tablelingdingdong.domain.repository.CustomerRepository;
import com.example.tablelingdingdong.domain.repository.ReservationRepository;
import com.example.tablelingdingdong.exception.CustomException;
import com.example.tablelingdingdong.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    public void isExistEmail(String email) {
        if(customerRepository.findByEmail(email).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
        }
    }

    public Customer signUp(SignUpForm signUpForm) {
        signUpForm.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        return customerRepository.save(Customer.from(signUpForm));
    }
@Transactional
    public void changeValidateEmail(Long id, String code) {
        Optional<Customer> customerOptional= customerRepository.findById(id);

        if(customerOptional.isEmpty()){
            throw new CustomException(ErrorCode.NOT_EXIST_USER);
        }else{
            Customer customer =  customerOptional.get();
            customer.setVerificationCode(code);
            customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
        }
    }

    @Transactional
    public void verifyEmail(String email, String code){
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));

        if(customer.isVerify()){
            throw new CustomException(ErrorCode.ALREADY_VERIFIED);
        }
        else if(!customer.getVerificationCode().equals(code)){
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        }
        else if(customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        customer.setVerify(true);
    }

    public Customer findValidManager(String email, String password) {

        Customer customer = customerRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.NOT_EXIST_USER));
        if(!passwordEncoder.matches(password,customer.getPassword())){
            throw new CustomException(ErrorCode.LOGIN_CHECK_FAIL);
        }
        return customer;
    }

    public Optional<Customer> findByIdAndEmail(Long id, String email) {
        return customerRepository.findByIdAndEmail(id,email);
    }

}
