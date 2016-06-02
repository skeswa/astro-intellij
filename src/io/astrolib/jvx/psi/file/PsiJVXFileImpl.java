package io.astrolib.jvx.psi.file;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import io.astrolib.jvx.psi.stub.JVXStubElementTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author skeswa
 */
public class PsiJVXFileImpl extends PsiJVXFileBaseImpl {
    public PsiJVXFileImpl(FileViewProvider file) {
        super(JVXStubElementTypes.JVX_FILE, JVXStubElementTypes.JVX_FILE, file);
    }

    @Override
    @NotNull
    public FileType getFileType() {
        return JVXFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "PsiJVXFile:" + getName();
    }
}