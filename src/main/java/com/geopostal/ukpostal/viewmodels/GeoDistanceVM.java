package com.geopostal.ukpostal.viewmodels;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GeoDistanceVM extends BaseVM{
    List<DistanceResultVM> distanceResults;
}
