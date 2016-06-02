package io.astrolib.jvx.psi.stub.impls;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.PsiClassStub;
import com.intellij.psi.impl.light.LightClassReference;
import io.astrolib.jvx.psi.stub.JVXStubElementTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author skeswa
 */
public class PsiEnumConstantInitializerImpl extends PsiClassImpl implements PsiEnumConstantInitializer {
    private static final Logger LOG = Logger.getInstance("#com.intellij.psi.impls.source.PsiEnumConstantInitializerImpl");
    private PsiClassType myCachedBaseType = null;

    public PsiEnumConstantInitializerImpl(final PsiClassStub stub) {
        super(stub, JVXStubElementTypes.ENUM_CONSTANT_INITIALIZER);
    }

    public PsiEnumConstantInitializerImpl(final ASTNode node) {
        super(node);
    }

    @Override
    protected Object clone() {
        PsiEnumConstantInitializerImpl clone = (PsiEnumConstantInitializerImpl)super.clone();
        clone.myCachedBaseType = null;
        return clone;
    }

    @Override
    public void subtreeChanged() {
        super.subtreeChanged();
        myCachedBaseType = null;
    }

    @Override
    public PsiExpressionList getArgumentList() {
        PsiElement parent = getParent();
        LOG.assertTrue(parent instanceof PsiEnumConstant);
        return ((PsiCall)parent).getArgumentList();
    }

    @Override
    public boolean isInQualifiedNew() {
        return false;
    }

    @Override
    @NotNull
    public PsiJavaCodeReferenceElement getBaseClassReference() {
        PsiClass containingClass = getBaseClass();
        return new LightClassReference(getManager(), containingClass.getName(), containingClass);
    }

    private PsiClass getBaseClass() {
        PsiElement parent = getParent();
        LOG.assertTrue(parent instanceof PsiEnumConstant);
        PsiClass containingClass = ((PsiEnumConstant)parent).getContainingClass();
        LOG.assertTrue(containingClass != null);
        return containingClass;
    }

    @Override
    public PsiElement getParent() {
        return getParentByStub();
    }

    @Override
    @NotNull
    public PsiEnumConstant getEnumConstant() {
        return (PsiEnumConstant) getParent();
    }

    @Override
    @NotNull
    public PsiClassType getBaseClassType() {
        if (myCachedBaseType == null) {
            myCachedBaseType = JavaPsiFacade.getInstance(getProject()).getElementFactory().createType(getBaseClass());
        }
        return myCachedBaseType;
    }

    @Override
    public PsiIdentifier getNameIdentifier() {
        return null;
    }

    @Override
    public String getQualifiedName() {
        return null;
    }

    @Override
    public PsiModifierList getModifierList() {
        return null;
    }

    @Override
    public boolean hasModifierProperty(@NotNull String name) {
        return false;
    }

    @Override
    public PsiReferenceList getExtendsList() {
        return null;
    }

    @Override
    public PsiReferenceList getImplementsList() {
        return null;
    }

    @Override
    @NotNull
    public PsiClassType[] getSuperTypes() {
        return new PsiClassType[]{getBaseClassType()};
    }

    @Override
    public boolean isInterface() {
        return false;
    }

    @Override
    public boolean isAnnotationType() {
        return false;
    }

    @Override
    public boolean isEnum() {
        return false;
    }

    @Override
    public PsiTypeParameterList getTypeParameterList() {
        return null;
    }

    @Override
    public PsiElement getOriginalElement() {
        return this;
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor)visitor).visitEnumConstantInitializer(this);
        }
        else {
            visitor.visitElement(this);
        }
    }

    public String toString() {
        return "PsiAnonymousClass (PsiEnumConstantInitializerImpl)):";
    }
}