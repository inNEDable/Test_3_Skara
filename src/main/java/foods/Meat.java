package foods;

public class Meat extends Food{

    public enum MeatType implements FoodSubtype {
        KUFTETA(1, 102, 1),
        PLASKAVICA(2, 103, 2),
        PURJOLA(3, 104, 3);
        
        private final double priceLeva;
        private final long prepareTimeMilliseconds;
        private int id;

        MeatType(double priceLeva, long prepareTimeMilliseconds, int id) {
            this.priceLeva = priceLeva;
            this.prepareTimeMilliseconds = prepareTimeMilliseconds;
            this.id = id;
        }


        public double getPrice() {
            return priceLeva;
        }

        @Override
        public int getId() {
            return id;
        }

        public long getPrepareTimeMilliseconds() {
            return prepareTimeMilliseconds;
        }
    }

    public Meat(MeatType foodSubtype) {
        super(foodSubtype);
    }
}
