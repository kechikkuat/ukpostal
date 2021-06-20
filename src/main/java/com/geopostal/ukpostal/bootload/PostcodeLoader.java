package com.geopostal.ukpostal.bootload;

import com.geopostal.ukpostal.model.Postcode;
import com.geopostal.ukpostal.repositories.PostcodeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@Component
public class PostcodeLoader implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(PostcodeLoader.class);
    public final PostcodeRepo postcodeRepo;

    public PostcodeLoader(PostcodeRepo postcodeRepo){
        this.postcodeRepo = postcodeRepo;
    }

    @Override
    public void run(String... args)throws Exception{
        LOGGER.info("Load data from CSV to db. This might take a while, please be patient");
        loadPostcode();
        LOGGER.info("Data Loaded");
    }

    private void loadPostcode(){
        if(postcodeRepo.count() == 0){
            Resource resource = new ClassPathResource("ukpostcodes.csv");

            try {
                File file = resource.getFile();
                if(file.exists()){
                    String line = "";
                    String delimiter = ",";

                    BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
                    br.readLine();
                    while ((line = br.readLine()) != null){
                        String[] post = line.split(delimiter);
                        try {
                            postcodeRepo.save(Postcode.builder()
                                    .migrationId(Long.parseLong(post[0]))
                                    .postcode(post[1])
                                    .latitude(post.length > 2? Double.parseDouble(post[2]) : null)
                                    .longitude(post.length > 3? Double.parseDouble(post[3]) : null)
                                    .build());
                        }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
                            LOGGER.error("Error when saving data to DB : " + line);
                            LOGGER.error(e.getMessage(), e);
                        }

                    }
                }
            }catch (IOException e){
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
