package jh.park.screenback.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleDTO {
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private String location;
    private String description;
    private Long groupId;
    private String document;  // Assuming document is a string representation, adjust type if necessary
}
