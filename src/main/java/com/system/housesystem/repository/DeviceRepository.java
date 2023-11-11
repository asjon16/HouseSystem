package com.system.housesystem.repository;

import com.system.housesystem.entity.Device;
import com.system.housesystem.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DeviceRepository extends JpaRepository<Device,Integer> {

    List<Device> findAllByDeletedFalse();
    List<Device> findAllByDeletedTrue();
    List<Device>findAll();

    Device findByIdAndDeletedFalse(Integer id);
    Device findByIdAndDeletedTrue(Integer id);

}
