package dev.nodeninja.simflightstracker.tracker.service.impl;

import com.github.f4b6a3.ulid.UlidCreator;
import dev.nodeninja.simflightstracker.api.v2.model.*;
import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import dev.nodeninja.simflightstracker.exceptions.BusinessException;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.FlightPlanHistoryItem;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimFlightsHistory;
import dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model.VatsimUserHours;
import dev.nodeninja.simflightstracker.tracker.component.VatsimLiveDataCache;
import dev.nodeninja.simflightstracker.tracker.external.VatsimClient;
import dev.nodeninja.simflightstracker.tracker.mapper.TrackerMapper;
import dev.nodeninja.simflightstracker.tracker.model.AuthRecord;
import dev.nodeninja.simflightstracker.tracker.model.GrantType;
import dev.nodeninja.simflightstracker.tracker.model.VatsimTokenResponse;
import dev.nodeninja.simflightstracker.tracker.repository.AuthRepository;
import dev.nodeninja.simflightstracker.tracker.repository.FlightTracksRepository;
import dev.nodeninja.simflightstracker.tracker.service.VatsimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VatsimServiceImpl implements VatsimService {
    private final TrackerMapper mapper;
    private final VatsimClient vatsimClient;
    private final VatsimLiveDataCache vatsimLiveDataCache;
    private final FlightTracksRepository flightTracksRepository;
    private final AuthRepository authRepository;

    private final ApplicationConfigProperties configProps;


    @Override
    public VatsimLiveData vatsimLiveData() {
        try {
            var response = vatsimLiveDataCache.getVatsimLiveData();

            return VatsimLiveData.builder()
                    .flights(response.getPilots().stream().map(mapper::vatsimFlightToSummary).collect(Collectors.toList()))
                    .controllers(response.getControllers().stream().map(mapper::vatsimAtcToSummary).collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Vatsim live data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public Flight flightDetails(String callSign) {
        try {
            var response = vatsimLiveDataCache.getVatsimLiveData();

            var flights = response.getPilots();
            var foundFlight = flights.stream()
                    .filter(flight -> callSign.equals(flight.getCallsign()))
                    .findFirst()
                    .orElse(null);

            if (foundFlight == null) { return null; }

            return mapper.vatsimFlightToGeneric(foundFlight);
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Vatsim flight data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public AirTrafficController atcDetails(String callSign) {
        try {
            var response = vatsimLiveDataCache.getVatsimLiveData();

            var controllers = response.getControllers();

            var foundController = controllers.stream().filter(controller -> controller.getCallsign().equals(callSign)).findFirst().orElse(null);

            if (foundController == null) { return null; }

            return mapper.vatsimControllerToGeneric(foundController);
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Vatsim controller data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public String metarByAirportIcaoId(String icaoId) {
        try {
            URI endpoint = URI.create(configProps.getVatsim().getHost().getMetar()  + String.format("/%s", icaoId));

            return vatsimClient.metarByIcaoId(endpoint);
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Vatsim metar data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public List<EventSummary> events() {
        URI endpoint = URI.create(configProps.getVatsim().getHost().getV2() + "/events/latest");

        try {
            var response = vatsimClient.getAllEvents(endpoint);
            return response.getData().stream().map(mapper::vatsimEventToSummary).toList();
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Vatsim events data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public VatsimEvent eventDetails(String eventId) {
        URI endpoint = URI.create(configProps.getVatsim().getHost().getV2() + String.format("/events/view/%s", eventId));

        try {
            var response = vatsimClient.eventDetails(endpoint);

            return response.getData();
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Vatsim event data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public List<VatsimTransceiver> transceivers() {
        try {
            var allowedStations = List.of("DEL", "GND", "TWR", "APP", "DEP", "CTR");

            var response = vatsimLiveDataCache.getVatsimTransceivers();

            return response
                    .stream()
                    .filter(ts1 -> allowedStations.contains(
                            StringUtils.right(
                                    ts1.getCallsign(),
                                    3))).toList();
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Vatsim transceivers data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public VatsimFlightsHistory flightsHistory(String vatsimId, Integer offset) {
        URI endpoint = URI.create(configProps.getVatsim().getHost().getCore()  + "/members/" + vatsimId + "/history?limit=" + 50);

        try {
            return vatsimClient.getFlightsHistory(endpoint);
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Vatsim flight history data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public List<FlightPlanHistoryItem> flightPlanHistory(String vatsimId) {
        URI endpoint = URI.create(configProps.getVatsim().getHost().getCore()  + "/members/" + vatsimId + "/flightplans");

        try {
            return vatsimClient.flightPlanHistory(endpoint);
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Vatsim flight plan history data",
                    "LIVE_DATA_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public FlightTrack getFlightTrack(String callsign) {
        return flightTracksRepository.findByCallsign(callsign);
    }

    @Override
    public VatsimUserHours getUserHours(String vatsimId) {
        URI endpoint = URI.create(configProps.getVatsim().getHost().getCore() + "/members/" + vatsimId + "/stats");

        try {
            return vatsimClient.getUserHours(endpoint);
        } catch (Exception e) {
            throw new BusinessException(
                    "Could not get Vatsim stats",
                    "VATSIM_STATS_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public String startAuth(String simNetwork) {
        //  add auth record to and then send back auth ID;
        var authId = UlidCreator.getUlid();

        var record = AuthRecord.builder()
                .authId(authId.toString())
                .simNetwork(simNetwork)
                .build();

        var createdAuth = authRepository.save(record);

        return createdAuth.getAuthId();
    }

    @Override
    public boolean patchAuth(String authCode, String state) {
        try {
            //  try to find auth record by state;
            var foundRecord = authRepository.findByAuthId(state);

            if (foundRecord == null) { return false; }

            var response = getToken(GrantType.authorization_code, authCode);

            if (response == null) { return false; }

            var record = patchRecord(foundRecord, response);

            authRepository.save(record);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public AuthedUserDetails getAuthedUserDetails(String authId) {
        try {
            var authToken = "";

            var now = Instant.now();
            var authRecord = authRepository.findByAuthId(authId);

            if (authRecord == null) { return null; }

            var token = authRecord.getToken();

            if (token == null) { return null; }

            authToken = token;

            if (now.isAfter(authRecord.getExpiryDate())) {
                if (now.isAfter(authRecord.getRefreshExpiryDate())) {
                    authRepository.deleteByAuthId(authId);
                    return null;
                }

                //  refresh access token;
                var tokenResponse = getToken(GrantType.refresh_token, authRecord.getRefresh());
                if (tokenResponse == null) { return null; }

                authToken = tokenResponse.getAccessToken();

                //  patch auth record;
                var record = patchRecord(authRecord, tokenResponse);

                authRepository.save(record);
            }

            var headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + authToken);

            var endpoint =  URI.create(configProps.getVatsim().getOAuth().getUserDetails());

            var response = vatsimClient.getUserDetails(endpoint, headers);

            return AuthedUserDetails.builder()
                    .cid(response.getData().getCid())
                    .fullName(response.getData().getPersonal().getNameFull())
                    .firstName(response.getData().getPersonal().getNameFirst())
                    .lastName(response.getData().getPersonal().getNameLast())
                    .email(response.getData().getPersonal().getEmail())
                    .country(response.getData().getPersonal().getCountry().getName())
                    .countryCode(response.getData().getPersonal().getCountry().getId())
                    .controllerRating(response.getData().getVatsim().getRating().getLongName())
                    .pilotRating(response.getData().getVatsim().getPilotrating().getLongName())
                    .division(response.getData().getVatsim().getDivision().getName())
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void destroyVatsimRecord(String authId) {
        try {
            if (StringUtils.isNotBlank(authId)) {
                authRepository.deleteByAuthId(authId);
                log.info("Removed record with authId: {}", authId);
            }
        } catch (Exception e) {
            log.error("Could not remove record with authId: {}", authId);
        }
    }

    @Override
    public VatsimATCHistory atcHistory(String vatsimId) {
        URI endpoint = URI.create(configProps.getVatsim().getHost().getCore() + "/members/" + vatsimId + "/atc");

        try {
            return vatsimClient.getATCHistory(endpoint);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(
                    "Could get atc history",
                    "VATSIM_ATC_HISTORY_ERROR",
                    HttpStatus.SC_BAD_REQUEST);
        }
    }

    private VatsimTokenResponse getToken(GrantType grantType, String code) {
        try {
            var endpoint = URI.create(configProps.getVatsim().getOAuth().getTokenUri());
            var authRequest = new LinkedMultiValueMap<String, String>();

            authRequest.add("client_id", configProps.getVatsim().getOAuth().getClientId());
            authRequest.add("client_secret", configProps.getVatsim().getOAuth().getClientSecret());
            authRequest.add("grant_type", grantType.toString());
            authRequest.add("redirect_uri", configProps.getVatsim().getOAuth().getRedirectUri());

            switch (grantType) {
                case authorization_code: authRequest.add("code", code);
                break;
                case refresh_token: authRequest.add("refresh_token", code);
                break;
            }

            return vatsimClient.exchangeCodeForToken(
                    endpoint,
                    authRequest
            );
        } catch (Exception e){
            return null;
        }
    }

    private AuthRecord patchRecord(AuthRecord authRecord, VatsimTokenResponse response) {
        var now = Instant.now();
        var tokenExpiry = now.plusSeconds(response.getExpiresIn() - 120);
        var refreshExpiry = now.plus(29, ChronoUnit.DAYS);

        //  patch record with new data;
        authRecord.setToken(response.getAccessToken());
        authRecord.setRefresh(response.getRefreshToken());
        authRecord.setExpiresIn(response.getExpiresIn());
        authRecord.setType(response.getTokenType());
        authRecord.setExpiryDate(tokenExpiry);
        authRecord.setRefreshExpiryDate(refreshExpiry);

        return authRecord;
    }
}
