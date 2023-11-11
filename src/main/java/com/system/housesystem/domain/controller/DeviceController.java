package com.system.housesystem.domain.controller;


import com.system.housesystem.domain.dto.DeviceDto;
import com.system.housesystem.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.system.housesystem.domain.mapper.DeviceMapper.toDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/device")
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/create")
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceDto deviceDto){
        return ResponseEntity.ok(deviceService.createDevice(deviceDto));
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<DeviceDto> findDevice(@PathVariable Integer id){
        return ResponseEntity.ok(toDto(deviceService.findById(id)));
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<DeviceDto>> getAllDevices(){
        return ResponseEntity.ok(deviceService.findAll());
    }
    @GetMapping("/findAllActive")
    public ResponseEntity<List<DeviceDto>> getAllActiveDevices(){
        return ResponseEntity.ok(deviceService.findAllActive());
    }
    @PostMapping("/percentage/{id}")
    public ResponseEntity<DeviceDto> setStatusPercentage(@PathVariable Integer id, @RequestParam Integer statusPercentage){
        return ResponseEntity.ok(deviceService.setStatusPercentage(id,statusPercentage));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<DeviceDto> updateDevice(@PathVariable Integer id, @RequestBody DeviceDto deviceDto){
        return ResponseEntity.ok(deviceService.updateDevice(id,deviceDto));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<DeviceDto> updateStatus(@PathVariable Integer id, @RequestParam String status){
        return ResponseEntity.ok(deviceService.updateStatus(id,status));
    }

    @PutMapping("/timer/{id}")
    public ResponseEntity<DeviceDto> setTimer(@PathVariable Integer id, @RequestBody DeviceDto deviceDto){
        return ResponseEntity.ok(deviceService.setTimer(id,deviceDto));
    }

    @PostMapping("/disable/{id}")
    public ResponseEntity<Void>disableDevice(@PathVariable Integer id){
        deviceService.disableDevice(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/enable/{id}")
    public ResponseEntity<Void>enableDevice(@PathVariable Integer id){
        deviceService.enableDevice(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/turnOffAll")
    public ResponseEntity<List<DeviceDto>> turnOffAll(){
        return ResponseEntity.ok(deviceService.turnOffAllDevices());
    }
    @PostMapping("/turnOnAll")
    public ResponseEntity<List<DeviceDto>> turnOnAll(){
        return ResponseEntity.ok(deviceService.turnOnAllDevices());
    }
    @PostMapping("/disableAll")
    public ResponseEntity<List<DeviceDto>> disableAll(){
        return ResponseEntity.ok(deviceService.disableAllDevices());
    }
    @PostMapping("/enableAll")
    public ResponseEntity<List<DeviceDto>> enableAll(){
        return ResponseEntity.ok(deviceService.enableAllDevices());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> permanentlyDeleteDevice(@PathVariable Integer id){
        deviceService.forceDeleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
