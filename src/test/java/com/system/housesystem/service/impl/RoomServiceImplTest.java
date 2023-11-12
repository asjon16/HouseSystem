package com.system.housesystem.service.impl;

import com.system.housesystem.domain.dto.RoomDto;
import com.system.housesystem.entity.Room;
import com.system.housesystem.repository.RoomRepository;
import com.system.housesystem.service.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoomServiceImplTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Test
    public void testCreateRoom() {

        RoomDto roomDto = new RoomDto();

        when(roomRepository.save(any())).thenReturn(new Room());

        RoomDto createdRoom = roomService.createRoom(roomDto);

        verify(roomRepository, times(1)).save(any());
    }
}
