package org.blackdread.sqltojava.util;

import org.blackdread.sqltojava.listener.SetDatabaseProfileApplicationEventListener;
import org.springframework.boot.SpringApplication;

public final class AppUtil {

    /**
     * Utility method to configure application the same in both tests and production since the
     * main method is not called when the tests run and the database profile needs to be set in
     * ApplicationEnvironmentPreparedEvent which runs before the context is loaded.
     * @param app
     * @return
     */
    public static SpringApplication setup(SpringApplication app) {
        app.addListeners(new SetDatabaseProfileApplicationEventListener());
        return app;
    }
}
