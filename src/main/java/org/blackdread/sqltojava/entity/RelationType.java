package org.blackdread.sqltojava.entity;

/**
 * Created by Yoann CAPLAIN on 2017/10/6.
 */
public enum RelationType {
    /**
     * @deprecated no reason to keep that, exception way before should happen
     */
    @Deprecated
    Unknown,
    /**
     * E.g: Can be known if sql column is unique
     */
    OneToOne,
    ManyToOne,
    ManyToMany;
    // OneToMany is not put as we always use ManyToOne

    public String toJdl(){
        return this.name();
    }
}
