package com.befoodly.be.model.constant;

import com.befoodly.be.model.TimeSlot;

import java.util.List;

public class TimeSlotConstants {
    public static final List<TimeSlot> timeSlots = List.of(
            TimeSlot.builder().hour(12).minutes(30).build(),
            TimeSlot.builder().hour(13).minutes(00).build(),
            TimeSlot.builder().hour(13).minutes(30).build(),
            TimeSlot.builder().hour(14).minutes(00).build(),
            TimeSlot.builder().hour(14).minutes(30).build(),
            TimeSlot.builder().hour(15).minutes(00).build(),
            TimeSlot.builder().hour(15).minutes(30).build(),
            TimeSlot.builder().hour(19).minutes(30).build(),
            TimeSlot.builder().hour(20).minutes(00).build(),
            TimeSlot.builder().hour(20).minutes(30).build(),
            TimeSlot.builder().hour(21).minutes(00).build(),
            TimeSlot.builder().hour(21).minutes(30).build()
    );
}
