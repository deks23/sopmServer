package pl.sopmproject.sopmserver.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSurveysFromNeighborhoodRequest {
    private double radius;
    private double longitude;
    private double latitude;
}
