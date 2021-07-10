package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaServiceImpl implements ICinemaInitService {

    @Autowired
    private VilleReposetory villeReposetory;
    @Autowired
    private CinemaReposetory cinemaReposetory;
    @Autowired
    private SalleReposetory salleReposetory;
    @Autowired
    private PlaceReposetory placeReposetory;
    @Autowired
    private SeanceReposetory seanceReposetory;
    @Autowired
    private FilmReposetory filmReposetory;
    @Autowired
    private ProjectionReposetory projectionReposetory;
    @Autowired
    private CategorieReposetory categorieReposetory;
    @Autowired
    private TicketReposetory ticketReposetory;

    @Override
    public void initVilles() {
        Stream.of("Casablanca", "Rabat", "Tanger", "Marrakech").forEach(v -> {
            Ville ville = new Ville();
            ville.setName(v);
            villeReposetory.save(ville);
        });

    }

    @Override
    public void initCinemas() {
        villeReposetory.findAll().forEach(v -> {
            Stream.of("Megarama", "IMAX", "Cianema Star", "Cinema Show")
                    .forEach(c -> {
                        Cinema cinema = new Cinema();
                        cinema.setName(c);
                        cinema.setNombreDeSales(3 + (int) (Math.random() * 7));
                        cinema.setVille(v);
                        cinemaReposetory.save(cinema);
                    });
        });

    }

    @Override
    public void initSalles() {
        cinemaReposetory.findAll().forEach(c -> {
            for (int i = 0; i < c.getNombreDeSales(); i++) {
                Salle salle = new Salle();
                salle.setName("salle" + (i + 1));
                salle.setCinema(c);
                salle.setNombrePlaces(15 + (int) (Math.random() * 20));
                salleReposetory.save(salle);

            }
        });

    }

    @Override
    public void initPlaces() {
        salleReposetory.findAll().forEach(s -> {
            for (int i = 0; i < s.getNombrePlaces(); i++) {
                Place place = new Place();
                place.setNumeroPlace(i + 1);
                place.setSalle(s);
                placeReposetory.save(place);
            }
        });

    }

    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("11:00", "15:00", "17:00", "19:00", "21:00").forEach(s -> {
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(s));
                seanceReposetory.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void initCategories() {
        Stream.of("Actions", "Histoire", "Comedie", "Biographie").forEach(c -> {
            Categorie categorie = new Categorie();
            categorie.setName(c);
            categorieReposetory.save(categorie);

        });

    }

    @Override
    public void initFilms() {

        double[] durees = new double[]{1.5, 1.5, 2, 2.5, 3};
        List<Categorie> categories = categorieReposetory.findAll();
        Stream.of(  "A Beautiful Mind", "Zodiac","Cast Away","TheGodFather","Lucy","Inception","the Equalizer","the Revenent","The GodFather","the Equalizer2").forEach(f -> {
            Film film = new Film();
            film.setTitre(f);
            film.setDuree(durees[new Random().nextInt(durees.length)]);
            film.setPhoto(f.replaceAll(" ", "") + ".jpg");
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));
            filmReposetory.save(film);
        });
    }

    @Override
    public void initProjections() {
        double[] prix = new double[]{60, 75, 90, 45, 110};
        List<Film> films = filmReposetory.findAll();
        villeReposetory.findAll().forEach(v -> {
            v.getCinemas().forEach(c -> {
                c.getSalles().forEach(s -> {
                    int index = new Random().nextInt(films.size());
                    Film film = films.get(index);

                        seanceReposetory.findAll().forEach(seance -> {
                            Projection projection = new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix(prix[new Random().nextInt(prix.length)]);
                            projection.setSalle(s);
                            projection.setSeance(seance);
                            projectionReposetory.save(projection);
                        });

                });
            });
        });

    }

    @Override
    public void initTickets() {
        projectionReposetory.findAll().forEach(p -> {
            p.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(p.getPrix());
                ticket.setProjection(p);
                ticket.setReserve(false);
                ticketReposetory.save(ticket);
            });
        });

    }
}
