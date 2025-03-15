import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Catalogue {
    ArrayList<Product> products;

    public Catalogue() {
        products = new ArrayList<>();
        products.add(new Product("Laptop", 1000, Category.ELECTRONICS, true));
        products.add(new Product("T-shirt", 20, Category.CLOTHING, true));
        products.add(new Product("Football", 50, Category.SPORTS, false));
        products.add(new Product("Apple", 1, Category.FOOD, true));
        products.add(new Product("Java for Dummies", 30, Category.BOOKS, true));
        products.add(new Product("Smartphone", 800, Category.ELECTRONICS, true));
        products.add(new Product("Jeans", 50, Category.CLOTHING, false));
        products.add(new Product("Basketball", 40, Category.SPORTS, true));
        products.add(new Product("Banana", 2, Category.FOOD, true));
        products.add(new Product("Effective Java", 40, Category.BOOKS, true));
        products.add(new Product("Tablet", 600, Category.ELECTRONICS, true));
        products.add(new Product("Dress", 70, Category.CLOTHING, true));
        products.add(new Product("Volleyball", 30, Category.SPORTS, true));
        products.add(new Product("Orange", 3, Category.FOOD, true));
    }

    public List<ProductBase> getAllAvailableProductsFromCategory(Category category) {
        List<ProductBase> result = products.stream()
                .filter(Product::isAvailable)
                .filter(product -> product.getCategory().equals(category))
                .sorted(Comparator.comparing(Product::getName))
                .map(product -> new ProductBase(product.getName(), product.getPrice()))
                .collect(Collectors.toList());
        System.out.println(result);
        return result;
    }

    public List<ProductBase> getAllProducts() {
        List<ProductBase> result = products.stream()
                .sorted(Comparator.comparing(Product::getName))
                .map(product -> new ProductBase(product.getName(), product.getPrice()))
                .collect(Collectors.toList());
        System.out.println(result);
        return result;
    }


}
