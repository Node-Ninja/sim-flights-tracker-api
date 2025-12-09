package dev.nodeninja.simflightstracker.tracker.mapper;

import dev.nodeninja.simflightstracker.api.v2.model.*;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.AircraftDetailsResponse;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.IvaoAtc;
import dev.nodeninja.simflightstracker.tracker.adapter.ivao.model.IvaoFlight;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimAtc;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimFlight;
import dev.nodeninja.simflightstracker.util.GenericUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrackerMapper {
    public Flight vatsimFlightToGeneric(VatsimFlight flight) {
        var flightPlan = flight.getFlightPlan() != null
                ? FlightPlan.builder()
                .departure(flight.getFlightPlan().getDeparture())
                .arrival(flight.getFlightPlan().getArrival())
                .alternate(flight.getFlightPlan().getAlternate())
                .altitude(flight.getFlightPlan().getAltitude())
                .aircraftFaa(flight.getFlightPlan().getAircraftFaa())
                .aircraftShort(flight.getFlightPlan().getAircraftShort())
                .route(flight.getFlightPlan().getRoute())
                .build()
                : null;

        return Flight.builder()
                .id(flight.getCid())
                .pilotName(flight.getName())
                .callsign(flight.getCallsign())
                .latitude(flight.getLatitude())
                .longitude(flight.getLongitude())
                .altitude(flight.getAltitude())
                .heading(flight.getHeading())
                .groundSpeed(flight.getGroundspeed())
                .transponder(flight.getTransponder())
                .flightPlan(flightPlan)
                .build();
    }
    
    public AirTrafficController vatsimControllerToGeneric(VatsimAtc controller) {
        return AirTrafficController.builder()
                .id(controller.getCid())
                .name(controller.getName())
                .frequency(controller.getFrequency())
                .facility(controller.getFacility())
                .rating(controller.getRating())
                .callsign(controller.getCallsign())
                .textAtis(controller.getTextAtis())
                .logonTime(controller.getLogonTime())
                .connectionType(null)
                .atcPosition(null)
                .build();
    }

    public EventSummary vatsimEventToSummary(VatsimEvent event) {
        return EventSummary.builder()
                .name(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .id(event.getId())
                .poster(event.getBanner())
                .build();
    }

    public AtcSummary vatsimAtcToSummary(VatsimAtc controller) {
        return AtcSummary.builder()
                .callsign(controller.getCallsign())
                .frequency(controller.getFrequency())
                .facility(GenericUtils.mapControllerType(controller.getCallsign()))
                .latitude(null)
                .longitude(null)
                .build();
    }

    public FlightSummary vatsimFlightToSummary(VatsimFlight flight) {
        var flightPlanSummary = flight.getFlightPlan() != null
                ? FlightPlanSummary.builder()
                .departure(flight.getFlightPlan().getDeparture())
                .arrival(flight.getFlightPlan().getArrival())
                .build()
                : null;

        return FlightSummary.builder()
                .callsign(flight.getCallsign())
                .latitude(flight.getLatitude())
                .longitude(flight.getLongitude())
                .groundspeed(flight.getGroundspeed())
                .heading(flight.getHeading())
                .altitude(flight.getAltitude())
                .flightPlan(flightPlanSummary)
                .build();
    }

    public FlightSummary ivaoFlightToSummary(IvaoFlight flight) {
        var hasFlightPlan = flight.getFlightPlan() != null;
        var hasDepartureArrival = hasFlightPlan && flight.getFlightPlan().getArrival() != null && flight.getFlightPlan().getDeparture() != null;
        var hasLastTrack = flight.getLastTrack() != null;

        var flightPlanSummary = hasFlightPlan && hasDepartureArrival
                ? FlightPlanSummary.builder()
                .arrival(flight.getFlightPlan().getArrival().getIcao())
                .departure(flight.getFlightPlan().getDeparture().getIcao())
                .build()
                : null;

        return FlightSummary.builder()
                .callsign(flight.getCallsign())
                .latitude(hasLastTrack ? flight.getLastTrack().getLatitude() : 0.0)
                .longitude(hasLastTrack ? flight.getLastTrack().getLongitude() : 0.0)
                .altitude(hasLastTrack ? flight.getLastTrack().getAltitude() : 0)
                .groundspeed(hasLastTrack ? flight.getLastTrack().getGroundSpeed() : 0)
                .heading(hasLastTrack ? flight.getLastTrack().getHeading() : 0)
                .flightPlan(flightPlanSummary)
                .build();
    }

    public AtcSummary ivaoAtcToSummary(IvaoAtc atc) {
        Double lat;
        Double lng;

        if (atc.getAtcPosition() != null) {
            lat = atc.getAtcPosition().getAirport().getLatitude();
            lng = atc.getAtcPosition().getAirport().getLongitude();
        } else {
            lat = null;
            lng = null;
        }

        return AtcSummary.builder()
                .callsign(atc.getCallsign())
                .frequency(atc.getAtcSession().getFrequency().toString())
                .facility(GenericUtils.mapControllerType(atc.getCallsign()))
                .latitude(lat)
                .longitude(lng)
                .build();
    }

    public Flight ivaoFlightToGeneric(IvaoFlight flight) {
        var flightPlan = (flight.getFlightPlan() != null && flight.getFlightPlan().getDeparture() != null)
                ? FlightPlan.builder()
                .departure(flight.getFlightPlan().getDepartureId())
                .arrival(flight.getFlightPlan().getArrivalId())
                .alternate("")
                .altitude(flight.getLastTrack().getAltitude().toString())
                .aircraftFaa("")
                .aircraftShort(flight.getFlightPlan().getAircraftId())
                .route("")
                .build()
                : null;

        return Flight.builder()
                .id(flight.getId())
                .pilotName("")
                .callsign(flight.getCallsign())
                .latitude(flight.getLastTrack().getLatitude())
                .longitude(flight.getLastTrack().getLongitude())
                .altitude(flight.getLastTrack().getAltitude())
                .heading(flight.getLastTrack().getHeading())
                .groundSpeed(flight.getLastTrack().getGroundSpeed())
                .transponder(flight.getLastTrack().getTransponder().toString())
                .flightPlan(flightPlan)
                .build();
    }

    public AirTrafficController ivaoControllerToGeneric(IvaoAtc controller) {
        return AirTrafficController.builder()
                .id(controller.getId())
                .name("")
                .frequency(controller.getAtcSession().getFrequency().toString())
                .facility(100)
                .callsign(controller.getCallsign())
                .connectionType(controller.getConnectionType())
                .atcPosition(controller.getAtcPosition())
                .build();
    }

    public Aircraft ivaoAircraftToGeneric(AircraftDetailsResponse aircraft) {
        return Aircraft.builder()
                .manufacture(aircraft.getManufacture().getName())
                .icaoCode(aircraft.getIcaoCode())
                .iataCode(aircraft.getIataCode())
                .description(aircraft.getDescription())
                .model(aircraft.getModel())
                .wakeTurbulence(aircraft.getWakeTurbulence())
                .numberEngines(aircraft.getNumberEngines())
                .isMilitary(false)
                .build();
    }

    public EventSummary ivaoEventToSummary(IvaoEvent event) {
        return EventSummary.builder()
                .name(event.getTitle())
                .startTime(event.getStartDate())
                .endTime(event.getEndDate())
                .id(event.getId())
                .poster(event.getImageUrl())
                .build();
    }

//    public EventDetails ivaoEventToDetails(IvaoEvent event) {}
    public EventDetails ivaoEventToDetails(IvaoEvent event) {
        return EventDetails.builder()
                .id(event.getId())
                .type(event.getEventType())
                .name(event.getTitle())
                .link(event.getInfoUrl())
                .startTime(event.getStartDate())
                .endTime(event.getEndDate())
                .shortDescription(event.getDescription())
                .description(event.getDescription())
                .banner(event.getImageUrl())
                .organisers(List.of())
                .routes(List.of())
                .airports(event.getAirports().stream().map(
                        airport -> EventDetails.EventAirport.builder()
                                .icao(airport)
                                .build()
                ).collect(Collectors.toList()))
                .build();

    }
}
