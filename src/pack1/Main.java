package pack1;

import pack1.employees.BreadChef;
import pack1.employees.GrillChef;
import pack1.employees.SaladChef;
import pack1.employees.Seller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {


        BreadChef breadChef = new BreadChef();
        GrillChef grillChef = new GrillChef();
        SaladChef saladChef = new SaladChef();
        Seller seller = new Seller();
        Skaradjiinica skaradjiinica = new Skaradjiinica(breadChef, grillChef, saladChef, seller);

        Thread tBreadChef = new Thread(breadChef);
        tBreadChef.start();

        Thread tGrillChef = new Thread(grillChef);
        tGrillChef.start();

        Thread tSaladChef = new Thread(saladChef);
        tSaladChef.start();

        /*ExecutorService bakers = Executors.newFixedThreadPool(1);
        bakers.submit(breadChef);


        ExecutorService saladers = Executors.newFixedThreadPool(1);
        saladers.submit(saladChef);

        ExecutorService grillers = Executors.newFixedThreadPool(1);
        grillers.submit(grillChef);*/


    }
}
