package foods;

public class Salad extends Food{

    public enum SaladType implements FoodSubtype {
        RUSKA(1.5, 110, 1),
        LUTENICA(1.1, 108, 2),
        SNEJANKA(1.2, 104, 3),
        ZELE_I_MORKOVI (0.8, 102, 4),
        DOMATI_I_KRASTAVICI(1.3, 103, 5);

        private final double priceLeva;
        private final long prepareTimeMilliseconds;
        private int id;


        SaladType(double priceLeva, long prepareTimeMilliseconds, int id) {
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

    public Salad(SaladType foodSubtype) {
        super(foodSubtype);
    }

}
