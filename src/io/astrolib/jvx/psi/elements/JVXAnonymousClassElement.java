package io.astrolib.jvx.psi.elements;

import com.intellij.psi.impl.source.tree.java.AnonymousClassElementBase;

/**
 * @author skeswa
 */
public class JVXAnonymousClassElement extends AnonymousClassElementBase {
    public JVXAnonymousClassElement() {
        super(JVXElementType.ANONYMOUS_CLASS);
    }
}
