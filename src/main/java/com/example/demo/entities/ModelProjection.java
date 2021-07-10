package com.example.demo.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;
import java.util.Date;

@Projection(name = "p1",types = {com.example.demo.entities.Projection.class})
public interface ModelProjection {
    public long getId();
    public Date getDateProjection();
    public double getPrix();
    public Salle getSalle();
    public  Film getFilm();
    public Seance getSeance();
    public Collection<Ticket> getTickets();

}
