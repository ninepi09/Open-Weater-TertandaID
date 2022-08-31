package com.tertandaid.openweather;

public class Constants {
  public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
  public static final String UNITS = "metric";
  public static final String[] DAYS_OF_WEEK = {
      "Sunday",
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday"
  };

  public static final String PACKAGE_NAME = "com.tertandaid.openweather";
  static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
  static final String RECEVIER = PACKAGE_NAME + ".RECEVIER";
  static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

  static final String ADDRESS = PACKAGE_NAME + ".ADDRESS";
  static final String LOCAITY = PACKAGE_NAME + ".LOCAITY";
  static final String COUNTRY = PACKAGE_NAME + ".COUNTRY";
  static final String DISTRICT = PACKAGE_NAME + ".DISTRICT";
  static final String POST_CODE = PACKAGE_NAME + ".POST_CODE";
  static final String STATE = PACKAGE_NAME + ".STATE";

  static final int SUCCESS_RESULT = 1;
  static final int FAILURE_RESULT = 0;

  public static final String[] MONTH_NAME = {
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December"
  };


  public static final String[] WEATHER_STATUS = {
      "Thunderstorm",
      "Drizzle",
      "Rain",
      "Snow",
      "Atmosphere",
      "Clear",
      "Few Clouds",
      "Broken Clouds",
      "Cloud"
  };



  public static final String CITY_INFO = "city-info";

  public static final long TIME_TO_PASS = 6 * 600000;

  public static final String LAST_STORED_CURRENT = "last-stored-current";
  public static final String LAST_STORED_MULTIPLE_DAYS = "last-stored-multiple-days";
  public static final String OPEN_WEATHER_MAP_WEBSITE = "https://home.openweathermap.org/api_keys";

  public static final String API_KEY = "6bff40bad5757f3106f0b1f230e9fef3";
  public static final String LANGUAGE = "language";
  public static final String DARK_THEME = "dark-theme";
  public static final String FIVE_DAY_WEATHER_ITEM = "five-day-weather-item";
}
