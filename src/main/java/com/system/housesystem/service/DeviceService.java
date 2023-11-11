package com.system.housesystem.service;

import com.system.housesystem.domain.dto.DeviceDto;
import com.system.housesystem.entity.Device;

import java.util.List;

public interface DeviceService {

    Device findById(Integer id);

    DeviceDto createDevice(DeviceDto deviceDto);

    DeviceDto updateDevice(Integer id, DeviceDto deviceDto);

    DeviceDto setStatusPercentage(Integer id,Integer percentage);

    DeviceDto updateStatus(Integer id, String status);

    DeviceDto setTimer(Integer id, DeviceDto deviceDto);

    List<DeviceDto> findAll();
    List<DeviceDto> findAllActive();

    List<DeviceDto> turnOffAllDevices();
    List<DeviceDto> turnOnAllDevices();
    List<DeviceDto> disableAllDevices();
    List<DeviceDto> enableAllDevices();

    void disableDevice(Integer id);
    void enableDevice(Integer id);
    void forceDeleteById(Integer id);





}
