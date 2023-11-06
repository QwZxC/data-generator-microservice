package com.example.datageneratormicroservice.web.controller;

import com.example.datageneratormicroservice.model.Data;
import com.example.datageneratormicroservice.service.KafkaDataService;
import com.example.datageneratormicroservice.service.TestDataService;
import com.example.datageneratormicroservice.web.dto.DataDto;
import com.example.datageneratormicroservice.web.dto.DataTestOptionsDto;
import com.example.datageneratormicroservice.web.mapper.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/data")
@RequiredArgsConstructor
public class DataController {

    private final DataMapper dataMapper;
    private final KafkaDataService kafkaDataService;
    private final TestDataService testDataService;

    @PostMapping
    public HttpStatus send(@RequestBody DataDto dto) {

        Data data = dataMapper.toEntity(dto);
        kafkaDataService.send(data);
        return HttpStatus.OK;
    }

    @PostMapping("/test")
    public void testSend(@RequestBody DataTestOptionsDto dto) {
        testDataService.sendMessages(dto);
    }
}
