
package com.fantastic3sum.luzcal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "group_settings")
@Getter
@Setter
public class GroupSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "group_id",
        nullable = false,
        unique = true
    )
    private Group group;

    @Column(nullable = false)
    private boolean allowEditorsToInvite = false;

    // null = automatic confirmation disabled
    @Column
    private Integer autoConfirmAfterMinutes;

    @Column(nullable = false)
    private int defaultReminderMinutesBefore = 30;
}
