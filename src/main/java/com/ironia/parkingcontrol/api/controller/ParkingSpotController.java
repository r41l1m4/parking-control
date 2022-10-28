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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        if(parkingSpotService.existsByLicensePlate(parkingSpotDTO.getLicensePlateCar())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Conflict: License Plate Car is already in use!");
        }
        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Conflict: Parking Spot is already in use!");
        }
        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDTO.getApartment(), parkingSpotDTO.getBlock())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Conflict: Parking Spot is already registered for this apartment/block!");
        }

        var parkingSpotModel = new ParkingSpotModel();
         BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
         parkingSpotModel.setRegistrationDate(LocalDateTime.now((ZoneId.of("UTC"))));

         return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(parkingSpotService.save(parkingSpotModel));
     }

     @GetMapping
     public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpot() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(parkingSpotService.findAll());
     }

     @GetMapping("/{id}")
     public ResponseEntity<?> getOneParkingSpot(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotModel = parkingSpotService.findById(id);
        if(parkingSpotModel.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Parking Spot not found!");
        }
         return ResponseEntity
                 .status(HttpStatus.OK)
                 .body(parkingSpotModel.get());
     }

}
