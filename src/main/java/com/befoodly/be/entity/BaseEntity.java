package com.befoodly.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    @JsonIgnore
    private Long id;

    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

    @Version
    @JsonIgnore
    private Integer version;
}
