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
    private Ticket ticket;

    public static class Ticket{
        Map<String, Integer[]> tickets = new HashMap<>();

        public Ticket() {

        }

        public void put(String key, Integer[] integers) {
            tickets.put(key, integers);
        }

        public Map<String, Integer[]> getTickets() {
            return tickets;
        }

        public void setTickets(Map<String, Integer[]> tickets) {
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

    public Object getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
