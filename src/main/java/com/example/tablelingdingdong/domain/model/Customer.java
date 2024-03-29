package com.example.tablelingdingdong.domain.model;

import com.example.tablelingdingdong.domain.form.SignUpForm;
import com.example.tablelingdingdong.type.UserType;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Customer extends BaseEntity {
    @Id
    @Column(name="manager_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String phone;
    private String verificationCode;
    private LocalDateTime verifyExpiredAt;
    private boolean verify;
    private UserType userType;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;


    public boolean isVerify(){
        return verify;
    }
    public static Customer from(SignUpForm signUpForm){
        return Customer.builder()
                .email(signUpForm.getEmail())
                .password(signUpForm.getPassword())
                .name(signUpForm.getName())
                .phone(signUpForm.getPhone())
                .verificationCode(null)
                .verify(false)
                .userType(UserType.MANAGER)
                .build();

    }
    public void addReservation(Reservation reservation) {
        if (reservations == null) {
            reservations = new ArrayList<>();
        }
        reservations.add(reservation);
    }
}
