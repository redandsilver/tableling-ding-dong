package com.example.tablelingdingdong.service;

import com.example.tablelingdingdong.client.MailgunClient;
import com.example.tablelingdingdong.client.mailgun.SendMailForm;
import com.example.tablelingdingdong.domain.form.ReservationForm;
import com.example.tablelingdingdong.domain.form.SignUpForm;
import com.example.tablelingdingdong.domain.model.Customer;
import com.example.tablelingdingdong.domain.model.Manager;
import com.example.tablelingdingdong.domain.model.Reservation;
import com.example.tablelingdingdong.domain.model.Store;
import com.example.tablelingdingdong.domain.repository.CustomerRepository;
import com.example.tablelingdingdong.domain.repository.ManagerRepository;
import com.example.tablelingdingdong.domain.repository.ReservationRepository;
import com.example.tablelingdingdong.domain.repository.StoreRepository;
import com.example.tablelingdingdong.exception.CustomException;
import com.example.tablelingdingdong.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
@Service
@RequiredArgsConstructor
public class ReservationServcie {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;
    private final MailgunClient mailgunClient;
    @Transactional
    public Long makeReservation(Customer customer, ReservationForm form, Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_EXIST_STORE)
        );

        Reservation reservation = Reservation.from(form);

        customer.addReservation(reservation);
        store.addReservation(reservation);
        reservation.setCustomer(customer);
        reservation.setStore(store);
        reservationRepository.save(reservation);
        Manager manager = storeRepository.findById(storeId).get().getManager();
        sendMail(manager.getEmail(),reservation.getId()+"의 예약을 확인해주세요!");
        return reservation.getId();
    }

    @Transactional
    public String confirmReservation(Manager manager, Long id) {

        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_EXIST_STORE)
        );
        Customer customer = customerRepository.findById(reservation.getCustomer().getId()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_EXIST_USER)
        );
        reservation.setConfirmed(true);
        sendMail(customer.getEmail(),id+"의 예약이 확인 되었습니다. 예약시간 10분전까지 도착해주세요!");
        return "링딩동~🎶";
    }
    private void sendMail(String email, String text){
        SendMailForm sendMailForm = SendMailForm.builder()
                .from("tester@lingdingdong.com")
                .to(email)
                .subject("Confirm store's reservation!")
                .text(text)
                .build();
        mailgunClient.sendEmail(sendMailForm);
    }

}
