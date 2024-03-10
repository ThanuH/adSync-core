package com.project.adsync.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_advertisement")
public class UserAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "advertisement_id") // Foreign key reference
    private Advertisement advertisement;

    @Column(nullable = false)
    private int priority;

    @Column(name = "targeted_age", length = 50)
    private String targetedAge;

    @Column(name = "targeted_audience", length = 255)
    private String targetedAudience;

    @ManyToOne
    @JoinColumn(name = "business_category_id")
    private BusinessCategory businessCategory;

    @Column(name = "status")
    private String status;

    @Column(name = "unique_identifier")
    private String uniqueIdentifier;

    // Constructors, getters, setters
}

