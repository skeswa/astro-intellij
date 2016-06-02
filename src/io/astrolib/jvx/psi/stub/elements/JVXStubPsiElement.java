package io.astrolib.jvx.psi.stub.elements;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.impl.source.JavaStubPsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import io.astrolib.jvx.psi.JVXLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * @author skeswa
 */
public class JVXStubPsiElement<T extends StubElement> extends JavaStubPsiElement<T> {
    public JVXStubPsiElement(@NotNull T stub, @NotNull IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public JVXStubPsiElement(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    @NotNull
    public Language getLanguage() {
        return JVXLanguage.INSTANCE;
    }
}
