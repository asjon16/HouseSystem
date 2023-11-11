package com.system.housesystem.domain.controller;

import com.system.housesystem.domain.dto.RoomDto;
import com.system.housesystem.entity.Room;
import com.system.housesystem.service.DeviceService;
import com.system.housesystem.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.system.housesystem.domain.mapper.RoomMapper.toDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto roomName){
        return ResponseEntity.ok(roomService.createRoom(roomName));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoomDto> updateDepartmentNoUsers(@PathVariable Integer id, @RequestBody RoomDto name){
        return ResponseEntity.ok(roomService.updateName(id,name));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<RoomDto>> getAllRoms(){
        return ResponseEntity.ok(roomService.findAllRooms());
    }
    @GetMapping("/findAllActive")
    public ResponseEntity<List<RoomDto>> findAllActiveRooms(){
        return ResponseEntity.ok(roomService.findAllActiveRooms());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<RoomDto> findRoom(@PathVariable Integer id){
        return ResponseEntity.ok(toDto(roomService.findById(id)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void>deleteRoom(@PathVariable Integer id){
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add/{roomId}")
    public ResponseEntity<RoomDto> addDeviceToRoom(@PathVariable Integer roomId, @RequestParam Integer deviceId){
        return ResponseEntity.ok(roomService.addDeviceIntoRoom(roomId,deviceId));
    }
    @PostMapping("/remove/{roomId}")
    public ResponseEntity<RoomDto> removeDeviceFromRoom(@PathVariable Integer roomId, @RequestParam Integer deviceId){
        return ResponseEntity.ok(roomService.removeDeviceFromRoom(roomId,deviceId));
    }
    @PostMapping("/removeAll/{roomId}")
    public ResponseEntity<Void> removeAllDevicesFromRoom(@PathVariable Integer roomId){
        roomService.removeAllDevices(roomId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/turnOffAll/{roomId}")
    public ResponseEntity<Void> turnOffAllDevicesOfRoom(@PathVariable Integer roomId){
        roomService.turnOffAllDevicesOfRoom(roomId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/turnOnAll/{roomId}")
    public ResponseEntity<Void> turnOnAllDevicesOfRoom(@PathVariable Integer roomId){
        roomService.turnOnAllDevicesOfRoom(roomId);
        return new ResponseEntity<>(HttpStatus.OK);
    }







}
