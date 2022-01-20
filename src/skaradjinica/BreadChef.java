package skaradjinica;

import foods.Bread;

import java.util.Random;

public class BreadChef extends Employee implements Runnable{

    public static final int MAX_BREAD_CAPACITY = 60;
    private Skaradjiinica employer;


    @Override
    public void run() {
        while (true){
            bakeBread();
        }
    }

    private void bakeBread (){

        synchronized (breadKey) {
            while ( employer.totalBreadsInContainer() >= MAX_BREAD_CAPACITY){
                try {
                    System.out.println("<<<<<<<< TOO MUCH BREAD >>>>>>>>>>>>>>>");
                    breadKey.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Bread.BreadType breadType = getCorrectTypeOfBread();
            try {
                Thread.sleep(breadType.getPrepareTimeMilliseconds());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bread newBakedBread = new Bread(breadType);
            System.out.println(newBakedBread.getFoodSubtype() + " Bread has been created");

            employer.receiveBread(newBakedBread);
            breadKey.notifyAll();
        }


    }

    private Bread.BreadType getCorrectTypeOfBread() {
        // HashMap<Bread.BreadType, ArrayList<Bread>> breadContainer

        int breadTypes = Bread.BreadType.values().length;
        int maxCapacityByBread = MAX_BREAD_CAPACITY / breadTypes;

        Bread.BreadType breadTypeToReturn = null;

        for (int i = 0; i < breadTypes; i++) {
            if (employer.breadContainer.get(Bread.BreadType.values()[i]) == null
                    ||employer.breadContainer.get(Bread.BreadType.values()[i]).size() < maxCapacityByBread){
                breadTypeToReturn = Bread.BreadType.values()[i];
                break;
            }
        }
        return breadTypeToReturn;
    }

    public void setEmployer(Skaradjiinica employer) {
        this.employer = employer;
    }
}
