package org.blackdread.sqltojava.view;

import java.util.Optional;

/**
 * Methods for comments in the view
 */
public interface JdlCommentView {
    Optional<String> getComment();

    default String getImplComment(String comment) {
        return (comment != null) ? "// " + comment : null;
    }

    default String getDocComment(String comment) {
        return (comment != null) ? "/** " + comment + " */" : null;
    }

    default String getDocComment() {
        return getDocComment(getComment().orElse(null));
    }

    default String getImplComment() {
        return getImplComment(getComment().orElse(null));
    }
}
