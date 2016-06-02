package io.astrolib.jvx.psi.stub.elements;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.impl.java.stubs.PsiImportListStub;
import com.intellij.psi.impl.source.PsiImportListImpl;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.astrolib.jvx.psi.elements.JVXImportListElement;
import io.astrolib.jvx.psi.stub.impls.PsiImportListStubImpl;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author skeswa
 */
public class JVXImportListElementType extends JVXStubElementType<PsiImportListStub, PsiImportList> {
    public JVXImportListElementType() {
        super("IMPORT_LIST");
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new JVXImportListElement();
    }

    @Override
    public PsiImportList createPsi(@NotNull final PsiImportListStub stub) {
        return getPsiFactory(stub).createImportList(stub);
    }

    @Override
    public PsiImportList createPsi(@NotNull final ASTNode node) {
        return new PsiImportListImpl(node);
    }

    @Override
    public PsiImportListStub createStub(final LighterAST tree, final LighterASTNode node, final StubElement parentStub) {
        return new PsiImportListStubImpl(parentStub);
    }

    @Override
    public void serialize(@NotNull final PsiImportListStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
    }

    @NotNull
    @Override
    public PsiImportListStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new PsiImportListStubImpl(parentStub);
    }

    @Override
    public void indexStub(@NotNull final PsiImportListStub stub, @NotNull final IndexSink sink) {
    }
}