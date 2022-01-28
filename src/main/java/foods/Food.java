package foods;

public abstract class Food {

    private FoodSubtype foodSubtype;

    public Food(FoodSubtype foodSubtype) {
        this.foodSubtype = foodSubtype;
    }

    public FoodSubtype getFoodSubtype (){
        return foodSubtype;
    }
}
