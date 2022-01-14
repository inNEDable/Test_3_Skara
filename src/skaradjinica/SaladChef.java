package skaradjinica;

import foods.Salad;

import java.util.Random;

public class SaladChef extends Employee implements Runnable{

    private static final int MAX_SALAD_CAPACITY = 25_000;
    private Skaradjiinica employer;

    public void setEmployer(Skaradjiinica employer) {
        this.employer = employer;
    }

    @Override
    public void run() {
        while (true){
            mixSalads();
        }
    }

    private  void mixSalads() {
        synchronized (saladKey) {
            Salad.SaladType saladType = Salad.SaladType.values()[new Random().nextInt(Salad.SaladType.values().length)];

            while (500 + employer.totalSaladContainer() > MAX_SALAD_CAPACITY){
                System.out.println("<<<<<<<< TOO MUCH SALADS >>>>>>>>>>>>>>>");
                try {
                    saladKey.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(saladType.getPrepareTimeMilliseconds());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Salad newMixedSalad = new Salad(saladType);
            employer.receiveSalad(newMixedSalad);
            System.out.println(newMixedSalad.getFoodSubtype() + " has been MIXED. Total SALADS are " + employer.totalSaladContainer());
            saladKey.notifyAll();
        }
    }
}
