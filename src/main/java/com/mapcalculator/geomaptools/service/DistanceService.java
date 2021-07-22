package com.mapcalculator.geomaptools.service;

import com.mapcalculator.geomaptools.entity.PostCodeLatLng;
import com.mapcalculator.geomaptools.model.Coordinate;
import com.mapcalculator.geomaptools.model.DistanceRequest;
import com.mapcalculator.geomaptools.model.DistanceResponse;
import com.mapcalculator.geomaptools.repository.PostcodeManagerRepository;
import com.mapcalculator.geomaptools.util.DistanceCalculator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service class contains the logic to retrieve the distance
 */
@Service
public class DistanceService {

    private static final String UNIT_KM = "km";

    private final PostcodeManagerRepository postcodeManagerRepository;
    private final DistanceCalculator distanceCalculator;

    @Autowired
    public DistanceService(PostcodeManagerRepository postcodeManagerRepository, DistanceCalculator distanceCalculator) {
        this.postcodeManagerRepository = postcodeManagerRepository;
        this.distanceCalculator  = distanceCalculator;
    }

    /**
     * This method separates postcodes from the request param
     * Get latitude and longitude for both postcodes
     * Calculate the distance
     * Set response detail
     *
     * @param request object consists from_postcode and to_postcode
     * @return object DistanceResponse
     */
    public DistanceResponse getDistance(DistanceRequest request) {
        DistanceResponse response = new DistanceResponse();
        double distance;

        String fromPostcode = request.getFromPostcode();
        String toPostcode = request.getToPostcode();

        // Get latitude and longitude for both postcodes
        PostCodeLatLng fromLatLong = getLatLong(request.getFromPostcode());
        PostCodeLatLng toLatLong = getLatLong(request.getToPostcode());

        double fromLatitude = fromLatLong.getLatitude();
        double fromLongitude = fromLatLong.getLongitude();
        double toLatitude = toLatLong.getLatitude();
        double toLongitude = toLatLong.getLongitude();

        // Calculate distance
        distance = distanceCalculator.calculate(fromLatitude, fromLongitude, toLatitude, toLongitude);

        // Set response detail
        response.setDistance(distance);
        response.setUnit(UNIT_KM);
        Coordinate from = new Coordinate(fromPostcode, fromLatitude, fromLongitude);
        Coordinate to = new Coordinate(toPostcode, toLatitude, toLongitude);
        response.setFromCoordinate(from);
        response.setToCoordinate(to);

        return response;
    }

    private PostCodeLatLng getLatLong(String postcode) {
        if (StringUtils.isEmpty(postcode)) {
            throw new IllegalStateException("Sorry, postcode must not be empty");
        }

        PostCodeLatLng postCodeLatLng = postcodeManagerRepository.findByPostcode(postcode);
        if (postCodeLatLng == null) {
            throw new IllegalStateException(String.format("Sorry, %s is not on our system at the current time", postcode));
        }
        return postCodeLatLng;
    }
}
