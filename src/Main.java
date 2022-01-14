import foods.Bread;
import foods.Meat;
import foods.Salad;
import skaradjinica.Skaradjiinica;
import skaradjinica.BreadChef;
import skaradjinica.GrillChef;
import skaradjinica.SaladChef;
import skaradjinica.Seller;

import java.util.Random;

public class Main {

    public static void main(String[] args) {


        BreadChef breadChef = new BreadChef();
        GrillChef grillChef = new GrillChef();
        SaladChef saladChef = new SaladChef();
        Seller seller = new Seller();
        Skaradjiinica skaradjiinica = new Skaradjiinica(breadChef, grillChef, saladChef, seller);

        Thread tBreadChef = new Thread(breadChef);
        tBreadChef.setName("Bread cheff");
        tBreadChef.start();

        Thread tGrillChef = new Thread(grillChef);
        tGrillChef.start();
        tGrillChef.setName("Grill cheff");

        Thread tSaladChef = new Thread(saladChef);
        tSaladChef.setName("Salad chef");
        tSaladChef.start();

        Thread tSeller = new Thread(seller);
        tSeller.setName("Seller");
        tSeller.start();



        /*ExecutorService bakers = Executors.newFixedThreadPool(1);
        bakers.submit(breadChef);


        ExecutorService saladers = Executors.newFixedThreadPool(1);
        saladers.submit(saladChef);

        ExecutorService grillers = Executors.newFixedThreadPool(1);
        grillers.submit(grillChef);*/


    }
}
