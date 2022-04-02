package com.example.appspringhomework251.entity;

import com.example.appspringhomework251.entity.enums.StatusName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Timestamp deadline;

    @Enumerated(value = EnumType.STRING)
    private StatusName statusName;
    
    @CreationTimestamp
    private Timestamp createdAt;
    
    @UpdateTimestamp
    private Timestamp updatedAt;
    
    @CreatedBy
    private UUID createdBy;
    
    @LastModifiedBy
    private UUID updatedBy;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private User toUser;

    private boolean active=true;
}
