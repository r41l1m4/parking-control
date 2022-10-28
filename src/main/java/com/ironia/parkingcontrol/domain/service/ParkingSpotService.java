package com.ironia.parkingcontrol.domain.service;

import com.ironia.parkingcontrol.domain.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }
}
