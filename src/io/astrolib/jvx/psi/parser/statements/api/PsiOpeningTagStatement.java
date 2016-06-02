package io.astrolib.jvx.psi.parser.statements.api;

import io.astrolib.jvx.psi.parser.expressions.api.PsiTagAttributeExpression;
import org.jetbrains.annotations.NotNull;

/**
 * @author skeswa
 */
public interface PsiOpeningTagStatement extends PsiTagStatement {
    @NotNull PsiTagAttributeExpression[] getAttributeExpressions();
}
