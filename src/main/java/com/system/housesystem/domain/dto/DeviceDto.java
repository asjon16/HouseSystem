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

    private Boolean status;

    private Integer activeTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime timer;

    @Min(value = 0, message = "Minimum value for status is 0")
    @Max(value = 100, message = "Maximum value for status is 100")
    @Column(name = "status_percentage")
    // sets up the % you want the device to be in capacity of. Example volume/brightness/heat.
    private Integer statusPercentage;

}
