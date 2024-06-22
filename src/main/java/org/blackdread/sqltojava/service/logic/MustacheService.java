package org.blackdread.sqltojava.service.logic;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.reflect.ReflectionObjectHandler;
import com.github.mustachejava.util.DecoratedCollection;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;
import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.entity.impl.JdlEntityImpl;
import org.blackdread.sqltojava.entity.impl.JdlFieldImpl;
import org.blackdread.sqltojava.entity.impl.JdlRelationGroupImpl;
import org.blackdread.sqltojava.entity.impl.JdlRelationImpl;
import org.blackdread.sqltojava.view.mapper.JdlViewMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MustacheService {

    private static final Logger log = LoggerFactory.getLogger(MustacheService.class);
    private static final String MUSTACHE_EXTENSION = ".mustache";
    private final MustacheFactory mf;

    private final ApplicationProperties properties;

    public MustacheService(ApplicationProperties properties) {
        this.mf = getMustacheFactory();
        this.properties = properties;
    }

    /**
     * Load templates from resources/mustache.  The assumed extension is .mustache so only the file name needs to be specified.
     * @param template
     * @param context
     * @return
     */
    public String executeTemplate(String template, Map<String, Object> context) {
        Mustache m = mf.compile(template + MUSTACHE_EXTENSION);
        try {
            StringWriter writer = new StringWriter();
            m.execute(writer, context).flush();
            return replaceMultpleBlankLinesWithOne(writer.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String replaceMultpleBlankLinesWithOne(String s) {
        return s.replaceAll("([ \\tw]*\\n){3,}", "\\\n\\\n").replaceAll("\\n\\n$", "\\\n");
    }

    /**
     * Create a MustacheFactory with a MapstructBasedObjectHandler
     * @return MustacheFactory
     */
    private MustacheFactory getMustacheFactory() {
        DefaultMustacheFactory mf = new DefaultMustacheFactory("mustache");
        mf.setObjectHandler(new MapstructBasedObjectHandler());
        return mf;
    }

    /**
     * Use Mapstruct to map the JDL model to view DTOs for rendering with mustache.java
     */
    private class MapstructBasedObjectHandler extends ReflectionObjectHandler {

        private static JdlViewMapperImpl mapper = new JdlViewMapperImpl();

        @Override
        public Object coerce(Object o) {
            if (o instanceof JdlRelationGroupImpl) return mapper.relationToView((JdlRelationGroupImpl) o);
            if (o instanceof JdlEntityImpl) return mapper.entityToView((JdlEntityImpl) o, properties.getUndefinedTypeHandling());
            if (o instanceof JdlFieldImpl) return mapper.fieldToView((JdlFieldImpl) o);
            if (o instanceof JdlRelationImpl) return mapper.relationToView((JdlRelationImpl) o);
            if (o instanceof Collection) return new DecoratedCollection((Collection) o);
            return super.coerce(o);
        }
    }
}
