package com.system.housesystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.Duration;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device extends BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer id;
    @Column(name = "device_name")
    private String name;
    @Column(name = "device_status")
    private Status status;
    @Column(name = "active_time")
    //Minutes active.
    private Integer activeTime;
    @Column(name = "timer")
    private LocalTime timer;
    // sets up the % you want the device to be in capacity of. Example volume/brightness/heat.
    private Integer statusPercentage;






}
