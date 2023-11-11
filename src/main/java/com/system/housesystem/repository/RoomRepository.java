package com.system.housesystem.repository;

import com.system.housesystem.entity.Device;
import com.system.housesystem.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {


    List<Room> findAllByDeletedFalse();
    Boolean existsByDevicesContains(Device device);

    @Query("SELECT r.devices FROM Room r WHERE r.id = :roomId")
    List<Device> findAllDevicesById(@Param("roomId") Integer roomId);


}
