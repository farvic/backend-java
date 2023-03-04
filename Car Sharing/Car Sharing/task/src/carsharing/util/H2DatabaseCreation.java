package carsharing.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

import static carsharing.repositories.BaseRepository.fromPropertyKey;
import static carsharing.repositories.BaseRepository.getPath;

public class H2DatabaseCreation {

    public static void createDatabaseTable(String databaseFilename) {

        try (Connection connection = DriverManager.getConnection(getPath(databaseFilename))) {

            connection.setAutoCommit(true);

            Statement statement = connection.createStatement();
            statement.executeUpdate(fromPropertyKey("TABLE_COMPANY_CREATION_WHEN_NOT_EXISTS_QUERY"));

//            statement = connection.createStatement();
//            statement.executeUpdate(fromPropertyKey("TABLE_CAR_CREATION_WHEN_NOT_EXITS_QUERY"));
//
//            statement = connection.createStatement();
//            statement.executeUpdate(fromPropertyKey("TABLE_CUSTOMER_CREATION_WHEN_NOT_EXISTS_QUERY"));

            statement.close();
//            connection.commit();

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }
}
