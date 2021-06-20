package com.geopostal.ukpostal.services;

import com.geopostal.ukpostal.model.Postcode;

import java.util.List;

public interface PostcodeService {

    List<Postcode> getPostcodes();

    Postcode findPostcodeByPostcode(String s);

    Postcode insert(Postcode postcode);
}
