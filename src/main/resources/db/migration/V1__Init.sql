CREATE TABLE flights (
                         cid BIGINT NOT NULL,
                         name VARCHAR(255),
                         callSign VARCHAR(50),
                         server VARCHAR(50),
                         pilotRating INT,
                         militaryRating INT,
                         transponder VARCHAR(10),
                         heading INT,
                         qnhIHg DOUBLE PRECISION,
                         qnhMb INT,
                         logonTime TIMESTAMP WITH TIME ZONE,
                         lastUpdated TIMESTAMP WITH TIME ZONE,
                         flightPlanId BIGINT,
                         PRIMARY KEY (cid, callSign, logonTime)
);

CREATE TABLE flightPlans (
                             id SERIAL PRIMARY KEY,
                             flightIdCid BIGINT NOT NULL,
                             flightIdCallSign VARCHAR(50) NOT NULL,
                             flightIdLogonTime TIMESTAMP WITH TIME ZONE NOT NULL,
                             flightRules CHAR(1),
                             aircraft TEXT,
                             aircraftFaa TEXT,
                             aircraftShort VARCHAR(50),
                             departure VARCHAR(10),
                             arrival VARCHAR(10),
                             alternate VARCHAR(10),
                             cruiseTas VARCHAR(10),
                             altitude VARCHAR(10),
                             depTime VARCHAR(10),
                             enrouteTime VARCHAR(10),
                             fuelTime VARCHAR(10),
                             remarks TEXT,
                             route TEXT,
                             revisionId INT,
                             assignedTransponder VARCHAR(10)
);

CREATE TABLE flightPaths (
                             id SERIAL PRIMARY KEY,
                             flightIdCid BIGINT NOT NULL,
                             flightIdCallSign VARCHAR(50) NOT NULL,
                             flightIdLogonTime TIMESTAMP WITH TIME ZONE NOT NULL,
                             latitude DECIMAL(9,6) NOT NULL,
                             longitude DECIMAL(9,6) NOT NULL,
                             altitude INT,
                             groundSpeed INT,
                             timeStamp TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

ALTER TABLE flightPlans ADD CONSTRAINT fk_flight FOREIGN KEY (flightIdCid, flightIdCallSign, flightIdLogonTime)
    REFERENCES flights (cid, callSign, logonTime) ON DELETE CASCADE;

ALTER TABLE flightPaths ADD CONSTRAINT fk_flightPath FOREIGN KEY (flightIdCid, flightIdCallSign, flightIdLogonTime)
    REFERENCES flights (cid, callSign, logonTime) ON DELETE CASCADE;