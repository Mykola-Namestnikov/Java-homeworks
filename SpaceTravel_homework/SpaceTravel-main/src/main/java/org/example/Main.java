package org.example;

import org.example.config.HibernateUtil;
import org.example.entity.Client;
import org.example.entity.Planet;
import org.example.entity.Ticket;
import org.example.service.ClientCrudService;
import org.example.service.PlanetCrudService;
import org.example.service.TicketCrudService;

public class Main {
    public static void main(String[] args) {
        ClientCrudService clientService = new ClientCrudService();
        PlanetCrudService planetService = new PlanetCrudService();
        TicketCrudService ticketService = new TicketCrudService();

        Client newClient = new Client();
        newClient.setName("Yuri Gagarin Eco");
        clientService.save(newClient);
        System.out.println("Saved client ID: " + newClient.getId());

        Planet newPlanet = new Planet();
        newPlanet.setId("PLUTO");
        newPlanet.setName("Pluto");
        planetService.save(newPlanet);

        Ticket ticket = new Ticket();
        ticket.setClient(clientService.findById(1L));
        ticket.setFromPlanet(planetService.findById("EARTH"));
        ticket.setToPlanet(planetService.findById("PLUTO"));
        ticketService.save(ticket);
        System.out.println("Ticket successfully saved!");

        try {
            Ticket invalidTicket = new Ticket();
            invalidTicket.setClient(null);
            invalidTicket.setFromPlanet(planetService.findById("EARTH"));
            invalidTicket.setToPlanet(planetService.findById("PLUTO"));
            ticketService.save(invalidTicket);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught expected error: " + e.getMessage());
        }
        HibernateUtil.shutdown();
    }
}
