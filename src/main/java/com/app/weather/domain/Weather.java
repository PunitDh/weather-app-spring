package com.app.weather.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    public double lat;
    public double lon;
    public String timezone;
    public String timezone_offset;
    public WeatherDetails current;
    public List<MinutelyDetails> minutely;
    public WeatherDetails hourly;
    public WeatherDetails daily;
    public List<WeatherAlert> alerts;

    public static class MinutelyDetails {
        public double dt;
        public int precipitation;
    }

    public static class WeatherDetails {
        public double dt;
        public double sunrise;
        public double sunset;
        public double temp;
        public double feels_like;
        public double pressure;
        public double humidity;
        public double dew_point;
        public int clouds;
        public int visibility;
        public int wind_deg;
        public List<WeatherDescription> weather;

        public static class WeatherDescription {
            public int id;
            public String main;
            public String description;
            public String icon;
        }
    }

    public static class WeatherAlert {
        public String sender_name;
        public String event;
        public double start;
        public double end;
        public String description;
        public List<String> tags;
    }
}
