package com.tertandaid.openweather;

import java.util.ArrayList;

    public class Weather2 {
    private String mTemperature;
    private boolean itemJam;

    public Weather2(String temperture, boolean itemJam) {
        mTemperature = temperture;
        itemJam = itemJam;
    }

    public String getName() {
        return mTemperature;
    }

    public boolean isOnline() {
        return itemJam;
    }

    private static int lastContactId = 0;

    public static ArrayList<Weather2> createContactsList(int numContacts) {
        ArrayList<Weather2> weather2s = new ArrayList<Weather2>();

        for (int i = 1; i <= numContacts; i++) {
            weather2s.add(new Weather2("Person " + ++lastContactId, i <= numContacts / 2));
        }

        return weather2s;
    }
}