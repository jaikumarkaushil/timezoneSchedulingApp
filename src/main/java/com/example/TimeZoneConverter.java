package com.example;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TimeZoneConverter {

    /**
     * Represents a scheduled event with time zone information.
     */
    static class ScheduledEvent {
        String eventName;
        ZonedDateTime startTime;
        ZonedDateTime endTime;
        ZoneId targetTimeZone;

        public ScheduledEvent(String eventName, String startTime, String startTimeZone, String endTime, String endTimeZone, String targetTimeZone) {
            if (eventName == null || startTime == null || endTime == null || startTimeZone == null || endTimeZone == null || targetTimeZone == null) {
                throw new IllegalArgumentException("Event name, start time, end time, time zones must not be null");
            }
            this.eventName = eventName;
            this.startTime = ZonedDateTime.parse(startTime, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(startTimeZone)));
            this.endTime = ZonedDateTime.parse(endTime, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(endTimeZone)));
            this.targetTimeZone = ZoneId.of(targetTimeZone);
        }
    }

    /**
     * Verifies the accuracy of time zone conversions for a list of scheduled events.
     *
     * @param events List of scheduled events
     */

    public void verifyTimeZoneConversions(List<EventJson> events) {
        for (EventJson event : events) {
            try {
                ScheduledEvent scheduledEvent = new ScheduledEvent(
                        event.getEventName(),
                        event.getStartTime(),
                        event.getStartTimeZone(),
                        event.getEndTime(),
                        event.getEndTimeZone(),
                        event.getTargetTimeZone()
                );
                verifyEventTimeZoneConversion(scheduledEvent);
            } catch (DateTimeException e) {
                System.out.println("Error processing event " + event.getEventName() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Verifies the time zone conversion for a single event.
     *
     * @param event The scheduled event to verify
     */
    private void verifyEventTimeZoneConversion(ScheduledEvent event) {
        // Convert start and end times to the target time zone
        ZonedDateTime convertedStartTime = event.startTime.withZoneSameInstant(event.targetTimeZone);
        ZonedDateTime convertedEndTime = event.endTime.withZoneSameInstant(event.targetTimeZone);

        // Check if the converted start time is earlier than the converted end time
        if (convertedStartTime.isAfter(convertedEndTime)) {
            System.out.println("Error: Converted start time is later than converted end time for event " + event.eventName);
        }

        // Print event details
        printEventDetails(event, convertedStartTime, convertedEndTime);
    }

    /**
     * Prints the details of the event, including original and converted times.
     *
     * @param event              The scheduled event
     * @param convertedStartTime The converted start time
     * @param convertedEndTime   The converted end time
     */
    private void printEventDetails(ScheduledEvent event, ZonedDateTime convertedStartTime, ZonedDateTime convertedEndTime) {
        System.out.println("Event: " + event.eventName);
        System.out.println("Original Start Time: " + event.startTime);
        System.out.println("Original End Time: " + event.endTime);
        System.out.println("Converted Start Time: " + convertedStartTime);
        System.out.println("Converted End Time: " + convertedEndTime);
        System.out.println();
    }

    public static void main(String[] args) {
        TimeZoneConverter verifier = new TimeZoneConverter();

        // Read from JSON file
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<EventJson> events = mapper.readValue(new File("input.json"), new TypeReference<List<EventJson>>() {});
            verifier.verifyTimeZoneConversions(events);
        } catch (IOException e) {
            System.out.println("Error reading from JSON file: " + e.getMessage());
        }
    }
}