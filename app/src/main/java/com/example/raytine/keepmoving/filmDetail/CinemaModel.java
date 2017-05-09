package com.example.raytine.keepmoving.filmDetail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by raytine on 2017/5/1.
 */

public class CinemaModel implements Serializable{
    private String cinemaName;
    private String cinemaId;
    private String address;
    private String filmPrice;
    private Ticket ticket;
    private String cinemaObjectId;


    public String getCinemaObjectId() {
        return cinemaObjectId;
    }

    public void setCinemaObjectId(String cinemaObjectId) {
        this.cinemaObjectId = cinemaObjectId;
    }


    public static class Ticket implements Serializable{
        Map<String, Integer[]> tickets = new HashMap<>();



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


    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
