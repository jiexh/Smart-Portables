import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;

public class MongoDBDataStoreUtilities {
    private static MongoCollection<Document> REVIEWS;
    private static Gson GSON = new GsonBuilder().create();

    public static void initConnection() {
        REVIEWS = new MongoClient("127.0.0.1", 27017)
                .getDatabase("SmartPortables")
                .getCollection("CustomerReviews");
    }

    public static List<Review> listReviews() {
        List<Review> reviews = new ArrayList<>();

        for (Document document : REVIEWS.find()) {
            reviews.add(GSON.fromJson(document.toJson(), Review.class));
        }

        return reviews;
    }

    public static List<Review> getReviewsByProductId(String productId) {
        List<Review> reviews = new ArrayList<>();

        for (Review review : listReviews()) {
            if (review.getProductId().equals(productId)) {
                reviews.add(review);
            }
        }

        return reviews;
    }

    public static void insertReview(Review review) {
        REVIEWS.insertOne(Document.parse(GSON.toJson(review)));
    }

    public static List<KeyValuePair> topFiveMostLikedProducts() {
        List<KeyValuePair> pairs = new ArrayList<>();

        List<Bson> pipe = new ArrayList<>();
        pipe.add(Aggregates.group("$productId", Accumulators.avg("avgRating", "$reviewRating")));
        pipe.add(Aggregates.sort(Sorts.descending("avgRating")));
        pipe.add(Aggregates.limit(5));
        //pipe.add(Updates.rename("_id", "productId"));

        for (Document document : REVIEWS.aggregate(pipe)) {
            pairs.add(new KeyValuePair<>(document.getString("_id"), document.getDouble("avgRating")));
        }

        return pairs;
    }

    public static List<KeyValuePair> topFiveZipCodesSoldMostProducts() {
        List<KeyValuePair> pairs = new ArrayList<>();

        List<Bson> pipe = new ArrayList<>();
        pipe.add(Aggregates.sortByCount("$retailerZip"));
        pipe.add(Aggregates.limit(5));

        for (Document document : REVIEWS.aggregate(pipe)) {
            pairs.add(new KeyValuePair<>(document.getString("_id"), document.getInteger("count")));
        }

        return pairs;
    }

    public static List<KeyValuePair> topFiveMostSoldProducts() {
        List<KeyValuePair> pairs = new ArrayList<>();

        List<Bson> pipe = new ArrayList<>();
        pipe.add(Aggregates.sortByCount("$productId"));
        pipe.add(Aggregates.limit(5));

        for (Document document : REVIEWS.aggregate(pipe)) {
            pairs.add(new KeyValuePair<>(document.getString("_id"), document.getInteger("count")));
        }

        return pairs;
    }
}
