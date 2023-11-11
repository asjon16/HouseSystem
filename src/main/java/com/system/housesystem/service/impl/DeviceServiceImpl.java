package com.system.housesystem.service.impl;

import com.system.housesystem.domain.dto.DeviceDto;
import com.system.housesystem.domain.exception.BadTimeException;
import com.system.housesystem.domain.exception.ResourceNotFoundException;
import com.system.housesystem.domain.exception.StatusException;
import com.system.housesystem.domain.mapper.DeviceMapper;
import com.system.housesystem.entity.Device;
import com.system.housesystem.entity.Status;
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

    @Override
    public DeviceDto createDevice (DeviceDto dto){
        var device = toEntity(dto);
            device.setStatus(Status.OFF);
            device.setActiveTime(0);
            deviceRepository.save(device);
        return toDto(device);
    }


    @Override
    public Device findById(Integer id) {
        return deviceRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String
                        .format("Device with id %s do not exist",id)));
    }

    @Override
    public DeviceDto updateDevice(Integer id, DeviceDto deviceDto) {
        var device = findById(id);
        if (device.isDeleted()){
            throw new StatusException("Device is disabled, please enable it before you process actions on it");
        }
        device.setName(deviceDto.getName());
        deviceRepository.save(device);
        return toDto(device);
    }

    @Override
    public DeviceDto updateStatus(Integer id, String status) {
        var device = findById(id);

        if (device.isDeleted()){
            throw new StatusException("Device is disabled, please enable it before you process actions on it");
        }
        if (device.getStatus().name().equals(status)){
            throw new StatusException("Device status cannot be the same as it was");
        }
        if (status.equals("ON")||status.equals("OFF")){
            device.setStatus(Status.valueOf(status));
            if (status.equals("OFF")) {
                device.setActiveTime(0);
                device.setTimer(null);
             }
        }else throw new StatusException("Status can only be ON/OFF");
        deviceRepository.save(device);
        return toDto(device);
    }

    @Override
    public DeviceDto setTimer(Integer id, DeviceDto deviceDto) {
        var device = findById(id);
        if (device.isDeleted()){
            throw new StatusException("Device is disabled, please enable it before you process actions on it");
        }
        if (LocalTime.now().isAfter(deviceDto.getTimer())){
            throw new BadTimeException("You can only set a timer for ahead");
        }
        if (device.getStatus().equals(Status.OFF)){
            throw new StatusException("The device is off and you cannot put a timer for it, please turn it on first");
        }
        device.setTimer(deviceDto.getTimer());
        deviceRepository.save(device);
        return toDto(device);
    }


    @Scheduled(fixedRate = 60000)
    public void timerTurnOff(){
        List<Device> devices = deviceRepository.findAll();
        for (Device d : devices) {
            if (d.getStatus().equals(Status.ON) && d.getTimer() != null && LocalTime.now().isAfter(d.getTimer())){
                d.setStatus(Status.OFF);
                d.setTimer(null);
                deviceRepository.save(d);
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void activeTime(){
        List<Device> devices = deviceRepository.findAll();
        for (Device d : devices) {
            if (!d.isDeleted()) {
                if (Status.ON.equals(d.getStatus())) {
                    d.setActiveTime(d.getActiveTime() + 1);
                } else {
                    d.setActiveTime(0);
                    d.setStatusPercentage(0);
                    d.setTimer(null);
                }
                deviceRepository.save(d);
            }
        }
    }
    @Override
    public DeviceDto setStatusPercentage(Integer id,Integer percentage){
        var device = deviceRepository.findByIdAndDeletedFalse(id);
        if (device.getStatus().equals(Status.OFF)){
            throw new StatusException("Device is off, please turn it on first.");
        }
        if (percentage<0 || percentage > 100){
            throw new StatusException("You can only put the percentage between 0-100 for this device.");
        }
        device.setStatusPercentage(percentage);
        deviceRepository.save(device);
        return toDto(device);
    }

    @Override
    public List<DeviceDto> findAll() {
        return deviceRepository.findAll().stream().map(DeviceMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<DeviceDto> findAllActive() {
        return deviceRepository.findAllByDeletedFalse().stream().map(DeviceMapper::toDto).collect(Collectors.toList());
    }


    public void disableDevice(Integer id){
        var device =deviceRepository.findByIdAndDeletedFalse(id);
        device.setDeleted(true);
        device.setStatus(Status.OFF);
        device.setActiveTime(0);
        device.setTimer(null);
        device.setStatusPercentage(0);
        deviceRepository.save(device);
    }
    public void enableDevice(Integer id){
        var device = deviceRepository.findByIdAndDeletedTrue(id);
        device.setDeleted(false);
        device.setStatus(Status.OFF);
        device.setActiveTime(0);
        device.setTimer(null);
        device.setStatusPercentage(0);
        deviceRepository.save(device);
    }

    public List<DeviceDto> turnOffAllDevices(){
        List<Device> devices = deviceRepository.findAllByDeletedFalse();
        for (Device d : devices){
            disableDevice(d.getId());
        }
        return devices.stream().map(DeviceMapper::toDto).collect(Collectors.toList());
    }

    public List<DeviceDto> turnOnAllDevices(){
        List<Device> devices = deviceRepository.findAllByDeletedTrue();
        for (Device d : devices){
            enableDevice(d.getId());
        }
        return devices.stream().map(DeviceMapper::toDto).collect(Collectors.toList());
    }

    public List<DeviceDto> disableAllDevices(){
        List<Device> devices = deviceRepository.findAllByDeletedFalse();
        if (devices.isEmpty()){
            throw new ResourceNotFoundException("All Devices are disabled already.");
        }
        for (Device d : devices){
            d.setDeleted(true);
            deviceRepository.save(d);
        }
        return devices.stream().map(DeviceMapper::toDto).collect(Collectors.toList());
    }
    public List<DeviceDto> enableAllDevices(){
        List<Device> devices = deviceRepository.findAllByDeletedTrue();
        if (devices.isEmpty()){
            throw new ResourceNotFoundException("All Devices are enabled already.");
        }
        for (Device d : devices){
            d.setDeleted(false);
            deviceRepository.save(d);
        }
        return devices.stream().map(DeviceMapper::toDto).collect(Collectors.toList());
    }



    public void forceDeleteById(Integer id){
        deviceRepository.deleteById(id);
    }


}
