package io.astrolib.jvx.psi.parser.expressions.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.psi.impl.ResolveScopeManager;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import io.astrolib.jvx.psi.parser.expressions.api.PsiTagAttributeExpression;
import org.jetbrains.annotations.Nullable;

import static com.intellij.psi.impl.source.tree.ElementType.EXPRESSION_BIT_SET;

/**
 * @author skeswa
 */
public class PsiTagAttributeExpressionImpl extends CompositePsiElement implements PsiTagAttributeExpression {
    private static final Logger LOG = Logger.getInstance("#io.astrolib.jvx.psi.parser.expressions.impls.PsiTagAttributeExpressionImpl");

    public PsiTagAttributeExpressionImpl() {
        super(TAG_ATTRIBUTE_EXPRESSION);
    }

    @Override
    public String getName() {
        final ASTNode node = findChildByType(JavaTokenType.IDENTIFIER);
        return node == null ? null : node.getText();
    }

    @Nullable
    @Override
    public String getValueText() {
        final ASTNode node = findChildByType(JavaTokenType.STRING_LITERAL);
        return node == null ? null : node.getText();
    }

    @Nullable
    @Override
    public PsiExpression getValueExpression() {
        final ASTNode node = findChildByType(EXPRESSION_BIT_SET);
        return node == null ? null : (PsiExpression) node.getPsi();
    }

    @Nullable
    @Override
    public PsiType getType() {
        PsiExpression expr;

        // Tag attribute type is represented by the type of its value.
        if (getValueText() != null) {
            // Value is a string literal.
            PsiManagerEx manager = getManager();
            GlobalSearchScope resolveScope = ResolveScopeManager.getElementResolveScope(this);
            return PsiType.getJavaLangString(manager, resolveScope);
        } else if ((expr = getValueExpression()) != null) {
            // Value is a bracketed expression.
            return expr.getType();
        } else {
            // If there is no value, treat this attribute like a flag.
            return PsiType.BOOLEAN;
        }
    }
}
