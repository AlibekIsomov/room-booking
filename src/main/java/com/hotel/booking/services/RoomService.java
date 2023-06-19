package com.hotel.booking.services;

import com.hotel.booking.entity.Booking;
import com.hotel.booking.response.FreeTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RoomService <Room>{
    public Page<Room> getAll(Pageable pageable);
    public Room create(Room entity) throws Exception;
    public Room update (Room entity);
    public Room getById(Long id);
    public boolean delete(Long id);
    public void updateRoomAvailability(Long roomId, LocalDateTime startTime, LocalDateTime endTi);
    public void dataAdd();
    public List<FreeTime> freeTime(Long roomId,LocalDate date);
    public Booking booking(Long roomId,Booking booking);

}
