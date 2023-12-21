

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class RecommendationSystem {
    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_STEPS = 10000;

    public void storeRecommendations(int userId, List<String> recommendations) {
            String query = "INSERT INTO recommendations (user_id, recommendation) VALUES (?, ?)";
            try (Connection con = DatabaseConnection.getCon();
                            PreparedStatement statement = con.prepareStatement(query)) {
                for (String recommendation : recommendations) {
                            statement.setInt(1, userId);
                            statement.setString(2, recommendation);
                            statement.executeUpdate();
                    }
            } catch (SQLException e) { e.printStackTrace();
        }
    }

    public List<String> getRecommendationsForUser(int userId) {
            List<String> recommendations = new ArrayList<>();
            String query = "SELECT recommendation FROM recommendations WHERE user_id = ?";
            try (Connection con = DatabaseConnection.getCon();
                            PreparedStatement statement = con.prepareStatement(query)) {
                    statement.setInt(1, userId);
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                            recommendations.add(rs.getString("recommendation"));
                    }
            } catch (SQLException e) {e.printStackTrace();
            }
            return recommendations;
    }


    public List<String> generateRecommendations(HealthData healthData) {
        List<String> recommendations = new ArrayList<>();

//        // Analyze heart rate
        int heartRate = healthData.getHeartRate();
        if (heartRate < MIN_HEART_RATE) {
            recommendations.add("Your heart rate is lower than the recommended range. " +
                    "Consider increasing your physical activity to improve your cardiovascular health.");
        } else if (heartRate > MAX_HEART_RATE) {
                recommendations.add("Your heart rate is higher than the recommended range. " +
                                "Please see a doctor for further advice.");
        }
//
//
//        // Analyze steps
        int steps = healthData.getSteps();
        if (steps < MIN_STEPS) {
            recommendations.add("You're not reaching the recommended daily step count. " +
                    "Try to incorporate more walking or other physical activities into your daily routine.");
        }
//
       storeRecommendations(healthData.getUserId(), recommendations);



        return recommendations;
    }
}
