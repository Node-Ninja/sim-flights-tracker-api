package dev.nodeninja.simflightstracker.util;

import dev.nodeninja.simflightstracker.tracker.model.ControllerType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class GenericUtils {

    /**
     * Map controller callsign code to a controller type;
     * @param callsign Controller callsign;
     * @return ControllerType
     */
    public static ControllerType mapControllerType(String callsign) {
        ControllerType controllerType = ControllerType.UNKNOWN;

        if (StringUtils.contains(callsign, "_GND")) {
            controllerType = ControllerType.GROUND;
        }
        if (StringUtils.contains(callsign, "_DEL")) {
            controllerType = ControllerType.DELIVERY;
        }
        if (StringUtils.contains(callsign, "_TWR")) {
            controllerType = ControllerType.TOWER;
        }
        if (StringUtils.contains(callsign, "_DEP")) {
            controllerType = ControllerType.DEPARTURE;
        }
        if (StringUtils.contains(callsign, "_APP")) {
            controllerType = ControllerType.APPROACH;
        }
        if (StringUtils.contains(callsign, "_CTR")) {
            controllerType = ControllerType.CENTER;
        }
        if (StringUtils.contains(callsign, "_OBS")) {
            controllerType = ControllerType.OBSERVER;
        }
        if (StringUtils.contains(callsign, "_FSS")) {
            controllerType = ControllerType.FLIGHT_SERVICE;
        }

        return controllerType;
    }
}
