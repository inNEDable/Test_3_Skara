package skaradjinica;

import foods.Salad;

import java.util.Random;

public class SaladChef extends Employee implements Runnable{

    private static final int MAX_SALAD_CAPACITY = 25_000;
    public static final int AMOUNT_TO_CREATE = 500;
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

    private  void mixSalads()  {
        synchronized (saladKey) {

            ///////
            while (500 + employer.totalSaladContainer() > MAX_SALAD_CAPACITY){
                System.out.println("<<<<<<<< TOO MUCH SALADS >>>>>>>>>>>>>>>");
                try {
                    saladKey.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Salad.SaladType saladType = getCorrectTypeOfSalad();
            if (saladType == null){
                return;
            }

            try {
                Thread.sleep(saladType.getPrepareTimeMilliseconds());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Salad newMixedSalad = new Salad(saladType);
            employer.receiveSalad(newMixedSalad, AMOUNT_TO_CREATE);
            System.out.println(newMixedSalad.getFoodSubtype() + " has been MIXED. Total SALADS are " + employer.totalSaladContainer());
            saladKey.notifyAll();
        }
    }

    private boolean shouldCreateSalad() {
        int typesOfSalads = Salad.SaladType.values().length;
        int maxCapacityBySalad = MAX_SALAD_CAPACITY / typesOfSalads;

        if (employer.saladContainer.size() < typesOfSalads){
            return true;
        }

        for (Salad.SaladType saladType : employer.saladContainer.keySet()){
            int cuurentAmount = employer.saladContainer.get(saladType);
            if ( cuurentAmount <= maxCapacityBySalad - AMOUNT_TO_CREATE){
                return true;
            }
        }
        return false;
    }

    private Salad.SaladType getCorrectTypeOfSalad() {
        // HashMap<Salad.SaladType, Integer> saladContainer
        int typesOfSalads = Salad.SaladType.values().length;
        int maxCapacityBySalad = MAX_SALAD_CAPACITY / typesOfSalads;

        Salad.SaladType saladTypeToReturn = null;

        for (int i = 0; i < typesOfSalads; i++) {
            if (!employer.saladContainer.containsKey(Salad.SaladType.values()[i])
                    || employer.saladContainer.get(Salad.SaladType.values()[i]) < maxCapacityBySalad - AMOUNT_TO_CREATE){
                saladTypeToReturn = Salad.SaladType.values()[i];
                break;
            }
        }

        return saladTypeToReturn;
    }
}
