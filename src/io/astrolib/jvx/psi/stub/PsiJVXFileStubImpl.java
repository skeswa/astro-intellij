package io.astrolib.jvx.psi.stub;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.impl.java.stubs.*;
import com.intellij.psi.stubs.PsiFileStubImpl;
import com.intellij.psi.tree.IStubFileElementType;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.io.StringRef;

/**
 * Created by skeswa on 6/1/16.
 */
public class PsiJVXFileStubImpl extends PsiFileStubImpl<PsiJavaFile> implements PsiJavaFileStub {
    private final StringRef myPackageName;
    private final boolean myCompiled;
    private StubPsiFactory myFactory;

    public PsiJVXFileStubImpl(PsiJavaFile file, StringRef packageName, boolean compiled) {
        super(file);
        myPackageName = packageName;
        myCompiled = compiled;
        myFactory = compiled ? new ClsStubPsiFactory() : new SourceStubPsiFactory();
    }

    public PsiJVXFileStubImpl(String packageName, boolean compiled) {
        this(null, StringRef.fromString(packageName), compiled);
    }

    @Override
    public IStubFileElementType getType() {
        return JVXStubElementTypes.JVX_FILE;
    }

    @Override
    public PsiClass[] getClasses() {
        return getChildrenByType(JavaStubElementTypes.CLASS, PsiClass.ARRAY_FACTORY);
    }

    @Override
    public String getPackageName() {
        return StringRef.toString(myPackageName);
    }

    @Override
    public boolean isCompiled() {
        return myCompiled;
    }

    @Override
    public StubPsiFactory getPsiFactory() {
        return myFactory;
    }

    @Override
    public void setPsiFactory(StubPsiFactory factory) {
        myFactory = factory;
    }

    /** @deprecated use constructors (to be removed in IDEA 16) */
    @SuppressWarnings("unused")
    public void setPackageName(String packageName) {
        throw new IncorrectOperationException();
    }

    @Override
    public String toString() {
        return "PsiJavaFileStub [" + myPackageName + "]";
    }
}