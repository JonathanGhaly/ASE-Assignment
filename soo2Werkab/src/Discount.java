public interface Discount {
    default double getDiscount(double price) {
        return price;
    }
}
