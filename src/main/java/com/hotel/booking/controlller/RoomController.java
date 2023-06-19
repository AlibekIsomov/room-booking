package com.hotel.booking.controlller;

import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.Room;
import com.hotel.booking.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/room")
@CrossOrigin(maxAge = 3600)
public class RoomController {
    @Autowired
    RoomService service;

    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable){
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Room room) throws Exception {
        return new ResponseEntity<>(service.create(room), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Room room) throws Exception {
        return new ResponseEntity<>(service.update(room), HttpStatus.CREATED);
    }

    @DeleteMapping("/get/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(service.delete(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }



//    Availability
    @GetMapping("/{id}/availability")
    public ResponseEntity<?> freeTime(@PathVariable Long id, @RequestParam(required = false) LocalDate date){
        return ResponseEntity.ok(service.freeTime(id,date));
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<?> booking(@PathVariable Long id,@RequestBody Booking booking) throws Exception {
        return new ResponseEntity<>(service.booking(id,booking), HttpStatus.CREATED);
    }

}
