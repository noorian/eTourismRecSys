package com.mmsdivino.recommendation;

import net.librec.conf.Configuration;
import net.librec.data.DataModel;
import net.librec.data.model.TextDataModel;
import net.librec.eval.RecommenderEvaluator;
import net.librec.eval.rating.MAEEvaluator;
import net.librec.filter.GenericRecommendedFilter;
import net.librec.filter.RecommendedFilter;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.cf.UserKNNRecommender;
import net.librec.recommender.item.GenericRecommendedItem;
import net.librec.similarity.PCCSimilarity;
import net.librec.similarity.RecommenderSimilarity;

import java.util.Iterator;
import java.util.List;

public class Driver {

    public static void getRecommendations() {
        try {
            // recommender configuration
            Configuration conf = new Configuration();
            conf.set("dfs.data.dir", "data");
            conf.set("data.input.path", "googlemaps/rating");
            conf.set("dfs.result.dir", "result");
            conf.set("rec.neighbors.knn.number", "50");
            conf.set("rec.recommender.isranking", "false");

            // K-fold
//        conf.set("data.splitter.cv.number","5");
            conf.set("rec.recommender.ranking.topn", "0");

            // build data model
            DataModel dataModel = new TextDataModel(conf);
            dataModel.buildDataModel();

            // set recommendation context
            RecommenderContext context = new RecommenderContext(conf, dataModel);
            RecommenderSimilarity similarity = new PCCSimilarity();
            similarity.buildSimilarityMatrix(dataModel);
            context.setSimilarity(similarity);

            // training
            Recommender recommender = new UserKNNRecommender();
            recommender.recommend(context);

            // evaluation
            RecommenderEvaluator evaluator = new MAEEvaluator();
            recommender.evaluate(evaluator);

            // recommendation results
            List recommendedItemList = recommender.getRecommendedList();
            RecommendedFilter filter = new GenericRecommendedFilter();
            recommendedItemList = filter.filter(recommendedItemList);

            for (Iterator<GenericRecommendedItem> i = recommendedItemList.iterator(); i.hasNext(); ) {
                GenericRecommendedItem item = i.next();
                System.out.println("itemID: " + item.getItemId() + "; userID: " + item.getUserId() + "; value: " + item.getValue());
            }
            System.out.print("Finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}