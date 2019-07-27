import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ProductRecommendationUtility {
	private static final Map<String, List<Product>> RECOMMENDATION_MAP = new HashMap<>();

	public static List<Product> getRecommendationsForUser(String userId) {
		if (RECOMMENDATION_MAP.containsKey(userId)) {
			return RECOMMENDATION_MAP.get(userId);
		}

		return new ArrayList<>();
	}

	public static void loadRecommendationOutput(ServletContext servletContext) throws ServletException {

        BufferedReader br = null;
        String line = "";
		Map<String, Product> productMap = MySqlDataStoreUtilities.listProducts();

		try {
            br = new BufferedReader(new FileReader(new File(servletContext.getRealPath("recommendation-output.csv"))));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] fields = line.split("[,\"']");
				RECOMMENDATION_MAP.put(fields[0], new ArrayList<Product>());
                for (int i = 1; i < fields.length; i++) {
                	Product product = productMap.get(fields[i]);
                	if (product != null) {
                		RECOMMENDATION_MAP.get(fields[0]).add(product);
					} else {
                		System.err.printf("Unknown product id {%s}\n", fields[i]);
					}
				}
            }
		} catch (IOException e) {
			e.printStackTrace();
        }
	}
}