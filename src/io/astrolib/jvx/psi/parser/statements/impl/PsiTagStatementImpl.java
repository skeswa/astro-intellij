package io.astrolib.jvx.psi.parser.statements.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.tree.IElementType;
import io.astrolib.jvx.psi.parser.statements.api.PsiTagStatement;
import org.jetbrains.annotations.NotNull;

/**
 * @author skeswa
 */
public class PsiTagStatementImpl extends CompositePsiElement implements PsiTagStatement {
    PsiTagStatementImpl(IElementType type) {
        super(type);
    }

    @NotNull
    @Override
    public PsiReferenceExpression getTagClassExpression() {
        // Derived from PsiMethodCallExpressionImpl#getMethodExpression.
        final ASTNode node = getFirstChildNode();
        return (PsiReferenceExpression) node;
    }
}
