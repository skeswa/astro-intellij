package io.astrolib.jvx.psi.stub.impls;

import com.intellij.psi.PsiImportList;
import com.intellij.psi.impl.java.stubs.PsiImportListStub;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import io.astrolib.jvx.psi.stub.JVXStubElementTypes;

/**
 * @author max
 */
public class PsiImportListStubImpl extends StubBase<PsiImportList> implements PsiImportListStub {
    public PsiImportListStubImpl(final StubElement parent) {
        super(parent, JVXStubElementTypes.IMPORT_LIST);
    }

    @SuppressWarnings({"HardCodedStringLiteral"})
    public String toString() {
        return "PsiImportListStub";
    }
}