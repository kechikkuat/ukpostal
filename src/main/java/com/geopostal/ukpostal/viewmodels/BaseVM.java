package com.geopostal.ukpostal.viewmodels;

import com.geopostal.ukpostal.model.enumerations.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseVM {
    StatusCode status;
    String statusMessage;
}
