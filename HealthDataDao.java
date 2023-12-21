import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class HealthDataDao {
  DatabaseConnection databaseConnection = new DatabaseConnection();
    public boolean createHealthData(List<HealthData> healthDataList) {
    String query = "INSERT INTO health_data (user_id, weight, height, steps, heart_rate, date) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection con = DatabaseConnection.getCon();
         PreparedStatement statement = con.prepareStatement(query)) {
        for (HealthData healthData : healthDataList) {
            statement.setInt(1, healthData.getUserId());
            statement.setDouble(2, healthData.getWeight());
            statement.setDouble(3, healthData.getHeight());
            statement.setInt(4, healthData.getSteps());
            statement.setInt(5, healthData.getHeartRate());
            statement.setString(6, healthData.getDate());
            statement.addBatch();
        }
        int[] updatedRows = statement.executeBatch();
        return Arrays.stream(updatedRows).allMatch(rowCount -> rowCount > 0);
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public HealthData getHealthDataById(int id) {
        try (Connection connection = DatabaseConnection.getCon()) {
            String query = "SELECT * FROM health_data WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractHealthDataFromResultSet(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<HealthData> getHealthDataByUserId(int userId) {
        List<HealthData> healthDataList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getCon()) {
            String query = "SELECT * FROM health_data WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        HealthData healthData = extractHealthDataFromResultSet(resultSet);
                        healthDataList.add(healthData);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return healthDataList;
    }

    public boolean updateHealthData(HealthData healthData) {
      String query = "UPDATE health_data SET weight = ?, height = ?, steps = ?, heart_rate = ?, date = ? WHERE id = ?";
      try (Connection con = DatabaseConnection.getCon();
          PreparedStatement statement = con.prepareStatement(query)) {
        statement.setDouble(1, healthData.getWeight());
        statement.setDouble(2, healthData.getHeight());
        statement.setInt(3, healthData.getSteps());
        statement.setInt(4, healthData.getHeartRate());
        statement.setString(5, healthData.getDate());
        statement.setInt(6, healthData.getId());
        int updatedRows = statement.executeUpdate();
        return updatedRows > 0;
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
    }

    public boolean addHealthData(int userId, HealthData healthData) {
        boolean success = false;
        String query = "INSERT INTO health_data (user_id, weight, height, steps, heart_rate, date) " +
                "VALUES (?, ?, ?, ?, ?, ?::date)";
        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setDouble(2, healthData.getWeight());
            statement.setDouble(3, healthData.getHeight());
            statement.setInt(4, healthData.getSteps());
            statement.setInt(5, healthData.getHeartRate());
            statement.setString(6, healthData.getDate());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    public boolean deleteHealthData(int id) {
        try (Connection connection = DatabaseConnection.getCon()) {
            String query = "DELETE FROM health_data WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                int updatedRows = statement.executeUpdate();
                return updatedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public HealthData getLatestHealthDataByUserId(int userId) {
        String query = "SELECT * FROM health_data WHERE user_id = ? ORDER BY date DESC LIMIT 1";
        try (Connection con = DatabaseConnection.getCon();
                PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                double weight = rs.getDouble("weight");
                double height = rs.getDouble("height");
                int steps = rs.getInt("steps");
                int heartRate = rs.getInt("heart_rate");
                String date = rs.getString("date");
                HealthData healthData = new HealthData();
                healthData.setId(id);
                healthData.setUserId(userId);
                healthData.setWeight(weight);
                healthData.setHeight(height);
                healthData.setSteps(steps);
                healthData.setHeartRate(heartRate);
                healthData.setDate(date);
                return healthData;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private HealthData extractHealthDataFromResultSet(ResultSet resultSet) throws SQLException {
      HealthData healthData = new HealthData();
      healthData.setId(resultSet.getInt("id"));
      healthData.setUserId(resultSet.getInt("user_id"));
      healthData.setWeight(resultSet.getDouble("weight"));
      healthData.setHeight(resultSet.getDouble("height"));
      healthData.setSteps(resultSet.getInt("steps"));
      healthData.setHeartRate(resultSet.getInt("heart_rate"));
      healthData.setDate(resultSet.getString("date"));
      return healthData;
    }
}