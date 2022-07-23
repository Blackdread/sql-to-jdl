package org.blackdread.sqltojava.extension;

import org.blackdread.sqltojava.shared.JdbcDatabaseContainerExtension;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.utility.DockerImageName;

public class MsSqlLatestExtension extends JdbcDatabaseContainerExtension {

    @Override
    protected JdbcDatabaseContainer createContainer() {
        return new MSSQLServerContainer(DockerImageName.parse("mcr.microsoft.com/mssql/server")
                                                       .withTag("2022-latest"))
                                                       .acceptLicense();
    }

}
