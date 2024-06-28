package dev.nodeninja.simflightstracker.api.v2.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "airports")
public class Airport {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String ident;
    private String type;
    private String name;
    private String latitude_deg;
    private String longitude_deg;
    private String elevation_ft;
    private String continent;
    private String iso_country;
    private String iso_region;
    private String municipality;
    private String scheduled_service;
    private String gps_code;
    private String iata_code;
    private String local_code;
    private String home_link;
    private String wikipedia_link;
    private String keywords;
    private AirportRegion region;
    private AirportCountry country;
    private List<AirportFrequency> freqs;
    private List<AirportRunway> runways;
    private List<AirportNavigationAid> navaids;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AirportRegion {
        @Field("id")
        private String id;
        private String code;
        private String local_code;
        private String name;
        private String continent;
        private String iso_country;
        private String wikipedia_link;
        private String keywords;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AirportCountry {
        @Field("id")
        private String id;
        private String code;
        private String name;
        private String continent;
        private String wikipedia_link;
        private String keywords;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AirportFrequency {
        @Field("id")
        private String id;
        private String airport_ref;
        private String airport_ident;
        private String type;
        private String description;
        private String frequency_mhz;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AirportRunway {
        @Field("id")
        private String id;
        private String airport_ref;
        private String airport_ident;
        private String length_ft;
        private String width_ft;
        private String surface;
        private String lighted;
        private String closed;
        private String le_ident;
        private String le_latitude_deg;
        private String le_longitude_deg;
        private String le_elevation_ft;
        private String le_heading_degT;
        private String le_displaced_threshold_ft;
        private String he_ident;
        private String he_latitude_deg;
        private String he_longitude_deg;
        private String he_elevation_ft;
        private String he_heading_degT;
        private String he_displaced_threshold_ft;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AirportNavigationAid {
        @Field("id")
        private String id;
        private String filename;
        private String ident;
        private String name;
        private String type;
        private String frequency_khz;
        private String latitude_deg;
        private String longitude_deg;
        private String elevation_ft;
        private String iso_country;
        private String dme_frequency_khz;
        private String dme_channel;
        private String dme_latitude_deg;
        private String dme_longitude_deg;
        private String dme_elevation_ft;
        private String slaved_variation_deg;
        private String magnetic_variation_deg;
        private String usageType;
        private String power;
        private String associated_airport;
    }
}
