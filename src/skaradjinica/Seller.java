package skaradjinica;

import foods.Bread;
import foods.Meat;
import foods.Salad;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Random;

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
                createReport(clientOrder);
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

    private void createReport(Order clientOrder) throws IOException {
        File file = new File("C:\\Users\\NED\\IdeaProjects\\Test_3_Skara\\src\\reports");
        file.mkdir();

        File file2 = new File("C:\\Users\\NED\\IdeaProjects\\Test_3_Skara\\src\\reports\\sales.txt");

        file2.createNewFile();


        PrintStream printStream = new PrintStream(file2);
        printStream.println(Thread.currentThread().getName() + "Order " + (++orderCount) + " Price: " + clientOrder.getTotalSum() + " products: " + clientOrder + "\n");
        printStream.close();

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
