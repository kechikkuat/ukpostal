package com.geopostal.ukpostal.services;

import com.geopostal.ukpostal.model.Postcode;
import com.geopostal.ukpostal.repositories.PostcodeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostcodeServiceImpl implements PostcodeService{
    private final static Logger LOGGER = LoggerFactory.getLogger(PostcodeServiceImpl.class);
    PostcodeRepo postcodeRepo;

    public PostcodeServiceImpl(PostcodeRepo postcodeRepo){
        this.postcodeRepo = postcodeRepo;
    }

    @Override
    public List<Postcode> getPostcodes(){
        List<Postcode> postcodes = new ArrayList<>();
        postcodeRepo.findAll().forEach(postcodes::add);

        return postcodes;
    }

    @Override
    public Postcode findPostcodeByPostcode(String postcode){
        Postcode obj = postcodeRepo.findPostcodeByPostcode(postcode);
        if(obj == null){
            LOGGER.info("Cannot find record with postcode = " + postcode);
        }
        return obj;
    }

    @Override
    public Postcode insert(Postcode postcode) {
        return postcodeRepo.save(postcode);
    }
}
