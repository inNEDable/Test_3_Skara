package pack1.employees;

import foods.Bread;
import pack1.Skaradjiinica;

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

    private synchronized void bakeBread (){

        synchronized (breadKey) {
            while ( employer.totalBreadsInContainer() >= MAX_BREAD_CAPACITY){
                try {
                    System.out.println("<<<<<<<< TOO MUCH BREAD >>>>>>>>>>>>>>>");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Bread.BreadType breadType = new Random().nextBoolean() ? Bread.BreadType.WHITE : Bread.BreadType.WHOLEGRAIN;
            try {
                Thread.sleep(breadType.getPrepareTimeMilliseconds());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bread newBakedBread = new Bread(breadType);
            System.out.println(newBakedBread.getFoodSubtype() + " Bread has been created");

            notifyAll();
            employer.receiveBread(newBakedBread);
        }

    }

    public void setEmployer(Skaradjiinica employer) {
        this.employer = employer;
    }
}
