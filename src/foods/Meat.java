package foods;

public class Meat extends Food{

    public enum MeatType implements FoodSubtype {
        KUFTETA(1, 102),
        PLASKAVICA(2, 103),
        PURJOLA(3, 104);

        private final double priceLeva;
        private final long prepareTimeMilliseconds;

        MeatType(double priceLeva, long prepareTimeMilliseconds) {
            this.priceLeva = priceLeva;
            this.prepareTimeMilliseconds = prepareTimeMilliseconds;
        }

        public double getPriceLeva() {
            return priceLeva;
        }

        public long getPrepareTimeMilliseconds() {
            return prepareTimeMilliseconds;
        }
    }

    private MeatType meatType;

    public Meat(MeatType foodSubtype) {
        super(foodSubtype);
    }
}
