package com.example.spi.impl;

import com.example.spi.service.SpiService;

public class SpiServiceImpl implements SpiService {
    @Override
    public String load() {
        return "impl by SpiServiceImpl";
    }
}
