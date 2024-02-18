package com.project.adsync.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "contact_number", nullable = false, length = 20)
    private String contactNumber;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "business_registration_number", unique = true, length = 100)
    private String businessRegistrationNumber;

    @Column(name = "business_name", length = 255)
    private String businessName;

    @ManyToOne
    @JoinColumn(name = "business_category_id")
    private BusinessCategory businessCategory;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String status;


}
