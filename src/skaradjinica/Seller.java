package skaradjinica;

import foods.Bread;
import foods.Meat;
import foods.Salad;

import java.util.Random;

public class Seller extends Employee implements Runnable{

    private Skaradjiinica employer;

    public void setEmployer(Skaradjiinica employer) {
        this.employer = employer;
    }

    @Override
    public void run() {
        while (true) {
            Order order = assembleTheOrder(
                    Bread.BreadType.values()[new Random().nextInt(Bread.BreadType.values().length)],
                    Meat.MeatType.values()[new Random().nextInt(Meat.MeatType.values().length)],
                    Salad.SaladType.values()[new Random().nextInt(Salad.SaladType.values().length)]
            );
            System.out.println("Uspeshno realizirahme " + order);
        }
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

     /*   private boolean hasBread(Bread.BreadType breadType) {
        for (Bread.BreadType keyBreadType : employer.breadContainer.keySet()){
            if (keyBreadType.equals(breadType)){
                employer.breadContainer.get(breadKey)
            }
        }
    }*/
}
