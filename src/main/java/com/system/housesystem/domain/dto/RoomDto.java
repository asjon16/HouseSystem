package com.system.housesystem.domain.dto;

import com.system.housesystem.entity.Device;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private Integer id;

    @Size(max = 30,message = "Maximum size of name is 30")
    private String name;

    private List<String> devices;
}
