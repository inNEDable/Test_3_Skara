package pack1;

import foods.Bread;
import foods.FoodSubtype;
import foods.Meat;
import foods.Salad;
import pack1.employees.BreadChef;
import pack1.employees.GrillChef;
import pack1.employees.SaladChef;
import pack1.employees.Seller;

import java.util.ArrayList;
import java.util.HashMap;

public class Skaradjiinica {

    private Seller seller;
    private GrillChef grillChef;
    private SaladChef saladChef;
    private BreadChef breadChef;

    private HashMap<Bread.BreadType, ArrayList<Bread>> breadContainer = new HashMap<>();
    private HashMap<Meat.MeatType, ArrayList<Meat>> meatContainer = new HashMap<>();
    private HashMap<Salad.SaladType, Integer> saladContainer = new HashMap<>();


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



    public int totalBreadsInContainer() {
        int counter = 0;

        for (Bread.BreadType breadType : breadContainer.keySet()) {
            counter+= breadContainer.get(breadType).size();
        }
        return counter;
    }

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

    public void receiveBread(Bread newBakedBread) {

        if (!breadContainer.containsKey((Bread.BreadType) newBakedBread.getFoodSubtype())){
            breadContainer.put((Bread.BreadType) newBakedBread.getFoodSubtype(), new ArrayList<>());
        }

        breadContainer.get((Bread.BreadType) newBakedBread.getFoodSubtype()).add(newBakedBread);
        System.out.println(newBakedBread.getFoodSubtype() + " has been added. Total amount is " + totalBreadsInContainer());
    }

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
}
