package org.blackdread.sqltojava.entity;


import java.util.Optional;

/**
 * Created by Yoann CAPLAIN on 2017/10/6.
 */
public interface JdlField {

    JdlFieldEnum getType();

    String getName();

    /**
     * @return Entity name of the enum if this field is an enum value
     */
    Optional<String> getEnumEntityName();

    boolean isRequired();

    Optional<String> getComment();

    /**
     * Min of field (represent minLength if is a string)
     *
     * @return Min value of field
     */
    Optional<Integer> getMin();

    /**
     * Max of field (represent miaxength if is a string)
     *
     * @return Max value of field
     */
    Optional<Integer> getMax();

    Optional<String> getPattern();

}
