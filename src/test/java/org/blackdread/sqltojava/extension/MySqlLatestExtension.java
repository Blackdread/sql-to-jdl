package org.blackdread.sqltojava.extension;

import org.blackdread.sqltojava.shared.JdbcDatabaseContainerExtension;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class MySqlLatestExtension extends JdbcDatabaseContainerExtension {

    @Override
    protected JdbcDatabaseContainer createContainer() {
        return new MySQLContainer(DockerImageName.parse("mysql:latest"));
    }
}
