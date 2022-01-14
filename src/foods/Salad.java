package foods;

public class Salad extends Food{

    public enum SaladType implements FoodSubtype {
        RUSKA(1.5, 110),
        LUTENICA(1.1, 108),
        SNEJANKA(1.2, 104),
        ZELE_I_MORKOVI (0.8, 102),
        DOMATI_I_KRASTAVICI(1.3, 103);

        private final double priceLeva;

        private final long prepareTimeMilliseconds;
        SaladType(double priceLeva, long prepareTimeMilliseconds) {
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

    public Salad(SaladType foodSubtype) {
        super(foodSubtype);
    }

}
