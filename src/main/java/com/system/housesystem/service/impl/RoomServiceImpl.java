package com.system.housesystem.service.impl;

import com.system.housesystem.domain.dto.RoomDto;
import com.system.housesystem.domain.exception.BadDeletionException;
import com.system.housesystem.domain.exception.ResourceNotFoundException;
import com.system.housesystem.domain.exception.StatusException;
import com.system.housesystem.domain.mapper.RoomMapper;
import com.system.housesystem.entity.Device;
import com.system.housesystem.entity.Room;
import com.system.housesystem.entity.Status;
import com.system.housesystem.repository.RoomRepository;
import com.system.housesystem.service.DeviceService;
import com.system.housesystem.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.system.housesystem.domain.mapper.RoomMapper.toDto;
import static com.system.housesystem.domain.mapper.RoomMapper.toEntity;

@RequiredArgsConstructor
@Validated
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final DeviceService deviceService;
    @Override
    public Room findById(Integer id) {
        return roomRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String
                        .format("Room with id %s do not exist",id)));
    }

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        var room = toEntity(roomDto);
        room.setDevices(new ArrayList<>());
        roomRepository.save(room);
        return toDto(room);
    }

    @Override
    public RoomDto updateName(Integer id, RoomDto name) {
        var room = findById(id);
        room.setName(name.getName());
        roomRepository.save(room);
        return toDto(room);
    }

    public RoomDto turnOffAllDevicesOfRoom(Integer id){
        var room = findById(id);
        List<Device> devices = roomRepository.findAllDevicesById(id);
        for (Device d : devices){
            d.setStatusPercentage(0);
            d.setStatus(Status.OFF);
            d.setActiveTime(0);
            d.setTimer(null);

        }roomRepository.save(room);
        return toDto(room);
    }
    public RoomDto turnOnAllDevicesOfRoom(Integer id){
        var room = findById(id);
        List<Device> devices = roomRepository.findAllDevicesById(id);
        for (Device d : devices){
            d.setStatusPercentage(0);
            d.setStatus(Status.ON);
            d.setActiveTime(0);
            d.setTimer(null);

        }roomRepository.save(room);
        return toDto(room);
    }

    @Override
    public void deleteRoom(Integer id) {
        var room = findById(id);
        if (!room.getDevices().isEmpty()){
            throw new BadDeletionException("You cannot delete this room as it still has devices assigned in it." +
                    "Please remove the devices before deleting the room.");
        }
        room.setDeleted(true);
        roomRepository.save(room);
    }

   /* @Override // Optional **
    public void deleteRoom(Integer id) {
        var room = roomRepository.findByIdAndDevicesIsEmpty(id);
        room.setDeleted(true);
        roomRepository.save(room);
    }*/

    @Override
    public List<RoomDto> findAllRooms() {
        List<Room> roomList = roomRepository.findAll();
        return roomList.stream().map(RoomMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RoomDto> findAllActiveRooms() {
        List<Room> roomList = roomRepository.findAllByDeletedFalse();
        return roomList.stream().map(RoomMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public RoomDto addDeviceIntoRoom(Integer roomId, Integer deviceId) {
        var room = findById(roomId);
        var device = deviceService.findById(deviceId);
        if (!roomRepository.existsByDevicesContains(device)) {
            room.getDevices().add(device);
            roomRepository.save(room);
        }else throw new StatusException("That device is already assigned on a room");

        return toDto(room);
    }

    @Override
    public RoomDto removeDeviceFromRoom(Integer roomId, Integer deviceId) {
        var room = findById(roomId);
        var device = deviceService.findById(deviceId);
        room.getDevices().remove(device);
        roomRepository.save(room);
        return toDto(room);
    }

    @Override
    public void removeAllDevices(Integer roomId) {
        var room = findById(roomId);
        room.setDevices(null);
        roomRepository.save(room);
    }


}
