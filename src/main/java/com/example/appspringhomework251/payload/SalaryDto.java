package com.example.appspringhomework251.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto {
    private Double amount;

    private UUID userId;
}
