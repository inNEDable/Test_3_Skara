package foods;

public class Bread extends Food{

    public enum BreadType implements FoodSubtype {
        WHITE(0.9, 102, 1),
        WHOLEGRAIN(1.4, 105, 2);

        private double price;
        private int id;

        private long prepareTimeMilliseconds;
        BreadType(double price, long prepareTimeMilliseconds, int id) {
            this.price = price;
            this.prepareTimeMilliseconds = prepareTimeMilliseconds;
            this.id = id;
        }



        public double getPrice() {
            return price;
        }

        @Override
        public int getId() {
            return id;
        }

        public long getPrepareTimeMilliseconds() {
            return prepareTimeMilliseconds;
        }

    }

    public Bread(BreadType foodSubtype) {
        super(foodSubtype);
    }

}
