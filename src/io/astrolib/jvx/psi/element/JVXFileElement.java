package io.astrolib.jvx.psi.element;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.impl.source.SourceTreeToPsiMap;
import com.intellij.psi.impl.source.tree.ChildRole;
import com.intellij.psi.impl.source.tree.FileElement;
import com.intellij.psi.impl.source.tree.TreeElement;
import com.intellij.psi.tree.ChildRoleBase;
import com.intellij.psi.tree.IElementType;
import io.astrolib.jvx.psi.stub.JVXStubElementTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by skeswa on 6/1/16.
 */
public class JVXFileElement extends FileElement {
    private static final Logger LOG = Logger.getInstance("#io.astrolib.jvx.JVXFileElement");

    public JVXFileElement(CharSequence text) {
        super(JVXStubElementTypes.JVX_FILE, text);
    }

    @Override
    public void deleteChildInternal(@NotNull ASTNode child) {
        if (child.getElementType() == JVXElementType.CLASS) {
            PsiJavaFile file = SourceTreeToPsiMap.treeToPsiNotNull(this);
            if (file.getClasses().length == 1) {
                file.delete();
                return;
            }
        }
        super.deleteChildInternal(child);
    }

    @Override
    @Nullable
    public ASTNode findChildByRole(int role) {
        LOG.assertTrue(ChildRole.isUnique(role));
        switch (role) {
            default:
                return null;

            case ChildRole.PACKAGE_STATEMENT:
                return findChildByType(JVXElementType.PACKAGE_STATEMENT);

            case ChildRole.IMPORT_LIST:
                return findChildByType(JVXElementType.IMPORT_LIST);
        }
    }

    @Override
    public int getChildRole(ASTNode child) {
        LOG.assertTrue(child.getTreeParent() == this);
        IElementType i = child.getElementType();
        if (i == JVXElementType.PACKAGE_STATEMENT) {
            return ChildRole.PACKAGE_STATEMENT;
        }
        else if (i == JVXElementType.IMPORT_LIST) {
            return ChildRole.IMPORT_LIST;
        }
        else if (i == JVXElementType.CLASS) {
            return ChildRole.CLASS;
        }
        else {
            return ChildRoleBase.NONE;
        }
    }

    @Override
    public void replaceChildInternal(@NotNull ASTNode child, @NotNull TreeElement newElement) {
        if (newElement.getElementType() == JVXElementType.IMPORT_LIST) {
            LOG.assertTrue(child.getElementType() == JVXElementType.IMPORT_LIST);
            if (newElement.getFirstChildNode() == null) { //empty import list
                ASTNode next = child.getTreeNext();
                if (next != null && next.getElementType() == TokenType.WHITE_SPACE) {
                    removeChild(next);
                }
            }
        }
        super.replaceChildInternal(child, newElement);
    }
}