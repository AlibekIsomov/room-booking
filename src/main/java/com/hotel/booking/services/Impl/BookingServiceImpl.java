package com.hotel.booking.services.Impl;

import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.Room;
import com.hotel.booking.repository.BookingRepo;
import com.hotel.booking.repository.RoomRepo;
import com.hotel.booking.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl {
    @Autowired
    private RoomRepo roomRepository;

    @Autowired
    private BookingRepo bookingRepository;
    @Autowired
    private RoomService roomService;


}

