package com.example.demo.entities;

import java.util.Collection;
import java.util.Date;

@org.springframework.data.rest.core.config.Projection(name = "p2", types = {Ticket.class})
public interface ModelTicket {

    public long getId();

    public String getNomClient();

    public double getPrix();

    public Integer getCodePayement();

    public boolean getReserve();

    public Place getPlace();


}
