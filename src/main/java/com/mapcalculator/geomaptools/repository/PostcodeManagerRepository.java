package com.mapcalculator.geomaptools.repository;

import com.mapcalculator.geomaptools.entity.PostCodeLatLng;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostcodeManagerRepository extends JpaRepository<PostCodeLatLng, Long> {
    PostCodeLatLng findByPostcode(String postcode);
}
