package com.geopostal.ukpostal.viewmodels;

import com.geopostal.ukpostal.model.Postcode;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DistanceResultVM extends BaseVM{
    final String unit = "KM";

    List<Postcode> postcodes;
    Double distance;
    String generatedWKTString;
}
