package io.astrolib.jvx.psi.parser.expressions.api;

import com.intellij.psi.PsiExpression;
import io.astrolib.jvx.psi.elements.JVXElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author skeswa
 */
public interface PsiTagAttributeExpression extends PsiExpression, JVXElementType {
    @NotNull
    String getName();
    @Nullable
    String getValueText();
    @Nullable
    PsiExpression getValueExpression();
}
