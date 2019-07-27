import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/Product")

public class Product extends HttpServlet {
    private String id;
    private String name;
    private String category;
    private String subcategory;
    private double price;
    private String image;
    private String condition;
    private double discount;
    private Set<String> accessoryIds;
    private String manufacturer;
    private double manufacturerRebate;
    private int inventoryCount;

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(final String subcategory) {
        this.subcategory = subcategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(final String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setManufacturerRebate(final double manufacturerRebate) {
        this.manufacturerRebate = manufacturerRebate;
    }

    public double getManufacturerRebate() {
        return this.manufacturerRebate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    Set<String> getAccessoryIds() {
        return accessoryIds;
    }

    public void setAccessoryIds(Set<String> accessoryIds) {
        this.accessoryIds = accessoryIds;
    }


    public int getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(final int inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", condition='" + condition + '\'' +
                ", discount=" + discount +
                ", accessoryIds=" + accessoryIds +
                ", manufacturer='" + manufacturer + '\'' +
                ", manufacturerRebate=" + manufacturerRebate +
                ", inventoryCount=" + inventoryCount +
                '}';
    }
}
