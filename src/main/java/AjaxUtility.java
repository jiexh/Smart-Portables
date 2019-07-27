import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

public class AjaxUtility {
    private static int MIN_QUERY_STRING_LENGTH = 2;
    private static int MAX_QUERY_RESULT_LENGTH = 7;
    public static List<Product> queryProductsByName(String searchId) throws ServletException {
        // Auto complete when there are 2 or more letters
        if (searchId == null || searchId.length() < MIN_QUERY_STRING_LENGTH) {
            return new ArrayList<>();
        }

        String query = "select * from Products where name like '%" + searchId + "%' limit " + MAX_QUERY_RESULT_LENGTH;
        return new ArrayList<>(MySqlDataStoreUtilities.queryProducts(query).values());
    }
}
