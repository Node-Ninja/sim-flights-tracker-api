package dev.nodeninja.simflightstracker.api.v2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VatsimATCHistoryItem {
    @JsonAlias("connection_id")
    private HistoryItemConnection connection;
    private Integer aircrafttracked;
    private Integer aircraftseen;
    private Integer flightsamended;
    private Integer handoffsinitiated;
    private Integer handoffsreceived;
    private Integer handoffsrefused;
    private Integer squawksassigned;
    private Integer cruisealtsmodified;
    private Integer tempaltsmodified;
    private Integer scratchpadmods;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class HistoryItemConnection {
        private Integer id;
        private String vatsim_id;
        private Integer type;
        private Integer rating;
        private String callsign;
        private String start;
        private String end;
        private String server;
    }
}