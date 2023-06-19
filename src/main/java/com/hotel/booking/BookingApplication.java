package com.hotel.booking;

import com.hotel.booking.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class BookingApplication implements ApplicationRunner {
	@Autowired
	RoomService roomService;

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		roomService.dataAdd();
		roomService.freeTime(1l, LocalDate.now());
	}
}
