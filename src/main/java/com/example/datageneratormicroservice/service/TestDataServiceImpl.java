package com.example.datageneratormicroservice.service;

import com.example.datageneratormicroservice.model.Data;
import com.example.datageneratormicroservice.model.test.DataTestOptions;
import com.example.datageneratormicroservice.web.dto.DataTestOptionsDto;
import com.example.datageneratormicroservice.web.mapper.DataTestOptionsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TestDataServiceImpl implements TestDataService {

    private final DataTestOptionsMapper dataTestOptionsMapper;
    private final ScheduledExecutorService executorService
            = Executors.newSingleThreadScheduledExecutor();
    private final KafkaDataService kafkaDataService;


    @Override
    public void sendMessages(DataTestOptionsDto dto) {
        DataTestOptions testOptions = dataTestOptionsMapper.toEntity(dto);

        if (testOptions.getMeasurementTypes().length > 0) {
            executorService.scheduleAtFixedRate(
                    () -> {
                        Data data = new Data();
                        data.setSensorId(
                                (long) getRandomNumber(1, 10)
                        );
                        data.setMeasurement(
                                getRandomNumber(15, 100)
                        );
                        data.setMeasurementType(
                                getRandomMeasurementType(
                                        testOptions.getMeasurementTypes()
                                )
                        );
                        data.setTimestamp(
                                LocalDateTime.now()
                        );
                        kafkaDataService.send(data);
                    },
                    0,
                    testOptions.getDelayInSeconds(),
                    TimeUnit.SECONDS)
            ;
        }
    }

    private double getRandomNumber(int min, int max) {
        return (Math.random() * (max - min) + min);
    }

    private Data.MeasurementType getRandomMeasurementType(
            Data.MeasurementType[] measurementTypes
    ) {
        int randomTypeId = (int) (Math.random() * measurementTypes.length);
        return measurementTypes[randomTypeId];
    }
}
