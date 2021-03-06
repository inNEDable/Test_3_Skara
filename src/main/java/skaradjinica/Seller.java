package skaradjinica;

import db_shit.DBManager;
import foods.Bread;
import foods.Meat;
import foods.Salad;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Seller extends Employee implements Runnable{
    private static int orderCount = 0;

    private Skaradjiinica employer;

    public void setEmployer(Skaradjiinica employer) {
        this.employer = employer;
    }

    @Override
    public void run() {
        while (true) {
            serveClients();
        }
    }

    private void serveClients() {
        synchronized (Employee.sellToClientKey) {
            while (employer.clients.isEmpty()){
                try {
                    Employee.sellToClientKey.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Client currentClient = employer.clients.poll();
            Order clientOrder = assembleTheOrder(currentClient.getWantedBread(), currentClient.getWantedMeat(), currentClient.getWantedSalad());

            currentClient.recieveOrder(clientOrder);
            try {
                createLocalReport(clientOrder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                currentClient.payBill(clientOrder.getTotalSum());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            employer.putMoneyInRegister(clientOrder.getTotalSum());
        }
    }

    private void createLocalReport(Order clientOrder) throws IOException {
        String path = "C:\\Users\\NED\\IdeaProjects\\Test_3_Skara\\src\\main\\java\\reports\\sales.txt";

        if (!Files.exists(Path.of(path))){
            Files.createFile(Path.of(path));
        }

        clientOrder.setDate(LocalDate.now());

        Files.writeString(Path.of(path), clientOrder.toString()+"\n", StandardOpenOption.APPEND);

    }

    public  Order assembleTheOrder(Bread.BreadType breadType, Meat.MeatType meatType, Salad.SaladType saladType) {

        Bread breadForClient;
        Meat meatForClient;
        Salad saladForClient;

        synchronized (breadKey) {
           while (!employer.breadContainer.containsKey(breadType) || employer.breadContainer.get(breadType).isEmpty()){
               try {
                   breadKey.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           breadForClient = employer.getBread(breadType);
           breadKey.notifyAll();
        }

        synchronized (grillKey){
            while (!employer.meatContainer.containsKey(meatType) || employer.meatContainer.get(meatType).isEmpty()){
                try {
                    grillKey.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            meatForClient = employer.getMeat(meatType);
            grillKey.notifyAll();
        }

        synchronized (saladKey) {
            while (!employer.saladContainer.containsKey(saladType) || employer.saladContainer.get(saladType) == 0) {
                try {
                    saladKey.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            saladForClient = employer.getSalad(saladType);
            saladKey.notifyAll();
        }

        return new Order(breadForClient, meatForClient, saladForClient);
    }
}
