import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product extends ProductBase {
    Category category;
    boolean  available;

    public Product(String name, double price, Category category, boolean available) {
        super(name, price);
        this.category = category;
        this.available = available;
    }
}
