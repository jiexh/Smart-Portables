import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

public class MySqlDataStoreUtilities {
    private static Connection conn = null;
    private static Map<String, Product> productMap = new HashMap<>();

    /**
     * Order processing methods
     */

    public static void initConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartPortables", "root", "root");
    }

    public static void cancelOrderItem(int orderId, String itemId) throws ServletException {
        try {
            String deleteOrderQuery = "Delete from Orders where OrderId=? and itemId=?";
            PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
            pst.setInt(1, orderId);
            pst.setString(2, itemId);
            pst.executeUpdate();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public static void cancelOrder(int orderId) throws ServletException {
        try {
            String deleteOrderQuery = "Delete from Orders where OrderId=?";
            PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
            pst.setInt(1, orderId);
            pst.executeUpdate();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public static void insertOrder(OrderPayment order) throws ServletException {
        try {
            String insertIntoCustomerOrderQuery = "INSERT INTO Orders(orderId,userName,itemId,itemPrice,userAddress,creditCardNo,deliveryTime,orderTime)"
                    + "VALUES (?,?,?,?,?,?,?,?);";

            PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
            //set the parameter for each column and execute the prepared statement
            pst.setInt(1, order.getOrderId());
            pst.setString(2, order.getUserName());
            pst.setString(3, order.getItemId());
            pst.setDouble(4, order.getItemPrice());
            pst.setString(5, order.getUserAddress());
            pst.setString(6, order.getCreditCardNo());
            pst.setLong(7, order.getDeliveryTime());
            pst.setLong(8, order.getOrderTime());
            pst.execute();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public static void updateOrder(OrderPayment order) throws ServletException {
        try {
            String updateOrderQuery = "UPDATE Orders SET itemPrice = ?, deliveryTime = ? WHERE orderId = ? and itemId = ?;";

            PreparedStatement pst = conn.prepareStatement(updateOrderQuery);
            //set the parameter for each column and execute the prepared statement
            pst.setDouble(1, order.getItemPrice());
            pst.setLong(2, order.getDeliveryTime());
            pst.setInt(3, order.getOrderId());
            pst.setString(4, order.getItemId());
            pst.execute();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public static List<OrderPayment> selectOrder() throws ServletException {
        List<OrderPayment> allOrders = new ArrayList<>();

        try {
            String selectOrderQuery = "select * from Orders";
            PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                //add to orderpayment hashmap
                OrderPayment order = new OrderPayment(
                        rs.getInt("OrderId"),
                        rs.getString("userName"),
                        rs.getString("itemId"),
                        rs.getDouble("itemPrice"),
                        rs.getString("userAddress"),
                        rs.getString("creditCardNo"),
                        rs.getLong("deliveryTime"),
                        rs.getLong("orderTime"));

                allOrders.add(order);
            }

            return allOrders;
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public static List<OrderPayment> getOrderByOrderIdAndUser(int orderId, String userName) throws ServletException {
        List<OrderPayment> orders = new ArrayList<>();

        for (OrderPayment order : selectOrder()) {
            if (order.getOrderId() == orderId && order.getUserName().equals(userName)) {
                orders.add(order);
            }
        }

        return orders;
    }

    public static List<OrderPayment> getOrdersByUser(String userName) throws ServletException {
        List<OrderPayment> orders = new ArrayList<>();

        for (OrderPayment order : selectOrder()) {
            if (order.getUserName().equals(userName)) {
                orders.add(order);
            }
        }

        return orders;
    }

    public static List<OrderPayment> getOrderByOrderId(int orderId) throws ServletException {
        List<OrderPayment> orders = new ArrayList<>();

        for (OrderPayment order : selectOrder()) {
            if (order.getOrderId() == orderId) {
                orders.add(order);
            }
        }

        return orders;
    }

    /**
     * User management
     */

    public static void insertUser(User user) throws ServletException {
        try {
            String insertIntoCustomerRegisterQuery = "INSERT INTO Users(name,password,usertype) "
                    + "VALUES (?,?,?);";

            PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
            pst.setString(1, user.getName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getUsertype());
            pst.execute();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public static HashMap<String, User> selectUser() throws ServletException {
        HashMap<String, User> hm = new HashMap<>();
        try {
            Statement stmt = conn.createStatement();
            String selectCustomerQuery = "select * from Users";
            ResultSet rs = stmt.executeQuery(selectCustomerQuery);

            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("password"), rs.getString("usertype"));
                hm.put(rs.getString("name"), user);
            }
            return hm;
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Product Management
     */

    public static void insertProduct(Product product) throws ServletException {
        try {
            // Sql needs to escape keywords like id, name, condition
            String insertProductQuery = "INSERT INTO Products (`id`,`name`,price,image,category,subcategory,`condition`," +
                    "discount,accessoryIds,manufacturer,manufacturerRebate,inventoryCount)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";

            PreparedStatement pst = conn.prepareStatement(insertProductQuery);
            pst.setString(1, product.getId());
            pst.setString(2, product.getName());
            pst.setDouble(3, product.getPrice());
            pst.setString(4, product.getImage());
            pst.setString(5, product.getCategory());
            pst.setString(6, product.getSubcategory());
            pst.setString(7, product.getCondition());
            pst.setDouble(8, product.getDiscount());
            pst.setString(9, serializeAccessoryIds(product.getAccessoryIds()));
            pst.setString(10, product.getManufacturer());
            pst.setDouble(11, product.getManufacturerRebate());
            pst.setInt(12, product.getInventoryCount());
            pst.execute();

            productMap.put(product.getId(), product);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public static void updateProduct(Product product) throws ServletException {
        try {
            String updateOrderQuery = "UPDATE Products SET `name` = ?, price = ? ,image = ?, category = ?, subcategory = ?, " +
                    "`condition` = ?, discount = ?,accessoryIds = ?, manufacturer = ?, manufacturerRebate = ?, inventoryCount = ? " +
                    "WHERE `id` = ?;";

            PreparedStatement pst = conn.prepareStatement(updateOrderQuery);
            //set the parameter for each column and execute the prepared statement

            pst.setString(1, product.getName());
            pst.setDouble(2, product.getPrice());
            pst.setString(3, product.getImage());
            pst.setString(4, product.getCategory());
            pst.setString(5, product.getSubcategory());
            pst.setString(6, product.getCondition());
            pst.setDouble(7, product.getDiscount());
            pst.setString(8, serializeAccessoryIds(product.getAccessoryIds()));
            pst.setString(9, product.getManufacturer());
            pst.setDouble(10, product.getManufacturerRebate());
            pst.setInt(11, product.getInventoryCount());
            pst.setString(12, product.getId());
            pst.execute();

            productMap.put(product.getId(), product);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public static Map<String, Product> queryProducts(String query) throws ServletException {
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            return convertResultSetToProductMap(pst.executeQuery());
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    public static Map<String, Product> listProducts() throws ServletException {
        if (!productMap.isEmpty()) {
            return productMap;
        }

        try {
            String listQuery = "select * from Products";
            PreparedStatement pst = conn.prepareStatement(listQuery);
            productMap.putAll(convertResultSetToProductMap(pst.executeQuery()));
            return productMap;
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private static Map<String, Product> convertResultSetToProductMap(ResultSet rs) throws SQLException {
        Map<String, Product> tempProductMap = new HashMap<>();
        while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getString("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                product.setCategory(rs.getString("category"));
                product.setSubcategory(rs.getString("subcategory"));
                product.setCondition(rs.getString("condition"));
                product.setDiscount(rs.getDouble("discount"));
                product.setAccessoryIds(deserializeAccessoryIds(rs.getString("accessoryIds")));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setManufacturerRebate(rs.getDouble("manufacturerRebate"));
                product.setInventoryCount(rs.getInt("inventoryCount"));

                tempProductMap.put(product.getId(), product);
        }

        return tempProductMap;
    }

    public static void deleteProduct(String productId) throws ServletException {
        try {
            String deleteStatement = "Delete from Products where id=?";
            PreparedStatement pst = conn.prepareStatement(deleteStatement);
            pst.setString(1, productId);
            pst.execute();

            productMap.remove(productId);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private static String serializeAccessoryIds(Set<String> accessoryIds) {
        StringBuilder builder = new StringBuilder();
        for (String accId : accessoryIds) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(accId);
        }

        return builder.toString();
    }

    private static Set<String> deserializeAccessoryIds(String acccessoryIdsStr) {
        Set<String> idSet = new HashSet<>();

        if (acccessoryIdsStr == null || acccessoryIdsStr.equals("")) {
            return idSet;
        }

        idSet.addAll(Arrays.asList(acccessoryIdsStr.split(",")));

        return idSet;
    }
}