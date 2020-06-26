package com.balmy.dropthemoney.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ReceiveInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "money", nullable = false)
    private long money;

    @OneToOne
    @JoinColumn(name = "receive_user_id")
    private User receiveUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private DropMoney dropMoney;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;
}
