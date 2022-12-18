package org.blackdread.sqltojava.test.general;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.*;
import java.util.stream.Collectors;
import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.config.UndefinedJdlTypeHandlingEnum;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.blackdread.sqltojava.entity.JdlRelation;
import org.blackdread.sqltojava.entity.RelationType;
import org.blackdread.sqltojava.entity.impl.JdlEntityImpl;
import org.blackdread.sqltojava.entity.impl.JdlFieldImpl;
import org.blackdread.sqltojava.entity.impl.JdlRelationImpl;
import org.blackdread.sqltojava.service.logic.MustacheService;
import org.blackdread.sqltojava.util.JdlUtils;
import org.blackdread.sqltojava.view.impl.JdlRelationViewImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MustacheTest.MustacheServiceTestContextConfiguration.class })
public class MustacheTest {

    @TestConfiguration
    static class MustacheServiceTestContextConfiguration {

        @Bean
        public MustacheService mustacheService() {
            ApplicationProperties properties = mock(ApplicationProperties.class);
            when(properties.getUndefinedTypeHandling()).thenReturn(UndefinedJdlTypeHandlingEnum.UNSUPPORTED);
            return new MustacheService(properties);
        }
    }

    @Autowired
    private MustacheService mustacheService;

    JdlFieldImpl p = new JdlFieldImpl(JdlFieldEnum.UUID, "id", true, null, null, null, null, null, false, false, true);
    JdlFieldImpl f = new JdlFieldImpl(JdlFieldEnum.STRING, "abrev", true, null, 2, 6, null, null, false, true, false);
    JdlEntityImpl b = new JdlEntityImpl("B", null, List.of(p, f), "Entity B comment", false, false, false, Collections.emptyList());
    JdlRelationImpl relationWithComments = new JdlRelationViewImpl(
        RelationType.ManyToOne,
        true,
        true,
        false,
        "A",
        "B",
        "a",
        null,
        "Owner comment",
        "Inverse comment",
        "b",
        null,
        "Relation comment"
    );

    JdlRelationImpl relationWithoutComments = new JdlRelationViewImpl(
        RelationType.ManyToOne,
        true,
        true,
        false,
        "A",
        "B",
        "a",
        null,
        null,
        null,
        "b",
        null,
        null
    );

    JdlEntityImpl a = new JdlEntityImpl(
        "A",
        null,
        List.of(p, f),
        "Entity A comment",
        false,
        false,
        false,
        List.of(relationWithoutComments)
    );

    @Test
    public void testEntity() {
        String expected =
            """
            /** Entity A comment */
            entity A {
                id UUID,
                abrev String required unique minlength(2) maxlength(6)
            }

            """;
        Map<String, Object> context = new HashMap<>();
        context.put("entities", List.of(a));
        String result = mustacheService.executeTemplate("entities", context);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testRelation() {
        String expected =
            """
            // Relation comment
            relationship ManyToOne {
                /** Owner comment */
                A{a required} to
                /** Inverse comment */
                B{b}
            }

            """;
        String result = mustacheService.executeTemplate("relations", Map.of("relations", List.of(relationWithComments)));
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testRelationWithoutComments() {
        String expected = """
            relationship ManyToOne {
                A{a required} to B{b}
            }

            """;
        String result = mustacheService.executeTemplate("relations", Map.of("relations", List.of(relationWithoutComments)));
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testEntities() {
        String expected =
            """
            /** Entity A comment */
            entity A {
                id UUID,
                abrev String required unique minlength(2) maxlength(6)
            }

            /** Entity B comment */
            entity B {
                id UUID,
                abrev String required unique minlength(2) maxlength(6)
            }

            """;
        List<JdlEntityImpl> entities = Arrays.asList(a, b);
        Map<String, Object> context = new HashMap<>();
        context.put("entities", entities);

        String result = mustacheService.executeTemplate("entities", context);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testNoEntities() {
        String expected =
            """
            // No entities were found for which JDL is to be generated. Please review console logs
            """;
        List<JdlEntityImpl> entities = Collections.emptyList();
        Map<String, Object> context = new HashMap<>();
        context.put("entities", entities);

        String result = mustacheService.executeTemplate("entities", context);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testApplication() {
        String expected =
            """
                /** Entity A comment */
                entity A {
                    id UUID,
                    abrev String required unique minlength(2) maxlength(6)
                }

                /** Entity B comment */
                entity B {
                    id UUID,
                    abrev String required unique minlength(2) maxlength(6)
                }



                // Relations
                relationship ManyToOne {
                    A{a required} to B{b}
                }



                // Options
                service * with serviceClass
                paginate * with pagination
                dto * with mapstruct
                filter *

                """;
        List<JdlEntityImpl> entities = Arrays.asList(a, b);
        List<JdlRelation> relations = entities.stream().flatMap(e -> e.getRelations().stream()).collect(Collectors.toList());

        List<String> options = JdlUtils.getOptions();

        Map<String, Object> context = new HashMap<>();
        context.put("entities", entities);
        context.put("relations", relations);
        context.put("options", options);

        String result = mustacheService.executeTemplate("application", context);
        assertThat(result).isEqualTo(expected);
    }
}
