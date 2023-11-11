package com.system.housesystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.housesystem.entity.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Duration;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {

    private Integer id;

    @NotEmpty
    @Size(max = 30,message = "Maximum size of name is 30")
    private String name;

    private String status;

    private Integer activeTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime timer;

    @Column(name = "status_percentage")
    private Integer statusPercentage;

}
