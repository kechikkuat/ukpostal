package com.geopostal.ukpostal.services;

import com.geopostal.ukpostal.model.Postcode;

import java.util.List;

public interface PostcodeService {

    List<Postcode> getPostcodes();

    Postcode findById(Long id);

    Postcode findPostcodeByPostcode(String s);

    Postcode insert(Postcode postcode);

    Postcode update(Long postcodeId, Postcode postcode);
}
