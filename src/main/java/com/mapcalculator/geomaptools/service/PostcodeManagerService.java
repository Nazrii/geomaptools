package com.mapcalculator.geomaptools.service;

import com.mapcalculator.geomaptools.entity.PostCodeLatLng;
import com.mapcalculator.geomaptools.model.PostcodeLatLongUpdateRequest;
import com.mapcalculator.geomaptools.model.PostcodeManagerResponse;
import com.mapcalculator.geomaptools.model.PostcodeUpdateRequest;
import com.mapcalculator.geomaptools.repository.PostcodeManagerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This service contains the logic to manage the postcode details in database
 */
@Service
public class PostcodeManagerService {

    private final PostcodeManagerRepository postCodeManagerRepository;

    @Autowired
    public PostcodeManagerService(PostcodeManagerRepository postCodeManagerRepository) {
        this.postCodeManagerRepository = postCodeManagerRepository;
    }

    /**
     * Get postcode details via id
     *
     * @param id
     * @return
     */
    public PostcodeManagerResponse getPostCodeById(Long id) {
        Optional<PostCodeLatLng> optPostCodeLatLng = postCodeManagerRepository.findById(id);
        PostCodeLatLng postCodeLatLng = optPostCodeLatLng.orElseThrow(() -> new IllegalStateException(String.format("Sorry, %s is not on our system at the current time", id)));

        PostcodeManagerResponse response = new PostcodeManagerResponse();
        response.setId(postCodeLatLng.getId());
        response.setPostcode(postCodeLatLng.getPostcode());
        response.setLatitude(postCodeLatLng.getLatitude());
        response.setLongitude(postCodeLatLng.getLongitude());

        return response;
    }

    /**
     * Get the postcode details consisting id, latitude and longitude
     *
     * @param postcode
     * @return
     */
    public PostcodeManagerResponse getPostCodeDetail(String postcode) {
        PostCodeLatLng postCodeLatLng = postCodeManagerRepository.findByPostcode(postcode);
        if (postCodeLatLng == null) {
            throw new IllegalStateException(String.format("Sorry, %s is not on our system at the current time", postcode));
        }

        PostcodeManagerResponse response = new PostcodeManagerResponse();
        response.setId(postCodeLatLng.getId());
        response.setPostcode(postCodeLatLng.getPostcode());
        response.setLatitude(postCodeLatLng.getLatitude());
        response.setLongitude(postCodeLatLng.getLongitude());

        return response;
    }

    /**
     * Update the postcode to a new postcode
     *
     * @param request
     */
    public void updatePostCode(PostcodeUpdateRequest request) {
        if (StringUtils.isEmpty(request.getOldPostcode()) || StringUtils.isEmpty(request.getNewPostcode())) {
            throw new IllegalStateException("Sorry, postcode must not be empty");
        }

        PostCodeLatLng postCodeLatLng = postCodeManagerRepository.findByPostcode(request.getOldPostcode());
        if (postCodeLatLng == null) {
            throw new IllegalStateException(String.format("Sorry, %s is not on our system at the current time", request.getOldPostcode()));
        }

        postCodeLatLng.setPostcode(request.getNewPostcode());
        postCodeManagerRepository.save(postCodeLatLng);
    }

    /**
     * Update the latitude and longitude for the requested postcode
     *
     * @param request
     */
    public void updateLatLong(PostcodeLatLongUpdateRequest request) {
        if (StringUtils.isEmpty(request.getPostcode())) {
            throw new IllegalStateException("Sorry, postcode must not be empty");
        }

        PostCodeLatLng postCodeLatLng = postCodeManagerRepository.findByPostcode(request.getPostcode());
        if (postCodeLatLng == null) {
            throw new IllegalStateException(String.format("Sorry, %s is not on our system at the current time", request.getPostcode()));
        }

        postCodeLatLng.setLatitude(request.getNewLatitude());
        postCodeLatLng.setLongitude(request.getNewLongitude());
        postCodeManagerRepository.save(postCodeLatLng);
    }

}
