package com.hotel.booking.repository;


import com.hotel.booking.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {

    Page<Room> findAllByOrderByIdDesc(Pageable pageable);


}
