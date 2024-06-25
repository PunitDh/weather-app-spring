package com.app.weather.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    public BigDecimal lat;
    public BigDecimal lon;
    public String timezone;
    public int timezone_offset;
    public WeatherDetails current;
    public List<MinutelyDetails> minutely;
    public List<WeatherDetails> hourly;
    public List<DailyWeatherDetails> daily;
    public List<WeatherAlert> alerts;

    @JsonSerialize
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MinutelyDetails {
        public double dt;
        public int precipitation;
    }

    @JsonSerialize
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DailyWeatherDetails extends WeatherDetails {
        public Temperature temp;
        public FeelsLike feels_like;
        public double moonrise;
        public double moonset;
        public double moon_phase;
        public String summary;
        public double pop;
        public double rain;

        @JsonSerialize
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Temperature {
            public BigDecimal day;
            public BigDecimal min;
            public BigDecimal max;
            public BigDecimal night;
            public BigDecimal eve;
            public BigDecimal morn;
        }

        @JsonSerialize
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class FeelsLike {
            public BigDecimal day;
            public BigDecimal night;
            public BigDecimal eve;
            public BigDecimal morn;
        }
    }

    @JsonSerialize
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherDetails {
        public double dt;
        public double sunrise;
        public double sunset;
        public BigDecimal temp;
        public BigDecimal feels_like;
        public double pressure;
        public double humidity;
        public BigDecimal dew_point;
        public int clouds;
        public int visibility;
        public double wind_speed;
        public int wind_deg;
        public double wind_gust;
        public double uvi;
        public List<WeatherDescription> weather;

        @JsonSerialize
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class WeatherDescription {
            public int id;
            public String main;
            public String description;
            public String icon;
        }
    }

    @JsonSerialize
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherAlert {
        public String sender_name;
        public String event;
        public double start;
        public double end;
        public String description;
        public List<String> tags;
    }
}
