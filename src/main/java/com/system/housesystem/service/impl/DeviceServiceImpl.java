package com.system.housesystem.service.impl;

import com.system.housesystem.domain.dto.DeviceDto;
import com.system.housesystem.domain.exception.BadTimeException;
import com.system.housesystem.domain.exception.ResourceNotFoundException;
import com.system.housesystem.domain.exception.StatusException;
import com.system.housesystem.domain.mapper.DeviceMapper;
import com.system.housesystem.entity.Device;
import com.system.housesystem.repository.DeviceRepository;
import com.system.housesystem.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.system.housesystem.domain.mapper.DeviceMapper.toDto;
import static com.system.housesystem.domain.mapper.DeviceMapper.toEntity;

@RequiredArgsConstructor
@Validated
@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override // finished works
    public DeviceDto createDevice (DeviceDto dto){
        var device = toEntity(dto);
        device.setStatus(false);
        device.setActiveTime(0);
        deviceRepository.save(device);
        return toDto(device);
    }


    @Override // finished works
    public Device findById(Integer id) {
        return deviceRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String
                        .format("Device with id %s do not exist",id)));
    }

    @Override // finished works
    public DeviceDto updateDevice(Integer id, DeviceDto deviceDto) {
        var device = findById(id);
        if (device.isDeleted()){
            throw new StatusException("Device is disabled, please enable it before you process actions on it");
        }
        device.setName(deviceDto.getName());
        deviceRepository.save(device);
        return toDto(device);
    }

    @Override // finished works
    public DeviceDto updateStatus(Integer id, boolean status) {
        var device = findById(id);
        if (device.isDeleted()){
            throw new StatusException("Device is disabled, please enable it before you process actions on it");
        }
        if (device.getStatus()==status){
            throw new StatusException("Device status cannot be the same as it was");
        }
        device.setStatus(status);
        if (status) {
            device.setActiveTime(0);
        }
        deviceRepository.save(device);
        return toDto(device);
    }

    @Override // finished works
    public DeviceDto setTimer(Integer id, DeviceDto deviceDto) {
        var device = findById(id);
        if (device.isDeleted()){
            throw new StatusException("Device is disabled, please enable it before you process actions on it");
        }
        if (LocalTime.now().isAfter(deviceDto.getTimer())){
            throw new BadTimeException("You can only set a timer for ahead");
        }
        if (!device.getStatus()){
            throw new StatusException("The device is off and you cannot put a timer for it, please turn it on first");
        }
        device.setTimer(deviceDto.getTimer());
        deviceRepository.save(device);
        return toDto(device);
    }


    @Scheduled(fixedRate = 60000)
    public void timerTurnOff(){ // finished works
        List<Device> devices = deviceRepository.findAll();
        for (Device d : devices) {
            if (d.getStatus() != null && d.getStatus() && d.getTimer() != null && LocalTime.now().isAfter(d.getTimer())){
                d.setStatus(false);
                d.setTimer(null);
                deviceRepository.save(d);
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void activeTime(){ // finished works
        List<Device> devices = deviceRepository.findAll();
        for (Device d : devices){
            if (!d.isDeleted() && d.getStatus() != null && d.getStatus()){
                d.setActiveTime(d.getActiveTime()+1);
                deviceRepository.save(d);
            }if (!d.isDeleted() && d.getStatus()!=null &&!d.getStatus()){
                d.setActiveTime(0);
                deviceRepository.save(d);
            }
        }
    }

    @Override
    public List<DeviceDto> findAll() { // finished works
        return deviceRepository.findAll().stream().map(DeviceMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<DeviceDto> findAllActive() { // finished works
        return deviceRepository.findAllByDeletedFalse().stream().map(DeviceMapper::toDto).collect(Collectors.toList());
    }


    public void deleteDeviceById(Integer id){ // finished works
        var device =findById(id);
        device.setDeleted(true);
        deviceRepository.save(device);
    }
    public void forceDeleteById(Integer id){
        deviceRepository.deleteById(id);
    } // finished works


}
