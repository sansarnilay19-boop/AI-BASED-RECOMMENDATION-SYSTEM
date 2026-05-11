package codTech.RecommendationSystem;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class ProductRecommender {

    public static void main(String[] args) {
        try {

            URL resource = ProductRecommender.class
                    .getClassLoader()
                    .getResource("data.csv");

            if (resource == null) {
                throw new RuntimeException("data.csv not found!");
            }

            File file = new File(resource.toURI());
            DataModel model = new FileDataModel(file);

            UserSimilarity similarity =
                    new PearsonCorrelationSimilarity(model);

            UserNeighborhood neighborhood =
                    new NearestNUserNeighborhood(2, similarity, model);

            GenericUserBasedRecommender recommender =
                    new GenericUserBasedRecommender(model,
                            neighborhood, similarity);

            List<RecommendedItem> recommendations =
                    recommender.recommend(1, 3);

            System.out.println("Recommended Products for User 1:");

            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Product ID: "
                        + recommendation.getItemID()
                        + " | Predicted Rating: "
                        + recommendation.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}