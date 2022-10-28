package com.ironia.parkingcontrol.api.service;

import com.ironia.parkingcontrol.api.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }
}
