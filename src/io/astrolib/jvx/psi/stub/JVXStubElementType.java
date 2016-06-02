package io.astrolib.jvx.psi.stub;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import com.intellij.psi.impl.java.stubs.StubPsiFactory;
import com.intellij.psi.stubs.ILightStubElementType;
import com.intellij.psi.stubs.PsiFileStub;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.ICompositeElementType;
import io.astrolib.jvx.psi.JVXLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author skeswa
 */
public abstract class JVXStubElementType<StubT extends StubElement, PsiT extends PsiElement>
        extends ILightStubElementType<StubT, PsiT> implements ICompositeElementType {
    private final boolean myLeftBound;

    protected JVXStubElementType(@NotNull @NonNls final String debugName) {
        this(debugName, false);
    }

    protected JVXStubElementType(@NotNull @NonNls final String debugName, final boolean leftBound) {
        super(debugName, JVXLanguage.INSTANCE);
        myLeftBound = leftBound;
    }

    @NotNull
    @Override
    public String getExternalId() {
        return "jvx." + toString();
    }

    protected StubPsiFactory getPsiFactory(StubT stub) {
        return getFileStub(stub).getPsiFactory();
    }

    public boolean isCompiled(StubT stub) {
        return getFileStub(stub).isCompiled();
    }

    private PsiJavaFileStub getFileStub(StubT stub) {
        StubElement parent = stub;
        while (!(parent instanceof PsiFileStub)) {
            parent = parent.getParentStub();
        }

        return (PsiJavaFileStub) parent;
    }

    @SuppressWarnings("MethodOverloadsMethodOfSuperclass")
    public abstract PsiT createPsi(@NotNull ASTNode node);

    @Override
    public final StubT createStub(@NotNull final PsiT psi, final StubElement parentStub) {
        final String message = "Should not be called. Element=" + psi + "; class" + psi.getClass() + "; file=" + (psi.isValid() ? psi.getContainingFile() : "-");
        throw new UnsupportedOperationException(message);
    }

    @Override
    public boolean isLeftBound() {
        return myLeftBound;
    }
}