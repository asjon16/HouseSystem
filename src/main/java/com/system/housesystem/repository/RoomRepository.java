package com.system.housesystem.repository;

import com.system.housesystem.entity.Device;
import com.system.housesystem.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {


    List<Room> findAllByDeletedFalse();
    Room findByIdAndDevicesIsEmpty(Integer Id);
    Boolean existsByDevicesContains(Device device);




}
