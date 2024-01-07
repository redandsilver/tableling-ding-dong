package com.example.tablelingdingdong.domain.model;

import com.example.tablelingdingdong.domain.form.ReservationForm;
import com.example.tablelingdingdong.domain.form.SignUpForm;
import com.example.tablelingdingdong.type.UserType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @Column(name="reservation_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reservatonName;
    private LocalDateTime reservationTime;
    private boolean confirmed;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;


    public boolean isConfirmed(){
        return confirmed;
    }
    public static Reservation from(ReservationForm reservationForm){
        return Reservation.builder()
                .reservatonName(reservationForm.getReservationName())
                .reservationTime(reservationForm.getReservationTime())
                .confirmed(false)
                .build();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.addReservation(this);
    }

    public void setStore(Store store) {
        this.store = store;
        store.addReservation(this);
    }
}
