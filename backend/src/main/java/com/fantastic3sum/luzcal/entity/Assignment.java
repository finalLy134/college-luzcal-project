
package com.fantastic3sum.luzcal.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.fantastic3sum.luzcal.enums.ConfirmationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "assignments",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"schedule_item_id", "group_member_id"}
    )
)
@Getter
@Setter
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_item_id", nullable = false)
    private ScheduleItem scheduleItem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_member_id", nullable = false)
    private GroupMember groupMember;

    @Column(nullable = false)
    private boolean isResponsible = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConfirmationStatus confirmationStatus = ConfirmationStatus.PENDING;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant assignedAt;

    private Instant confirmedAt;
}
