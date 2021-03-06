package com.mapcalculator.geomaptools.controller;

import com.mapcalculator.geomaptools.model.DistanceResponse;
import com.mapcalculator.geomaptools.model.DistanceRequest;
import com.mapcalculator.geomaptools.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * This controller class retrieves the distance based on requested parameters
 */
@RestController
@RequestMapping("/distance")
public class DistanceController {

    private final DistanceService distanceService;

    @Autowired
    public DistanceController(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    /**
     * Get distance based on  DistanceRequest object
     * DistanceRequest consists of from_postcode and to_postcode variables
     *
     * @param request DistanceRequest consists of from_postcode and to_postcode
     * @return ResponseEntity object
     */
    @PostMapping
    public ResponseEntity<DistanceResponse> getDistance(@RequestBody DistanceRequest request) {
        try {
            DistanceResponse response = distanceService.getDistance(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalStateException ise) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ise.getMessage());
        }
    }

}
