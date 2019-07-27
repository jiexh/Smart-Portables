import java.io.Serializable;

public class OrderPayment implements Serializable {
    public static final long DELIVERY_SCHEDULE_IN_MILLI = 14 * 24 * 60 * 60 * 1000L; // 14 days in milliseconds
    public static final long CANCEL_CUTOFF_SCHEDULE_IN_MILLI = 5 * 24 * 60 * 60 * 1000L; // 5 days;

    private int orderId;
    private String userName;
    private String itemId;
    private double itemPrice;
    private String userAddress;
    private String creditCardNo;
    private long deliveryTime;
    private long orderTime;

    public OrderPayment(final int orderId, final String userName, final String itemId, final double itemPrice, final String userAddress, final String creditCardNo, final long deliveryTime, final long orderTime) {
        this.orderId = orderId;
        this.userName = userName;
        this.itemId = itemId;
        this.itemPrice = itemPrice;
        this.userAddress = userAddress;
        this.creditCardNo = creditCardNo;
        this.deliveryTime = deliveryTime;
        this.orderTime = orderTime;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(final long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(final long orderTime) {
        this.orderTime = orderTime;
    }
}
