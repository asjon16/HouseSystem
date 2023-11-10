package com.system.housesystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room extends BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer id;
    @Column(name = "room_name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List <Device> devices;
}
