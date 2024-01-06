package com.example.tablelingdingdong.domain.model;

import com.example.tablelingdingdong.domain.SignUpForm;
import com.example.tablelingdingdong.domain.StoreForm;
import com.example.tablelingdingdong.type.UserType;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @Column(name="store_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String storeName;
    private String storeNumber;
    private String location;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public static Store from(StoreForm storeForm){
        return Store.builder()
                .storeName(storeForm.getName())
                .storeNumber(storeForm.getNumber())
                .location(storeForm.getLocation())
                .description(storeForm.getDescription())
                .build();
    }

    public void update(Manager manager) {
        this.manager = manager;
    }
}
