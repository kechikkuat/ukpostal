package com.geopostal.ukpostal.services.postcodes;

import com.geopostal.ukpostal.exceptions.PostcodeNotFoundException;
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
    public Postcode findById(Long id){
        Postcode obj = postcodeRepo.findById(id).orElse(null);
        if(obj == null){
            LOGGER.info("Cannot find record with id = " + id);
        }
        return obj;
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

    @Override
    public Postcode update(Long postcodeId, Postcode postcode) {
        Postcode postcodeFromDb = findById(postcodeId);
        if(postcodeFromDb != null){
            postcodeFromDb.setPostcode(postcode.getPostcode());
            postcodeFromDb.setLatitude(postcode.getLatitude());
            postcodeFromDb.setLongitude(postcode.getLongitude());

            return postcodeRepo.save(postcode);
        }else{
            throw new PostcodeNotFoundException(postcodeId);
        }
    }
}
