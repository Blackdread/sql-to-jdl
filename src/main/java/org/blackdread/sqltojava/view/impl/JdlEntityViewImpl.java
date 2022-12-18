package org.blackdread.sqltojava.view.impl;

import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import org.blackdread.sqltojava.config.UndefinedJdlTypeHandlingEnum;
import org.blackdread.sqltojava.entity.JdlField;
import org.blackdread.sqltojava.entity.JdlRelation;
import org.blackdread.sqltojava.entity.impl.JdlEntityImpl;
import org.blackdread.sqltojava.view.JdlEntityView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Immutable
@ThreadSafe
public class JdlEntityViewImpl extends JdlEntityImpl implements JdlEntityView {

    private static final Logger log = LoggerFactory.getLogger(JdlEntityViewImpl.class);

    private final UndefinedJdlTypeHandlingEnum undefinedJdlTypeHandling;

    /**
     * Constructory based on super
     * @param name
     * @param tableName
     * @param fields
     * @param comment
     * @param enumEntity
     * @param readOnly
     * @param pureManyToMany
     * @param relations
     */
    public JdlEntityViewImpl(
        String name,
        String tableName,
        List<JdlField> fields,
        @Nullable String comment,
        boolean enumEntity,
        boolean readOnly,
        boolean pureManyToMany,
        List<JdlRelation> relations,
        UndefinedJdlTypeHandlingEnum undefinedJdlTypeHandling
    ) {
        super(name, tableName, fields, comment, enumEntity, readOnly, pureManyToMany, relations);
        this.undefinedJdlTypeHandling = undefinedJdlTypeHandling;
    }

    @Override
    public UndefinedJdlTypeHandlingEnum getUndefinedJdlTypeHandling() {
        return undefinedJdlTypeHandling;
    }
}
