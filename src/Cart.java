import lombok.Getter;

import java.util.*;

public class Cart {
    private final Map<String, Promotions> promoCodes;
    public Map<String, CartItem> products;
    private String appliedPromotion;

    public Cart() {
        this.products = new HashMap<>();
        this.promoCodes = new HashMap<>();
        promoCodes.put("10off", Promotions.TEN_PERCENT_OFF);
        promoCodes.put("trzecizajeden", Promotions.THIRD_PRODUCT_1_ZL);
        promoCodes.put("drugizapolowe", Promotions.SECOND_SAME_PRODUCT_HALF_PRICE);
    }

    public void addProduct(Product product, int quantity) {
        products.putIfAbsent(product.getName(), new CartItem(product, 0));
        products.get(product.getName()).increaseQuantity(quantity);
    }

    public void removeProduct(Product product, int quantity) {
        if (products.containsKey(product.getName())) {
            CartItem item = products.get(product.getName());
            if (item.getQuantity() > quantity) {
                item.decreaseQuantity(quantity);
            } else {
                products.remove(product.getName());
            }
        }
    }

    public void displayProducts() {
        for (Map.Entry<String, CartItem> entry : products.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue().getQuantity() + " pcs.");
        }
    }

    public double calculateWithoutPromotion() {
        double total = 0;
        for (CartItem item : products.values()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public double calculateTotalPrice() {
        if(this.appliedPromotion == null) {
            return calculateWithoutPromotion();
        }
        switch (promoCodes.get(this.appliedPromotion)) {
            case TEN_PERCENT_OFF:
                return calculateWith10PercentOff();
            case THIRD_PRODUCT_1_ZL:
                return calculateWithThirdProduct1ZL();
            case SECOND_SAME_PRODUCT_HALF_PRICE:
                return calculateWithSecondSameProductHalfPrice();
            default:
                System.out.println("Invalid promotion code");
                return calculateWithoutPromotion();
        }
    }

    private double calculateWith10PercentOff() {
        double total = calculateWithoutPromotion();
        return total * 0.9;
    }

    private double calculateWithThirdProduct1ZL() {
        List<ProductBase> allProducts = getAllProductsInCart();
        allProducts.sort(Comparator.comparingDouble(ProductBase::getPrice));
        int discountedItems = allProducts.size() / 3;
        for(int i = 0; i < discountedItems; i++) {
            allProducts.get(i).setPrice(1);
        }
        return allProducts.stream()
                .reduce(0.0, (subtotal, product) -> subtotal + product.getPrice(), Double::sum);
    }

    private double calculateWithSecondSameProductHalfPrice() {
        double total = calculateWithoutPromotion();
        for (CartItem item : products.values()) {
            int discountedItems = item.getQuantity() / 2;
            total -= (item.getProduct().getPrice() / 2) * discountedItems;
        }
        return total;
    }


    public void displayTotalPrice() {
        System.out.printf("Total price: %.2f zł%n", calculateTotalPrice());
    }

    private List<ProductBase> getAllProductsInCart(){
        List<ProductBase> allProducts = new ArrayList<>();
        for (CartItem item : products.values()) {
            for(int i = 0; i < item.getQuantity(); i++) {
                allProducts.add(new ProductBase(item.getProduct().getName(), item.getProduct().getPrice()));
            }
        }
        return allProducts;
    }


    @Getter
    public static class CartItem {
        private final Product product;
        private int quantity;

        public CartItem(Product Product, int ilosc) {
            this.product = Product;
            this.quantity = ilosc;
        }

        public void increaseQuantity(int ilosc) {
            this.quantity += ilosc;
        }

        public void decreaseQuantity(int ilosc) {
            this.quantity -= ilosc;
        }

    }

}
