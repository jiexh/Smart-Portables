import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/Startup")

public class Startup extends HttpServlet {
    public void init() throws ServletException {
        try {
            MySqlDataStoreUtilities.initConnection();
            MongoDBDataStoreUtilities.initConnection();

            SaxParserDataStore.initHashMap(getServletContext());
            ProductRecommendationUtility.loadRecommendationOutput(getServletContext());

            Map<String, Product> productMap = MySqlDataStoreUtilities.listProducts();

            for (Product product : SaxParserDataStore.products.values()) {
                if (!product.getName().toLowerCase().contains(product.getManufacturer().toLowerCase())) {
                    product.setName(product.getManufacturer().trim() + " " + product.getName().trim());
                }
                if (productMap.containsKey(product.getId())) {
                    MySqlDataStoreUtilities.updateProduct(product);
                } else {
                    MySqlDataStoreUtilities.insertProduct(product);
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
