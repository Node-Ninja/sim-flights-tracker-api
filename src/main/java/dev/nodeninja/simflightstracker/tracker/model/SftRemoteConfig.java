package dev.nodeninja.simflightstracker.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SftRemoteConfig {
    private String appVersion;
    private boolean hasNotice;
    private String noticeType;
    private String noticeMessage;
    private boolean isInMaintenance;
}
