package com.example.datageneratormicroservice.service;

import com.example.datageneratormicroservice.web.dto.DataTestOptionsDto;

public interface TestDataService {

    void sendMessages(DataTestOptionsDto dto);
}
