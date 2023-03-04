package carsharing.view;

import carsharing.util.H2DatabaseCreation;

public class CarSharingBuilder {
    private final String databaseFilename;

    private CarSharingBuilder(String databaseFilename) {
        this.databaseFilename = databaseFilename != null ? databaseFilename : "carSharing";
    }

    public static CarSharingBuilder init(String databaseFilename) {
        return new CarSharingBuilder(databaseFilename);
    }

    public CarSharingBuilder withDatabase() {
        H2DatabaseCreation.createDatabaseTable(databaseFilename);
        return this;
    }

    public CarSharing build() {
        return new CarSharing();
    }
}

