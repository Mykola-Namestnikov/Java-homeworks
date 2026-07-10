package org.example;

import org.example.service.DatabaseInitService;
import org.example.service.DatabasePopulateService;
import org.example.service.DatabaseQueryService;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main(String[] args) {
        System.out.println("--- Ініціалізація БД ---");
        DatabaseInitService.main(args);

        System.out.println("\n--- Наповнення БД даними ---");
        DatabasePopulateService.main(args);

        System.out.println("\n--- Виконання аналітичних запитів ---");
        DatabaseQueryService queryService = new DatabaseQueryService();

        System.out.println("Максимум проєктів у клієнта:");
        queryService.findMaxProjectsClient().forEach(System.out::println);

        System.out.println("\nНайтриваліший проєкт:");
        queryService.findLongestProject().forEach(System.out::println);

        System.out.println("\nПрацівники з найбільшою зп:");
        queryService.findMaxSalaryWorker().forEach(System.out::println);

        System.out.println("\nНаймолодші та найстарші працівники:");
        queryService.findYoungestEldestWorkers().forEach(System.out::println);

        System.out.println("\nЦіни проєктів:");
        queryService.printProjectPrices().forEach(System.out::println);
    }
}
