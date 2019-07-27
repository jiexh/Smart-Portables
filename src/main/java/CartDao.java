import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.servlet.ServletException;

/**
 *
 * User a hashmap to store carts for all users.
 * Persists to files if required otherwise the all carts are cleaned after a deployment.
 */

public class CartDao {
    public static HashMap<String, ArrayList<CartItem>> cartMap = new HashMap<String, ArrayList<CartItem>>();

    public CartDao() {

    }

    /* gets the all items in cart for the user*/
    public static ArrayList<CartItem> getUserCart(String username) {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        if (CartDao.cartMap.containsKey(username)) {
            cartItems = CartDao.cartMap.get(username);
        }

        return cartItems;
    }

    public static void removeItem(String username, String itemId) {
        ArrayList<CartItem> cartItems = CartDao.cartMap.get(username);

        if (cartItems == null) {
            return;
        }

        // Since we use itemId to track uniqueness in the cart, when there are duplicate items, only one will be removed.
        CartItem targetItem = null;

        for (CartItem item : cartItems) {
            if  (item.getItemId().equals(itemId)) {
                targetItem = item;
            }
        }

        if (targetItem != null) {
            cartItems.remove(targetItem);
        }
    }

    public static void updateUserCart(String username, String itemId) throws ServletException {
        if (!CartDao.cartMap.containsKey(username)) {
            ArrayList<CartItem> arr = new ArrayList<>();
            CartDao.cartMap.put(username, arr);
        }

        ArrayList<CartItem> cartItems = CartDao.cartMap.get(username);

        Product product = MySqlDataStoreUtilities.listProducts().get(itemId);
        cartItems.add(new CartItem(itemId, product.getPrice(), product.getImage()));

        // sort the cart by the itemId to make removing duplicate item look clearer
        Collections.sort(cartItems, new Comparator<CartItem>() {
            @Override
            public int compare(final CartItem o1, final CartItem o2) {
                return o1.getItemId().compareTo(o2.getImage());
            }
        });
    }
}
