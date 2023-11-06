package com.example.datageneratormicroservice.web.dto;

import com.example.datageneratormicroservice.model.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class DataDto {

    private Long sensorId;
    @JsonFormat(pattern = "dd-MM-yyyy'T' HH:mm:ss")
    private LocalDateTime timestamp;
    private Double measurement;
    private Data.MeasurementType measurementType;
}
