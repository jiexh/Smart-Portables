import java.util.Date;

public class Review {
    private String productId;
    private String productCategory;
    private double productPrice;
    private String productManufacturer;
    private boolean productOnSale;
    private boolean productManufacturerRebate;
    private String retailerName;
    private String retailerZip;
    private String retailerCity;
    private String retailerState;
    private String userId;
    private int userAge;
    private String userGender;
    private String userOccupation;
    private int reviewRating;
    private Date reviewDate;
    private String reviewText;

    public Review() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(final String productCategory) {
        this.productCategory = productCategory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(final double productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isProductOnSale() {
        return productOnSale;
    }

    public void setProductOnSale(final boolean productOnSale) {
        this.productOnSale = productOnSale;
    }

    public boolean isProductManufacturerRebate() {
        return productManufacturerRebate;
    }

    public void setProductManufacturerRebate(final boolean productManufacturerRebate) {
        this.productManufacturerRebate = productManufacturerRebate;
    }


    public String getProductManufacturer() {
        return productManufacturer;
    }

    public void setProductManufacturer(final String productManufacturer) {
        this.productManufacturer = productManufacturer;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(final String retailerName) {
        this.retailerName = retailerName;
    }

    public String getRetailerZip() {
        return retailerZip;
    }

    public void setRetailerZip(final String retailerZip) {
        this.retailerZip = retailerZip;
    }

    public String getRetailerCity() {
        return retailerCity;
    }

    public void setRetailerCity(final String retailerCity) {
        this.retailerCity = retailerCity;
    }

    public String getRetailerState() {
        return retailerState;
    }

    public void setRetailerState(final String retailerState) {
        this.retailerState = retailerState;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(final int userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(final String userGender) {
        this.userGender = userGender;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(final String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(final int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(final Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(final String reviewText) {
        this.reviewText = reviewText;
    }

    @Override
    public String toString() {
        return "Review{" +
                "productId='" + productId + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productPrice=" + productPrice +
                ", productManufacturer='" + productManufacturer + '\'' +
                ", productOnSale=" + productOnSale +
                ", productManufacturerRebate=" + productManufacturerRebate +
                ", retailerName='" + retailerName + '\'' +
                ", retailerZip='" + retailerZip + '\'' +
                ", retailerCity='" + retailerCity + '\'' +
                ", retailerState='" + retailerState + '\'' +
                ", userId='" + userId + '\'' +
                ", userAge=" + userAge +
                ", userGender='" + userGender + '\'' +
                ", userOccupation='" + userOccupation + '\'' +
                ", reviewRating=" + reviewRating +
                ", reviewDate=" + reviewDate +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}
