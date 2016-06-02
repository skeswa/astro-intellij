package io.astrolib.jvx.psi.elements;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.impl.source.tree.ChildRole;
import com.intellij.psi.impl.source.tree.TreeUtil;
import com.intellij.psi.tree.ChildRoleBase;
import com.intellij.psi.tree.IElementType;

/**
 * @author skeswa
 */
public class JVXAnonymousClassElementBase extends JVXClassElement {
    private static final Logger LOG = Logger.getInstance("#com.intellij.psi.impls.source.tree.java.AnonymousClassElement");

    public JVXAnonymousClassElementBase(IElementType type) {
        super(type);
    }

    @Override
    public ASTNode findChildByRole(int role) {
        LOG.assertTrue(ChildRole.isUnique(role));
        switch(role){
            default:
                return null;

            case ChildRole.BASE_CLASS_REFERENCE:
                return getFirstChildNode().getElementType() == JVXElementType.JAVA_CODE_REFERENCE ? getFirstChildNode() : null;

            case ChildRole.ARGUMENT_LIST:
                return findChildByType(JVXElementType.EXPRESSION_LIST);

            case ChildRole.LBRACE:
                return findChildByType(JavaTokenType.LBRACE);

            case ChildRole.RBRACE:
                return TreeUtil.findChildBackward(this, JavaTokenType.RBRACE);
        }
    }

    @Override
    public int getChildRole(ASTNode child) {
        LOG.assertTrue(child.getTreeParent() == this);
        IElementType i = child.getElementType();
        if (i == JVXElementType.JAVA_CODE_REFERENCE) {
            return getChildRole(child, ChildRole.BASE_CLASS_REFERENCE);
        }
        else if (i == JVXElementType.EXPRESSION_LIST) {
            return ChildRole.ARGUMENT_LIST;
        }
        else if (i == JVXElementType.FIELD) {
            return ChildRole.FIELD;
        }
        else if (i == JVXElementType.METHOD) {
            return ChildRole.METHOD;
        }
        else if (i == JVXElementType.CLASS_INITIALIZER) {
            return ChildRole.CLASS_INITIALIZER;
        }
        else if (i == JVXElementType.CLASS) {
            return ChildRole.CLASS;
        }
        else if (i == JavaTokenType.LBRACE) {
            return getChildRole(child, ChildRole.LBRACE);
        }
        else if (i == JavaTokenType.RBRACE) {
            return getChildRole(child, ChildRole.RBRACE);
        }
        else if (i == JavaTokenType.COMMA) {
            return ChildRole.COMMA;
        }
        else {
            return ChildRoleBase.NONE;
        }
    }
}