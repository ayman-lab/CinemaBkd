package com.example.demo.web;

import com.example.demo.dao.FilmReposetory;
import com.example.demo.dao.TicketReposetory;
import com.example.demo.entities.Film;
import com.example.demo.entities.Ticket;
import com.example.demo.web.models.TicketForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmReposetory filmReposetory;
    @Autowired
    private TicketReposetory ticketReposetory;

    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id") Long id) throws Exception {
        Film film = filmReposetory.findById(id).get();
        String photoName = film.getPhoto();
        File file = new File(System.getProperty("user.home") + "/cinema/images/" + photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }
    @CrossOrigin("*")
    @PostMapping("/payerTicket")
    @Transactional
    public List<Ticket> reserverTicket(@RequestBody TicketForm ticketForm) {

        List<Ticket> tickets = new ArrayList<>();
        ticketForm.getListIdTicket().forEach(id -> {
            Ticket ticket = ticketReposetory.findById(id).get();
            ticket.setNomClient(ticketForm.getClientName());
            ticket.setReserve(true);
            ticket.setCodePayement(ticketForm.getCodePayement());
            ticketReposetory.save(ticket);
        });
        return tickets;
    }



}


