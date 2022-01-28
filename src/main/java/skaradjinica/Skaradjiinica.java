package skaradjinica;

import foods.Bread;
import foods.Meat;
import foods.Salad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Skaradjiinica {

    private Seller seller;
    private GrillChef grillChef;
    private SaladChef saladChef;
    private BreadChef breadChef;
    private Owner owner;
    private double register;
    private int id;


    protected HashMap<Bread.BreadType, ArrayList<Bread>> breadContainer = new HashMap<>();
    protected HashMap<Meat.MeatType, ArrayList<Meat>> meatContainer = new HashMap<>();
    protected HashMap<Salad.SaladType, Integer> saladContainer = new HashMap<>();

    protected BlockingQueue<Client> clients = new LinkedBlockingQueue<>();


    public Skaradjiinica(BreadChef breadChef, GrillChef grillChef, SaladChef saladChef, Seller seller) {
        this.breadChef = breadChef;
        this.grillChef = grillChef;
        this.saladChef = saladChef;
        this.seller = seller;
        this.id = 123456;
        this.owner = new Owner(this);
        this.owner.setDaemon(true);
        this.owner.start();

        this.breadChef.setEmployer(this);
        this.grillChef.setEmployer(this);
        this.saladChef.setEmployer(this);
        this.seller.setEmployer(this);

        Thread a = new Thread(breadChef);
//        a.setDaemon(true);
        a.start();
        Thread b = new Thread(grillChef);
//        b.setDaemon(true);
        b.start();
        Thread c = new Thread(saladChef);
//        c.setDaemon(true);
        c.start();
        Thread d = new Thread(seller);
//        d.setDaemon(true);
        d.start();
    }

    public  void welcomeClient(Client client){
        synchronized (Employee.sellToClientKey) {
            clients.offer(client);
            Employee.sellToClientKey.notifyAll();
        }
    }

    public void putMoneyInRegister(double totalSum) {
        register+= totalSum;
    }

    /////////////////////////////////////////////////////////////////////////

    public int totalBreadsInContainer() {
        int counter = 0;

        for (Bread.BreadType breadType : breadContainer.keySet()) {
            counter+= breadContainer.get(breadType).size();
        }
        return counter;
    }

    public void receiveBread(Bread newBakedBread) {

        if (!breadContainer.containsKey((Bread.BreadType) newBakedBread.getFoodSubtype())){
            breadContainer.put((Bread.BreadType) newBakedBread.getFoodSubtype(), new ArrayList<>());
        }

        breadContainer.get((Bread.BreadType) newBakedBread.getFoodSubtype()).add(newBakedBread);
        System.out.println(newBakedBread.getFoodSubtype() + " has been added. Total amount is " + totalBreadsInContainer());
    }

    public Bread getBread(Bread.BreadType breadType) {
        Bread breadToReturn = breadContainer.get(breadType).get(0);
        breadContainer.get(breadType).remove(breadToReturn);
        return breadToReturn;
    }


    /////////////////////////////////////////////////////////////////////////

    public int totalMeatInContainer() {
        int counter = 0;

        for (Meat.MeatType meatType : meatContainer.keySet()) {
            counter+= meatContainer.get(meatType).size();
        }

        return counter;
    }

    public void receiveMeat(Meat newGrilledMeat) {

        if (!meatContainer.containsKey((Meat.MeatType) newGrilledMeat.getFoodSubtype())){
            meatContainer.put((Meat.MeatType) newGrilledMeat.getFoodSubtype(), new ArrayList<>());
        }

        meatContainer.get((Meat.MeatType) newGrilledMeat.getFoodSubtype()).add(newGrilledMeat);
        System.out.println(newGrilledMeat.getFoodSubtype() + " has been added. Total MEATS are amount is " + totalMeatInContainer());
    }

    public Meat getMeat(Meat.MeatType meatType) {
        Meat meatToReturn = meatContainer.get(meatType).get(0);
        meatContainer.get(meatType).remove(meatToReturn);
        return meatToReturn;
    }

    /////////////////////////////////////////////////////////////////////////

    public int totalSaladContainer() {
        int counter = 0;

        for (Salad.SaladType saladType : saladContainer.keySet()) {
            counter+= saladContainer.get(saladType);
        }

        return counter;
    }

    public void receiveSalad(Salad newMixedSalad, int amountToCreate) {
        if (!saladContainer.containsKey((Salad.SaladType) newMixedSalad.getFoodSubtype())){
            saladContainer.put((Salad.SaladType) newMixedSalad.getFoodSubtype(), 0);
        }

        int oldGrams = saladContainer.get((Salad.SaladType) newMixedSalad.getFoodSubtype());
        saladContainer.put((Salad.SaladType) newMixedSalad.getFoodSubtype(), oldGrams + amountToCreate);
        System.out.println(newMixedSalad.getFoodSubtype() + " has been added. Total amount is " + totalSaladContainer());
    }

    public Salad getSalad(Salad.SaladType saladType) {

        Salad saladToReturn = new Salad(saladType);
        int temp = saladContainer.get(saladType) - 500;
        saladContainer.put(saladType, temp);
        return  saladToReturn;
    }

    /////////////////////////////////////////////////////////////////////////


    public int getId() {
        return id;
    }
}
