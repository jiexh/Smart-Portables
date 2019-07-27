import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet("/CartItem")

/*
	CartItem class contains class variables itemId,price,image,retailer.

	CartItem  class has a constructor with Arguments itemId,price,image,retailer.

	CartItem  class contains getters and setters for itemId,price,image,retailer.
*/

public class CartItem extends HttpServlet {
    private String itemId;
    private double price;
    private String image;


    public CartItem(String itemId, double price, String image) {
        this.itemId = itemId;
        this.price = price;
        this.image = image;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
