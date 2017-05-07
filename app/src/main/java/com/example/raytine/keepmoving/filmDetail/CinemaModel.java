package com.example.raytine.keepmoving.filmDetail;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by raytine on 2017/5/1.
 */

public class CinemaModel {
    private String cinemaName;
    private String cinemaId;
    private String address;
    private String filmPrice;
    private Tickets tickets;

    public static class Tickets{
        Map<String, Integer[][]> tickets = new HashMap<>();

        public Tickets() {

        }

        public void put(String key) {
            tickets.put(key, new Integer[5][5]);
        }

        public Map<String, Integer[][]> getTickets() {
            return tickets;
        }

        public void setTickets(Map<String, Integer[][]> tickets) {
            this.tickets = tickets;
        }
    }

    public String getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(String filmTime) {
        this.filmTime = filmTime;
    }

    private String filmTime;


    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFilmPrice() {
        return filmPrice;
    }

    public void setFilmPrice(String filmPrice) {
        this.filmPrice = filmPrice;
    }

    public Object getTickets() {
        return tickets;
    }

    public void setTickets(Tickets tickets) {
        this.tickets = tickets;
    }
}
