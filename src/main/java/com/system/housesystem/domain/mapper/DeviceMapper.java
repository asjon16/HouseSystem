package com.system.housesystem.domain.mapper;

import com.system.housesystem.domain.dto.DeviceDto;
import com.system.housesystem.entity.Device;

public class DeviceMapper {

    public static DeviceDto toDto(Device d){
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(d.getId());
        deviceDto.setName(d.getName());
        deviceDto.setActiveTime(d.getActiveTime());
        deviceDto.setStatus(d.getStatus());
        deviceDto.setTimer(d.getTimer());
        return deviceDto;
    }

    public static Device toEntity (DeviceDto d){
        Device device = new Device();
        device.setName(d.getName());
        return device;
    }
}
