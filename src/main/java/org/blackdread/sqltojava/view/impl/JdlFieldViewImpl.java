package org.blackdread.sqltojava.view.impl;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.blackdread.sqltojava.entity.impl.JdlFieldImpl;
import org.blackdread.sqltojava.service.logic.JdlService;
import org.blackdread.sqltojava.view.JdlFieldView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Immutable
@ThreadSafe
public class JdlFieldViewImpl extends JdlFieldImpl implements JdlFieldView {

    private static final Logger log = LoggerFactory.getLogger(JdlService.class);

    /**
     * Constructory based on super
     * @param type
     * @param name
     * @param required
     * @param comment
     * @param min
     * @param max
     * @param pattern
     * @param enumEntityName
     * @param nativeEnum
     * @param unique
     * @param primaryKey
     */
    public JdlFieldViewImpl(
        JdlFieldEnum type,
        String name,
        boolean required,
        @Nullable String comment,
        @Nullable Integer min,
        @Nullable Integer max,
        @Nullable String pattern,
        @Nullable String enumEntityName,
        Boolean nativeEnum,
        Boolean unique,
        Boolean primaryKey
    ) {
        super(type, name, required, comment, min, max, pattern, enumEntityName, nativeEnum, unique, primaryKey);
    }
}
