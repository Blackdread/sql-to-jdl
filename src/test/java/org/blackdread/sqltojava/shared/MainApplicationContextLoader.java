package org.blackdread.sqltojava.shared;

import org.blackdread.sqltojava.util.AppUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;

/**
 * Using this context loader is the same as what is loaded in main method via
 * the AppUtil.setup method.
 */
public class MainApplicationContextLoader extends SpringBootContextLoader {

    @Override
    protected SpringApplication getSpringApplication() {
        return AppUtil.setup(super.getSpringApplication());
    }

}
