package com.geopostal.ukpostal.repositories;

import com.geopostal.ukpostal.model.Postcode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostcodeRepo extends CrudRepository<Postcode, Long> {

    Postcode findPostcodeByPostcode(String postcode);
}
