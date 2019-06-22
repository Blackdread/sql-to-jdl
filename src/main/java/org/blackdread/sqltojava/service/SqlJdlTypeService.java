package org.blackdread.sqltojava.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
@Service
public class SqlJdlTypeService {

    private static final Logger log = LoggerFactory.getLogger(SqlJdlTypeService.class);

    @PostConstruct
    private void init() {
        final List<String> total = Streams.concat(
            jdlString().stream(),
            jdlBigDecimal().stream(),
            jdlBoolean().stream(),
            jdlDouble().stream(),
            jdlEnum().stream(),
            jdlFloat().stream(),
            jdlInstant().stream(),
            jdlInteger().stream(),
            jdlLocalDate().stream(),
            jdlLong().stream(),
            jdlZonedDateTime().stream(),
            jdlTextBlob().stream(),
            jdlTime().stream(),
            jdlGeometry().stream()
        ).collect(Collectors.toList());

        final HashSet<String> setTotal = new HashSet<>(total);
        if (setTotal.size() != total.size()) {
            log.error("Duplicate values: {}, {}", total, setTotal);
            throw new IllegalStateException("Found duplicate values");
        }
    }

    public JdlFieldEnum sqlTypeToJdlType(final String sqlType) {
        final String type = sqlType.toLowerCase();


        if (isTypeContained(jdlInstant(), type)) {
            return JdlFieldEnum.INSTANT;
        }

        if (isTypeContained(jdlZonedDateTime(), type)) {
            return JdlFieldEnum.ZONED_DATE_TIME;
        }

        if (isTypeContained(jdlLocalDate(), type)) {
            return JdlFieldEnum.LOCAL_DATE;
        }

        if (isTypeContained(jdlTime(), type)) {
            return JdlFieldEnum.TIME_AS_TEXT;
        }

        if (isTypeContained(jdlString(), type)) {
            return JdlFieldEnum.STRING;
        }

        if (isTypeContained(jdlTextBlob(), type)) {
            return JdlFieldEnum.TEXT_BLOB;
        }

        if (isTypeContained(jdlInteger(), type)) {
            return JdlFieldEnum.INTEGER;
        }

        if (isTypeContained(jdlLong(), type)) {
            return JdlFieldEnum.LONG;
        }

        if (isTypeContained(jdlDouble(), type)) {
            return JdlFieldEnum.DOUBLE;
        }

        if (isTypeContained(jdlFloat(), type)) {
            return JdlFieldEnum.FLOAT;
        }

        if (isTypeContained(jdlEnum(), type)) {
            return JdlFieldEnum.ENUM;
        }

        if (isTypeContained(jdlBoolean(), type)) {
            return JdlFieldEnum.BOOLEAN;
        }

        if (isTypeContained(jdlBigDecimal(), type)) {
            return JdlFieldEnum.BIG_DECIMAL;
        }

        if (isTypeContained(jdlGeometry(), type)) {
            return JdlFieldEnum.GEOMETRY_AS_TEXT;
        }

        throw new IllegalStateException("Unknown type: " + type);
    }

    private boolean isTypeContained(final List<String> list, final String type) {
        return list.stream().anyMatch(type::startsWith);
    }

    protected List<String> jdlTime() {
        return Lists.newArrayList("time");
    }

    protected List<String> jdlString() {
        return Lists.newArrayList("varchar", "char", "text", "tinytext");
    }

    protected List<String> jdlTextBlob() {
        return Lists.newArrayList("longtext", "mediumtext");
    }

    protected List<String> jdlInteger() {
        return Lists.newArrayList("smallint", "int");
    }

    protected List<String> jdlLong() {
        return Lists.newArrayList("bigint");
    }

    protected List<String> jdlDouble() {
        return Lists.newArrayList("double");
    }

    protected List<String> jdlFloat() {
        return Lists.newArrayList("float");
    }

    protected List<String> jdlLocalDate() {
        return Lists.newArrayList("date");
    }

    protected List<String> jdlEnum() {
        return Lists.newArrayList("enum");
    }

    protected List<String> jdlBoolean() {
        return Lists.newArrayList("boolean", "tinyint", "bit");
    }

    protected List<String> jdlBigDecimal() {
        return Lists.newArrayList("decimal");
    }

    protected List<String> jdlInstant() {
        return Lists.newArrayList("datetime", "timestamp");
    }

    protected List<String> jdlZonedDateTime() {
        return Collections.emptyList();
    }

    /**
     * Not support by base jhipster but to export database which has this type.
     * See:
     * https://blog.ippon.fr/2017/12/04/la-technologie-spatiale-au-service-de-jhipster/
     * https://github.com/chegola/jhipster-spatial
     * https://stackoverflow.com/questions/50122390/integration-of-postgis-with-jhipster
     **/
    protected List<String> jdlGeometry() {
        return Lists.newArrayList("geometry");
    }


}
