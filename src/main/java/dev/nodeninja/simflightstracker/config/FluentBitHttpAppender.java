package dev.nodeninja.simflightstracker.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class FluentBitHttpAppender extends AppenderBase<ILoggingEvent> {

    private RestTemplate restTemplate;
    private String fluentBitUrl;

    @Override
    public void start() {
        restTemplate = new RestTemplate();
        fluentBitUrl = "https://observability.simflightstracker.com/logs/submit";
        super.start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        try {
            String logTag = "spring-logs";
            String payload = getPayload(eventObject, logTag);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(payload, headers);
            var res = restTemplate.postForEntity(fluentBitUrl, request, String.class);
            System.out.println(res.getStatusCode());

        } catch (Exception e) {
            addError("Failed to send log to Fluent Bit", e);
        }
    }

    private String getPayload(ILoggingEvent eventObject, String logTag) {
        String message = eventObject.getFormattedMessage();
        String level = eventObject.getLevel().toString();
        String loggerName = eventObject.getLoggerName();
        long timestamp = eventObject.getTimeStamp();

        return String.format(
                "{\"tag\": \"%s\", \"time\": %d, \"level\": \"%s\", \"logger\": \"%s\", \"message\": \"%s\"}",
                logTag,
                timestamp,
                escapeJson(level),
                escapeJson(loggerName),
                escapeJson(message)
        );
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "\\\"");
    }
}
