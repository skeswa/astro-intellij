package io.astrolib.jvx.psi.parser.statements.impl;

import com.intellij.psi.PsiExpression;
import com.intellij.psi.impl.source.tree.ElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import io.astrolib.jvx.psi.parser.expressions.api.PsiTagAttributeExpression;
import io.astrolib.jvx.psi.parser.statements.api.PsiOpeningTagStatement;
import org.jetbrains.annotations.NotNull;

/**
 * @author skeswa
 */
public class PsiOpeningTagStatementImpl extends PsiTagStatementImpl implements PsiOpeningTagStatement {
    private static final TokenSet ATTRIBUTE_EXPRESSION_FILTER = TokenSet.create(TAG_ATTRIBUTE_EXPRESSION);

    public PsiOpeningTagStatementImpl() {
        super(OPENING_TAG_STATEMENT);
    }

    /** Used by {@link io.astrolib.jvx.psi.parser.statements.api.PsiSelfClosingTagStatement} **/
    PsiOpeningTagStatementImpl(IElementType type) {
        super(type);
    }

    @NotNull
    @Override
    public PsiTagAttributeExpression[] getAttributeExpressions() {
        final PsiExpression[] attributeExpressions = getChildrenAsPsiElements(ElementType.EXPRESSION_BIT_SET, PsiExpression.ARRAY_FACTORY);
        return (PsiTagAttributeExpression[]) attributeExpressions;
    }
}
