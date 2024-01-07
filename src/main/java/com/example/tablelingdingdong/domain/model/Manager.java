package com.example.tablelingdingdong.domain.model;

import com.example.tablelingdingdong.domain.form.SignUpForm;
import com.example.tablelingdingdong.type.UserType;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Manager extends BaseEntity {
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
    private boolean isPartnership;

    private UserType userType;
    // 가게를 외래키로 설정
    @OneToMany(mappedBy = "manager",cascade = CascadeType.ALL)
    private List<Store> stores;
    public boolean isVerify(){
        return verify;
    }
    public static Manager from(SignUpForm signUpForm){
        return Manager.builder()
                .email(signUpForm.getEmail())
                .password(signUpForm.getPassword())
                .name(signUpForm.getName())
                .phone(signUpForm.getPhone())
                .verificationCode(null)
                .verify(false)
                .isPartnership(false)
                .userType(UserType.MANAGER)
                .build();

    }

    public void addStore(Store store){
        this.stores.add(store);
        store.update(this);
    }
}
