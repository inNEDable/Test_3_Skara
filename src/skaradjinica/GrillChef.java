package skaradjinica;

import foods.Meat;

import java.util.Random;

public class GrillChef extends Employee implements Runnable{

    public static final int MAX_MEET_CAPACITY = 60;
    private Skaradjiinica employer;


    public GrillChef() {
    }

    public void setEmployer(Skaradjiinica employer) {
        this.employer = employer;
    }

    @Override
    public void run() {
        while (true){
            grillMeats();
        }
    }

    private  void grillMeats() {

        synchronized (grillKey) {
            while (employer.totalMeatInContainer() >= MAX_MEET_CAPACITY){
                System.out.println("<<<<<<<< TOO MUCH MEAT >>>>>>>>>>>>>>>");
                try {
                    grillKey.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Meat.MeatType meatType = getTheCorrectMeatType();

            try {
                Thread.sleep(meatType.getPrepareTimeMilliseconds());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Meat newGrilledMeat = new Meat(meatType);
            employer.receiveMeat(newGrilledMeat);
            System.out.println(newGrilledMeat.getFoodSubtype() + " has been grilled. Total meats are " + employer.totalMeatInContainer());
            grillKey.notifyAll();
        }
    }

    private Meat.MeatType getTheCorrectMeatType() {
        /// HashMap<Meat.MeatType, ArrayList<Meat>> meatContainer

        int typesOfMeat = Meat.MeatType.values().length;
        int capacityByMeatType = MAX_MEET_CAPACITY / typesOfMeat;

        Meat.MeatType meatToReturn = null;

        for (int i = 0; i < typesOfMeat; i++) {
            if (employer.meatContainer.get(Meat.MeatType.values()[i]) == null
                    || employer.meatContainer.get(Meat.MeatType.values()[i]).size() < capacityByMeatType){
                meatToReturn =  Meat.MeatType.values()[i];
                break;
            }
        }

        return meatToReturn;
    }

}
