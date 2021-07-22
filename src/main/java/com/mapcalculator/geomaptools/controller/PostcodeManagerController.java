package com.mapcalculator.geomaptools.controller;

import com.mapcalculator.geomaptools.model.PostcodeLatLongUpdateRequest;
import com.mapcalculator.geomaptools.model.PostcodeManagerResponse;
import com.mapcalculator.geomaptools.model.PostcodeRequest;
import com.mapcalculator.geomaptools.model.PostcodeUpdateRequest;
import com.mapcalculator.geomaptools.service.PostcodeManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * This controller class manages the postcode and its coordinates in the database
 */
@RestController
@RequestMapping("/postcode")
public class PostcodeManagerController {

    private final PostcodeManagerService postCodeManagerService;

    @Autowired
    public PostcodeManagerController(PostcodeManagerService postCodeManagerService) {
        this.postCodeManagerService = postCodeManagerService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PostcodeManagerResponse> getPostCodeById(@PathVariable Long id){
        try {
            PostcodeManagerResponse response = postCodeManagerService.getPostCodeById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalStateException ise) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ise.getMessage());
        }
    }


    /**
     * This method retrieve the postcode details (latitude, longitude and id)
     *
     * @param request PostcodeRequest object containing postcode string
     * @return ResponseEntity object
     */
    @PostMapping
    public ResponseEntity<PostcodeManagerResponse> getPostCodeDetail(@RequestBody PostcodeRequest request) {
        try {
            PostcodeManagerResponse response = postCodeManagerService.getPostCodeDetail(request.getPostcode());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalStateException ise) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ise.getMessage());
        }
    }

    /**
     * This method updates the postcode to a new postcode
     *
     * @param request PostcodeUpdateRequest object consists old and new postcode
     * @return ResponseEntity object
     */
    @PutMapping("/edit")
    public ResponseEntity<Object> updatePostCode(@RequestBody PostcodeUpdateRequest request) {
        try {
            postCodeManagerService.updatePostCode(request);
            return ResponseEntity.status(HttpStatus.OK).body("Postcode has successfully been updated");
        } catch (IllegalStateException ise) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ise.getMessage());
        }
    }

    /**
     * This method updates the postcode coordinate which is the latitude and longitude
     *
     * @param request PostcodeLatLongUpdateRequest object with postcode and new latitude and longitude
     * @return ResponseEntity object
     */
    @PutMapping("/latlong/edit")
    public ResponseEntity<Object> updateLatLong(@RequestBody PostcodeLatLongUpdateRequest request) {
        try {
            postCodeManagerService.updateLatLong(request);
            return ResponseEntity.status(HttpStatus.OK).body("Latitude/Longitude has successfully been updated");
        } catch (IllegalStateException ise) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ise.getMessage());
        }
    }

}
