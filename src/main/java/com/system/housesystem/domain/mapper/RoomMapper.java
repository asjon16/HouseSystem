package com.system.housesystem.domain.mapper;

import com.system.housesystem.domain.dto.RoomDto;
import com.system.housesystem.entity.Device;
import com.system.housesystem.entity.Room;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RoomMapper {

    public static RoomDto toDto (Room r){
        RoomDto roomDto = new RoomDto();
        roomDto.setId(r.getId());
        roomDto.setName(r.getName());
        if (r.getDevices().isEmpty()){
            r.setDevices(new ArrayList<>());
        }else {
            roomDto.setDevices(r.getDevices().stream().map(Device::getName).collect(Collectors.toList()));
        }
        return roomDto;
    }

    public static Room toEntity(RoomDto r){
        Room room = new Room();
        room.setName(r.getName());
        return room;
    }
}
