package com.geopostal.ukpostal.controllers;

import com.geopostal.ukpostal.model.Postcode;
import com.geopostal.ukpostal.model.enumerations.StatusCode;
import com.geopostal.ukpostal.services.PostcodeService;
import com.geopostal.ukpostal.utils.GeoDistanceUtil;
import com.geopostal.ukpostal.viewmodels.DistanceResultVM;
import com.geopostal.ukpostal.viewmodels.GeoDistanceVM;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/geo")
public class GeoDistanceController {

    private final static Logger LOGGER = LoggerFactory.getLogger(GeoDistanceController.class);

    PostcodeService postcodeService;

    public GeoDistanceController(PostcodeService postcodeService){
        this.postcodeService = postcodeService;
    }

    // <editor-fold defaultstate="collapsed" desc="(/api/geo) To List all Available UK Postcode ">
    @GetMapping
    public ResponseEntity<List<Postcode>> getAllPostcode(){
        List<Postcode> postcodes = postcodeService.getPostcodes();
        return new ResponseEntity<>(postcodes, HttpStatus.OK);
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="(calculate/distance) This section is the process to calculate the distance between Postcode">
    @PostMapping("calculate/distance")
    public ResponseEntity<GeoDistanceVM> calculateDistance(@RequestParam String... postcodes){
        LOGGER.info("Request {calculate/distance} called with Params {" + Arrays.toString(postcodes) + "}");
        int warnCount = 0;
        if(postcodes == null || Arrays.asList(postcodes).size() < 2){
            LOGGER.error("Required at least two valid postcode to calculate Distance");
            GeoDistanceVM vm = GeoDistanceVM.builder()
                    .status(StatusCode.ERROR)
                    .statusMessage("Parameter Error: Required at least two valid postcode to calculate Distance")
                    .build();
            return new ResponseEntity<>(vm, HttpStatus.BAD_REQUEST);
        }

        List<Postcode> postcodeList = new ArrayList<>();
        //Search Postcode for every input postcode
        for(String s : postcodes){
            Postcode postcodeModel = this.postcodeService.findPostcodeByPostcode(s);
            if(postcodeModel != null){
                postcodeList.add(postcodeModel);
            }else{
                warnCount++;
            }
        }

        //Return 422 Status code, because we cannot process or calculate with only 1 postcode coordinate
        if(postcodeList.size() < 1){
            LOGGER.error("Required at least two valid postcode to calculate Distance");
            GeoDistanceVM vm = GeoDistanceVM.builder()
                    .status(StatusCode.ERROR)
                    .statusMessage("Parameter Value Error : Required at least two valid postcode to calculate Distance")
                    .build();
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        List<DistanceResultVM> distanceResults = new ArrayList<>();
        for(int i = 0; i < postcodeList.size(); i++){
            String message = "";
            Postcode p1 = postcodeList.get(i);
            //Return the first element if (i + 1) > list size else return element (index + 1);
            //If input postcode > 2 then the last postcode will calculate the distance between the 1st postcode;
            Postcode p2 = (i + 1) == postcodeList.size()? postcodeList.get(0) : postcodeList.get(i + 1);

            List<Postcode> postLst = new ArrayList<>();
            postLst.add(p1);
            postLst.add(p2);

            Double distance = null;
            String wktString = null;

            //Checking if any lat/long from Postcode is null then increase the count for every record error encounter
            //Because we don't want to abort the process with the null value here
            if(p1.getLatitude() == null || p1.getLongitude() == null
                    || p2.getLatitude() == null || p2.getLongitude() == null){

                LOGGER.warn("NULL value for Lat/Long in Postcode");
                warnCount++;
                message += "NULL value for Lat/Long in Postcode";
            }else{
                //Generate the coordinate to gain the WKT String to populate into the World Map
                Coordinate[] jtsCoordinate = {new Coordinate(p1.getLongitude(), p1.getLatitude()),
                        new Coordinate(p2.getLongitude(), p2.getLatitude())};
                try {
                    LineString lineString = new GeometryFactory().createLineString(jtsCoordinate);
                    if(lineString.isValid()){
                        wktString = lineString.toString();
                    }
                }catch (IllegalArgumentException e){
                    warnCount++;
                    LOGGER.warn(e.getMessage(), e);
                    message += e.getMessage();
                }

                distance = GeoDistanceUtil.calculateDistance(p1.getLatitude(), p1.getLongitude(),
                        p2.getLatitude(), p2.getLongitude());

            }

            DistanceResultVM distanceResultVM = DistanceResultVM.builder()
                    .distance(distance)
                    .postcodes(postLst)
                    .generatedWKTString(wktString)
                    .build();

            distanceResultVM.setStatus(message.length() > 1? StatusCode.WARN : StatusCode.OK);
            distanceResultVM.setStatusMessage(message);

            distanceResults.add(distanceResultVM);

            if(postcodeList.size() == 2){break;}
        }

        GeoDistanceVM vm = GeoDistanceVM.builder()
                .distanceResults(distanceResults)
                .build();

        vm.setStatus(warnCount > 0? StatusCode.WARN : StatusCode.OK);
        vm.setStatusMessage(warnCount > 0? "Request return with warning please check the data for more info" : "OK");

        return new ResponseEntity<>(vm, HttpStatus.OK);
    }
    // </editor-fold>
}
