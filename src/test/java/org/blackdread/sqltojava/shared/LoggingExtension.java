package org.blackdread.sqltojava.shared;


import org.blackdread.sqltojava.shared.interfaces.LoggingTest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.slf4j.LoggerFactory;

public class LoggingExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance,
                                        ExtensionContext context) {

        LoggingTest loggingTest = (LoggingTest) testInstance;
        loggingTest.logger(LoggerFactory.getLogger(context.getTestClass().get()));
    }
}
