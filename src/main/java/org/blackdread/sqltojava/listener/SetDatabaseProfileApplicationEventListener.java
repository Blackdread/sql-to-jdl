package org.blackdread.sqltojava.listener;

import org.blackdread.sqltojava.util.JdbcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

public class SetDatabaseProfileApplicationEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger log = LoggerFactory.getLogger(SetDatabaseProfileApplicationEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent e) {
        ConfigurableEnvironment env = e.getEnvironment();
        String[] activeProfiles = env.getActiveProfiles();

        if (activeProfiles.length == 0) {
            String jdbcUrl = env.getProperty("spring.datasource.url");
            String profileName = JdbcUtil.getDatabaseTypeFromJdbcUrl(jdbcUrl);
            log.debug(String.format("No active profiles using %s from %s", profileName, jdbcUrl));
            env.setActiveProfiles(profileName);
        } else {
            log.debug(String.format("Active profile %s found.  Automatic setting from spring.datasource.url disabled.", activeProfiles));
        }
    }
}
