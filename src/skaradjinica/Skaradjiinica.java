package skaradjinica;

import foods.Bread;
import foods.Meat;
import foods.Salad;

import java.util.ArrayList;
import java.util.HashMap;

public class Skaradjiinica {

    private Seller seller;
    private GrillChef grillChef;
    private SaladChef saladChef;
    private BreadChef breadChef;

    protected HashMap<Bread.BreadType, ArrayList<Bread>> breadContainer = new HashMap<>();

    /*
    bql - 0
    cheren - 0
     */
    protected HashMap<Meat.MeatType, ArrayList<Meat>> meatContainer = new HashMap<>();
    protected HashMap<Salad.SaladType, Integer> saladContainer = new HashMap<>();


    public Skaradjiinica(BreadChef breadChef, GrillChef grillChef, SaladChef saladChef, Seller seller) {
        this.breadChef = breadChef;
        this.grillChef = grillChef;
        this.saladChef = saladChef;
        this.seller = seller;

        this.breadChef.setEmployer(this);
        this.grillChef.setEmployer(this);
        this.saladChef.setEmployer(this);
        this.seller.setEmployer(this);

    }

    public  void receiveAnOrder (Bread.BreadType breadType, Meat.MeatType meatType, Salad.SaladType saladType){
        Order order = seller.assembleTheOrder(breadType, meatType, saladType);

        System.out.println("Uspeshno realizirahme " + order);

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

    public void receiveSalad(Salad newMixedSalad) {
        if (!saladContainer.containsKey((Salad.SaladType) newMixedSalad.getFoodSubtype())){
            saladContainer.put((Salad.SaladType) newMixedSalad.getFoodSubtype(), 500);
        }

        int oldGrams = saladContainer.get((Salad.SaladType) newMixedSalad.getFoodSubtype());
        saladContainer.put((Salad.SaladType) newMixedSalad.getFoodSubtype(), oldGrams + 500);
        System.out.println(newMixedSalad.getFoodSubtype() + " has been added. Total amount is " + totalSaladContainer());
    }

    public Salad getSalad(Salad.SaladType saladType) {
        Salad saladToReturn = new Salad(saladType);
        int temp = saladContainer.get(saladType) - 200;
        saladContainer.put(saladType, temp);
        return  saladToReturn;
    }

    /////////////////////////////////////////////////////////////////////////

}
