package com.example.spi.app;

import com.example.spi.service.SpiService;

import java.util.ServiceLoader;

public class SpiApp {
    public static void main(String[] args) {
        SpiApp.service();
    }

    public static void service() {
        ServiceLoader<SpiService> load = ServiceLoader.load(SpiService.class);
        for (SpiService spiService : load) {
            System.out.println("spiService.load() = " + spiService.load());
        }
    }
}
