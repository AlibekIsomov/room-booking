package com.hotel.booking.controlller;


import com.hotel.booking.entity.Booking;
import com.hotel.booking.services.Impl.BookingServiceImpl;
import com.hotel.booking.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(maxAge = 3600)
public class BookingController {
    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private RoomService roomService;


}

