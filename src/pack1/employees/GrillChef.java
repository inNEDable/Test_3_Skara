package pack1.employees;

import foods.Meat;
import pack1.Skaradjiinica;

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

    private synchronized void grillMeats() {

        synchronized (grillKey) {
            Meat.MeatType meatType = Meat.MeatType.values()[new Random().nextInt(Meat.MeatType.values().length)];

            while (employer.totalMeatInContainer() >= MAX_MEET_CAPACITY){
                System.out.println("<<<<<<<< TOO MUCH MEAT >>>>>>>>>>>>>>>");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(meatType.getPrepareTimeMilliseconds());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Meat newGrilledMeat = new Meat(meatType);
            employer.receiveMeat(newGrilledMeat);
            System.out.println(newGrilledMeat.getFoodSubtype() + " has been grilled. Total meats are " + employer.totalMeatInContainer());
            notifyAll();
        }
    }

}
