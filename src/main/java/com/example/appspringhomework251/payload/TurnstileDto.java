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
public class TurnstileDto {
    @NotNull
    private Timestamp income;

    private Timestamp outcome;

    private boolean completed;

    @NotNull
    private UUID userId;
}
