package org.blackdread.sqltojava.view.mapper;

import org.blackdread.sqltojava.config.UndefinedJdlTypeHandlingEnum;
import org.blackdread.sqltojava.entity.impl.JdlEntityImpl;
import org.blackdread.sqltojava.entity.impl.JdlFieldImpl;
import org.blackdread.sqltojava.entity.impl.JdlRelationGroupImpl;
import org.blackdread.sqltojava.entity.impl.JdlRelationImpl;
import org.blackdread.sqltojava.view.impl.JdlEntityViewImpl;
import org.blackdread.sqltojava.view.impl.JdlFieldViewImpl;
import org.blackdread.sqltojava.view.impl.JdlRelationGroupViewImpl;
import org.blackdread.sqltojava.view.impl.JdlRelationViewImpl;
import org.mapstruct.Mapper;

/**
 * Generate Mapstruct mapper sources on compile
 */
@Mapper(componentModel = "spring", uses = OptionalUtils.class)
public interface JdlViewMapper {
    JdlEntityViewImpl entityToView(JdlEntityImpl jdlEntity, UndefinedJdlTypeHandlingEnum undefinedJdlTypeHandling);
    JdlFieldViewImpl fieldToView(JdlFieldImpl jdlField);
    JdlRelationViewImpl relationToView(JdlRelationImpl jdlRelation);
    JdlRelationGroupViewImpl relationToView(JdlRelationGroupImpl jdlRelation);
}
