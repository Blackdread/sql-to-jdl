package org.blackdread.sqltojava.entity;

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

    public String toJdl() {
        return this.name();
    }
}
