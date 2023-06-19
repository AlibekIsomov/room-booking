package com.hotel.booking.services.Impl;

import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.Room;
import com.hotel.booking.entity.Types;
import com.hotel.booking.entity.User;
import com.hotel.booking.repository.BookingRepo;
import com.hotel.booking.repository.RoomRepo;
import com.hotel.booking.repository.UserRepo;
import com.hotel.booking.response.FreeTime;
import com.hotel.booking.services.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService<Room> {
    private static final Logger LOG = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Autowired
    private BookingRepo bookingRepository;

    private final RoomRepo roomRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoomRepo roomRepository;

    public RoomServiceImpl(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }


    @Override
    public Page<Room> getAll(Pageable pageable) {
        Page<Room> some = roomRepo.findAllByOrderByIdDesc(pageable);
        if(some.isEmpty()){
            return Page.empty();
        }
        return some;
    }

    @Override
    public Room create(Room room) throws Exception {
        if(room.isNewEntity()){
            room.setCreated(LocalDateTime.now());
            room.setModified(LocalDateTime.now());
            return roomRepo.save(room);
        }
        LOG.error("Failed to save the entity of class '{}', Id should be null", room.getClass());
        return null;

    }

    @Override
    public Room update(Room room) {
        if(!room.isNewEntity()){
            room.setCreated(LocalDateTime.now());
            room.setModified(LocalDateTime.now());
            return roomRepo.save(room);
        }

        LOG.error("Failed to save the entity of class '{}', Id should not be null", room.getClass());
        return null;

    }

    @Override
    public Room getById(Long id) {
        return roomRepo.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        Room room = getById(id);
        if(room == null){
            LOG.error("Failed to delete entity with ID '{}' as it does not exist", id);
            return false;
        }
        try {
            roomRepo.delete(room);
            return true;
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void updateRoomAvailability(Long roomId, LocalDateTime startTime, LocalDateTime endTi) {

    }

    @Override
    public void dataAdd() {
        roomRepo.save(new Room(1l,null,null,"Room 1", Types.TEAM,14,true));
        roomRepo.save(new Room(2l,null,null,"Room 2", Types.TEAM,12,true));
        userRepo.save(new User(1l,"Abbos"));
        bookingRepository.save(new Booking(1l,LocalDateTime.now().plusHours(0),LocalDateTime.now().plusHours(2),roomRepo.findById(1l).get(),userRepo.findById(1l).get()));
        bookingRepository.save(new Booking(2l,LocalDateTime.now().plusHours(3),LocalDateTime.now().plusHours(4),roomRepo.findById(1l).get(),userRepo.findById(1l).get()));
        bookingRepository.save(new Booking(3l,LocalDateTime.now().plusHours(5),LocalDateTime.now().plusHours(6),roomRepo.findById(1l).get(),userRepo.findById(1l).get()));
        bookingRepository.save(new Booking(4l,LocalDateTime.now().plusHours(8),LocalDateTime.now().plusHours(10),roomRepo.findById(1l).get(),userRepo.findById(1l).get()));
    }

    @Override
    public Booking booking(Long roomId,Booking booking) {
        booking.setRoom(roomRepo.findById(roomId).get());
        return bookingRepository.save(booking);
    }

    @Override
    public List<FreeTime> freeTime(Long roomId,LocalDate date) {
        if (date == null) date=LocalDate.now();
        List<Booking> bookings = bookingRepository.findAllByRoomIdAndStartTimeBetweenOrderByStartTime(roomId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
        List<FreeTime> freeTimeList = new ArrayList<>();
        if (!bookings.isEmpty()) {
            Integer startHour=0;
            Integer startMinute=0;
            Integer startSecond=0;
            for (int i = 0; i < bookings.size(); i++) {
                if (!(bookings.get(i).getStartTime().getHour() == startHour &&
                        bookings.get(i).getStartTime().getMinute() == startMinute &&
                        bookings.get(i).getStartTime().getSecond() == startSecond) ) {
                    freeTimeList.add(new FreeTime(
                            LocalDateTime.now().withHour(startHour).withMinute(startMinute).withSecond(startSecond),
                            bookings.get(i).getStartTime()));
                }
                if (bookings.size() == (i + 1)) {
                    if (!(bookings.get(i).getEndTime().getHour() == 0 &&
                            bookings.get(i).getEndTime().getMinute() == 0 &&
                            bookings.get(i).getEndTime().getSecond() == 0 &&
                            bookings.get(i).getEndTime().getDayOfMonth() == (LocalDateTime.now().getDayOfMonth() + 1)) ) {
                            freeTimeList.add(new FreeTime(
                                    bookings.get(i).getEndTime(),
                                LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).plusDays(1)));
                    }

                }
                startHour = bookings.get(i).getEndTime().getHour();
                startMinute = bookings.get(i).getEndTime().getMinute();
                startSecond = bookings.get(i).getEndTime().getSecond();
            }
            return freeTimeList;
        }
        else {
            FreeTime freeTime = new FreeTime();
            freeTime.setStartTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
            freeTime.setEndTime(LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0));
            freeTimeList.add(freeTime);
            return freeTimeList;
        }
    }



}
