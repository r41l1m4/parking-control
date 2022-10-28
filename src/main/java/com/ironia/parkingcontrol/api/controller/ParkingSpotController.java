package com.ironia.parkingcontrol.api.controller;

import com.ironia.parkingcontrol.api.model.ParkingSpotDTO;
import com.ironia.parkingcontrol.domain.model.ParkingSpotModel;
import com.ironia.parkingcontrol.domain.service.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
     public ResponseEntity<?> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        var parkingSpotModel = new ParkingSpotModel();
         BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
         parkingSpotModel.setRegistrationDate(LocalDateTime.now((ZoneId.of("UTC"))));

         return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(parkingSpotService.save(parkingSpotModel));
     }
}
