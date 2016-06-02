package io.astrolib.jvx.psi.parser.statements.api;

import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiStatement;
import io.astrolib.jvx.psi.elements.JVXElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author skeswa
 */
public interface PsiTagStatement extends PsiStatement, JVXElementType {
    @NotNull PsiReferenceExpression getTagClassExpression();
}
