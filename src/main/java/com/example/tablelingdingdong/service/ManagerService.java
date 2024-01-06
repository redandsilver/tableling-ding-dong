package com.example.tablelingdingdong.service;

import com.example.tablelingdingdong.domain.Dto.UserVo;
import com.example.tablelingdingdong.domain.SignUpForm;
import com.example.tablelingdingdong.domain.StoreForm;
import com.example.tablelingdingdong.domain.model.Manager;
import com.example.tablelingdingdong.domain.model.Store;
import com.example.tablelingdingdong.domain.repository.ManagerRepository;
import com.example.tablelingdingdong.domain.repository.StoreRepository;
import com.example.tablelingdingdong.exception.CustomException;
import com.example.tablelingdingdong.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final PasswordEncoder passwordEncoder;
    private final ManagerRepository managerRepository;
    private final StoreRepository storeRepository;
    public void isExistEmail(String email) {
        if(managerRepository.findByEmail(email).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
        }
    }

    public Manager signUp(SignUpForm signUpForm) {
        signUpForm.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        return managerRepository.save(Manager.from(signUpForm));
    }
@Transactional
    public void changeValidateEmail(Long id, String code) {
        Optional<Manager> managerOptional= managerRepository.findById(id);

        if(managerOptional.isEmpty()){
            throw new CustomException(ErrorCode.NOT_EXIST_USER);
        }else{
            Manager manager =  managerOptional.get();
            manager.setVerificationCode(code);
            manager.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
        }
    }

    @Transactional
    public void verifyEmail(String email, String code){
        Manager manager = managerRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));

        if(manager.isVerify()){
            throw new CustomException(ErrorCode.ALREADY_VERIFIED);
        }
        else if(!manager.getVerificationCode().equals(code)){
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        }
        else if(manager.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        manager.setVerify(true);
    }

    public Manager findValidManager(String email, String password) {

        Manager manager = managerRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.NOT_EXIST_USER));
        if(!passwordEncoder.matches(password,manager.getPassword())){
            throw new CustomException(ErrorCode.LOGIN_CHECK_FAIL);
        }
        return manager;
    }

    public Optional<Manager> findByIdAndEmail(Long id, String email) {
        return managerRepository.findByIdAndEmail(id,email);
    }
    @Transactional
    public void joinPartnership(UserVo vo) {
        Manager manager = this.findByIdAndEmail(vo.getId(),vo.getEmail()).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_EXIST_USER)
        );
        manager.setPartnership(true);
    }
@Transactional
    public void registerStore(UserVo vo, StoreForm storeForm) {
        Manager manager = this.findByIdAndEmail(vo.getId(),vo.getEmail())
                .orElseThrow(
                ()->new CustomException(ErrorCode.NOT_EXIST_USER));
        if(!manager.isPartnership()){
            throw new CustomException(ErrorCode.PARTNERSHIP_CHECK_FAIL);
        }
        Store store = Store.from(storeForm);
        storeRepository.save(store);
        store.update(manager);
        manager.addStore(store);
    }

    public List<Store> getAllStores(UserVo vo) {
        Manager manager = this.findByIdAndEmail(vo.getId(),vo.getEmail())
                .orElseThrow(
                        ()->new CustomException(ErrorCode.NOT_EXIST_USER));
        return manager.getStores();
    }
}
