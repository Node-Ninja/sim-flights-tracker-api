package dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightPlanHistoryItem {
    private Integer id;
    @JsonAlias("connection_id")
    private Integer connectionId;
    @JsonAlias("vatsim_id")
    private String vatsimId;
    @JsonAlias("flight_type")
    private String flightType;
    @JsonAlias("callsign")
    private String callSign;
    private String aircraft;
    @JsonAlias("cruisespeed")
    private String cruiseSpeed;
    @JsonAlias("dep")
    private String departure;
    @JsonAlias("arr")
    private String arrival;
    @JsonAlias("alt")
    private String alternate;
    private String altitude;
    @JsonAlias("rmks")
    private String remarks;
    private String route;
    @JsonAlias("deptime")
    private String departureTime;
    @JsonAlias("hrsenroute")
    private Integer hoursEnRoute;
    @JsonAlias("minenroute")
    private Integer minutesEnRoute;
    @JsonAlias("hrsfuel")
    private Integer hoursFuel;
    @JsonAlias("minsfuel")
    private Integer minutesFuel;
    private String filed;
    @JsonAlias("assignedsquawk")
    private String assignedSquawk;
}
