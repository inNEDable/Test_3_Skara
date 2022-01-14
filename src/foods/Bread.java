package foods;

public class Bread extends Food{

    public enum BreadType implements FoodSubtype {
        WHITE(0.9, 102),
        WHOLEGRAIN(1.4, 105);

        private double price;

        private long prepareTimeMilliseconds;
        BreadType(double price, long prepareTimeMilliseconds) {
            this.price = price;
            this.prepareTimeMilliseconds = prepareTimeMilliseconds;
        }

        public double getPrice() {
            return price;
        }

        public long getPrepareTimeMilliseconds() {
            return prepareTimeMilliseconds;
        }

    }

    public Bread(BreadType foodSubtype) {
        super(foodSubtype);
    }

}
