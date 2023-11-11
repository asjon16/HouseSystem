package com.system.housesystem.service;

import com.system.housesystem.domain.dto.DeviceDto;
import com.system.housesystem.domain.dto.RoomDto;
import com.system.housesystem.entity.Device;
import com.system.housesystem.entity.Room;

import java.util.List;

public interface RoomService {

    Room findById(Integer id);

    RoomDto createRoom(RoomDto roomDto);

    RoomDto updateName(Integer id, RoomDto name);

    void deleteRoom(Integer id);

    List<RoomDto> findAllRooms();


    List<RoomDto> findAllActiveRooms();
    RoomDto turnOnAllDevicesOfRoom(Integer id);
    RoomDto turnOffAllDevicesOfRoom(Integer id);

    RoomDto addDeviceIntoRoom(Integer roomId,Integer deviceId);

    RoomDto removeDeviceFromRoom(Integer roomId, Integer deviceId);

    void removeAllDevices(Integer roomId);


}
