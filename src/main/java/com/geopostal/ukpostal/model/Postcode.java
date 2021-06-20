package com.geopostal.ukpostal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Postcode {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column(updatable = false)
    Long migrationId;

    @Column(length = 10)
    String postcode;

    @Column
    Double latitude;

    @Column
    Double longitude;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp createdDate;

    @UpdateTimestamp
    @Column
    Timestamp modifiedDate;
}
