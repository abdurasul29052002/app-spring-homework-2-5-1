package com.example.appspringhomework251.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String name;

    private String description;

    private Timestamp deadline;

    private boolean completed=false;

    private UUID fromUserId;

    private UUID toUserId;

    private boolean active=true;
}
