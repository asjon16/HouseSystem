package com.system.housesystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.Duration;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pajisjet extends BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pajisje_id")
    private Integer id;
    @Column(name = "pajisje_name")
    private String name;
    @Column(name = "pajisje_status")
    private Boolean status;
    @Column(name = "active_time")
    private Duration activeTime;
    @Min(value = 0, message = "Minimum value for status is 0")
    @Max(value = 100, message = "Maximum value for status is 100")
    @Column(name = "status_percentage")
    private Integer statusPercentage; // set ups the % you want the device to be in capacity of. Example volume/brightness/heat.




}
