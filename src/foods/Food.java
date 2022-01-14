package foods;

public abstract class Food {

    private FoodSubtype foodSubtype;

    public FoodSubtype getFoodSubtype (){
        return foodSubtype;
    }

    public Food(FoodSubtype foodSubtype) {
        this.foodSubtype = foodSubtype;
    }
}
