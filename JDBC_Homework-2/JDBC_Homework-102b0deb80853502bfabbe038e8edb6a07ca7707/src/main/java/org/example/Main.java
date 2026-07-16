package org.example;

import org.example.service.ClientService;
import org.example.service.DatabaseQueryService;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("--- DB Start (Flyway + CRUD) ---");

        ClientService clientService = new ClientService();

        System.out.println("\nInitial client list:");
        clientService.listAll().forEach(System.out::println);

        System.out.println("\nCreating a new client...");
        long newClientId = clientService.create("Cyberdyne Systems");
        System.out.println("Client created with ID: " + newClientId);

        String clientName = clientService.getById(newClientId);
        System.out.println("Retrieved name for ID " + newClientId + ": " + clientName);

        System.out.println("\nUpdating client name...");
        clientService.setName(newClientId, "Cyberdyne Systems Revised");
        System.out.println("New name in DB: " + clientService.getById(newClientId));

        System.out.println("\nDeleting client with ID: " + newClientId);
        clientService.deleteById(newClientId);

        System.out.println("\nClient list after deletion:");
        clientService.listAll().forEach(System.out::println);

        try {
            System.out.println("\nAttempting to create a client with an invalid name...");
            clientService.create("A");
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error successfully caught: " + e.getMessage());
        }

        System.out.println("\n=== Executing Analytical Queries ===");
        DatabaseQueryService queryService = new DatabaseQueryService();
        System.out.println("Max projects count per client:");
        queryService.findMaxProjectsClient().forEach(System.out::println);
        System.out.println("\nLongest Project:");
        queryService.findLongestProject().forEach(System.out::println);
        System.out.println("\nEmployees with the highest salary:");
        queryService.findMaxSalaryWorker().forEach(System.out::println);
        System.out.println("\nYoungest and oldest workers:");
        queryService.findYoungestEldestWorkers().forEach(System.out::println);
        System.out.println("\nProject prices:");
        queryService.printProjectPrices().forEach(System.out::println);
    }
}
