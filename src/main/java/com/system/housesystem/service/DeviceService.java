package com.system.housesystem.service;

import com.system.housesystem.domain.dto.DeviceDto;
import com.system.housesystem.entity.Device;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public interface DeviceService {
    // Works don't touch
    Device findById(Integer id);

    DeviceDto createDevice(DeviceDto deviceDto);

    DeviceDto updateDevice(Integer id, DeviceDto deviceDto);

    DeviceDto updateStatus(Integer id, boolean status);

    DeviceDto setTimer(Integer id, DeviceDto deviceDto);

    List<DeviceDto> findAll();
    List<DeviceDto> findAllActive();

    void deleteDeviceById(Integer id);
    void forceDeleteById(Integer id);





}
