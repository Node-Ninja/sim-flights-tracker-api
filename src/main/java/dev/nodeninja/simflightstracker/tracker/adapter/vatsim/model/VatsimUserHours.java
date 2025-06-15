package dev.nodeninja.simflightstracker.tracker.adapter.vatsim.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatsimUserHours {
    private int id;
    private int atc;
    private int pilot;
    private int admin;
    private int sup;
}
