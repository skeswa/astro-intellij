package io.astrolib.jvx.psi.stub.elements;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiNameHelper;
import com.intellij.psi.impl.cache.RecordUtil;
import com.intellij.psi.impl.java.stubs.PsiClassStub;
import com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import com.intellij.psi.impl.source.PsiAnonymousClassImpl;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiEnumConstantInitializerImpl;
import com.intellij.psi.impl.source.tree.JavaDocElementType;
import com.intellij.psi.impl.source.tree.LightTreeUtil;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.io.StringRef;
import io.astrolib.jvx.psi.elements.JVXAnonymousClassElement;
import io.astrolib.jvx.psi.elements.JVXElementType;
import io.astrolib.jvx.psi.elements.JVXEnumConstantInitializerElement;
import io.astrolib.jvx.psi.stub.JVXStubElementTypes;
import io.astrolib.jvx.psi.stub.JVXStubIndexKeys;
import io.astrolib.jvx.psi.stub.impls.PsiClassStubImpl;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author skeswa
 */
public abstract class JVXClassElementType extends JVXStubElementType<PsiClassStub, PsiClass> {
    public JVXClassElementType(@NotNull @NonNls final String id) {
        super(id);
    }

    @Override
    public PsiClass createPsi(@NotNull final PsiClassStub stub) {
        return getPsiFactory(stub).createClass(stub);
    }

    @Override
    public PsiClass createPsi(@NotNull final ASTNode node) {
        // TODO(skeswa): make this JVX specific
        if (node instanceof JVXEnumConstantInitializerElement) {
            // TODO(skeswa): make this JVX specific
            return new PsiEnumConstantInitializerImpl(node);
        }
        // TODO(skeswa): make this JVX specific
        else if (node instanceof JVXAnonymousClassElement) {
            // TODO(skeswa): make this JVX specific
            return new PsiAnonymousClassImpl(node);
        }

        return new PsiClassImpl(node);
    }

    @Override
    public PsiClassStub createStub(final LighterAST tree, final LighterASTNode node, final StubElement parentStub) {
        boolean isDeprecatedByComment = false;
        boolean isInterface = false;
        boolean isEnum = false;
        boolean isEnumConst = false;
        boolean isAnonymous = false;
        boolean isAnnotation = false;
        boolean isInQualifiedNew = false;
        boolean hasDeprecatedAnnotation = false;

        String qualifiedName = null;
        String name = null;
        String baseRef = null;

        if (node.getTokenType() == JVXElementType.ANONYMOUS_CLASS) {
            isAnonymous = true;
        }
        else if (node.getTokenType() == JVXElementType.ENUM_CONSTANT_INITIALIZER) {
            isAnonymous = isEnumConst = true;
            baseRef = ((PsiClassStub)parentStub.getParentStub()).getName();
        }

        for (final LighterASTNode child : tree.getChildren(node)) {
            final IElementType type = child.getTokenType();
            if (type == JavaDocElementType.DOC_COMMENT) {
                isDeprecatedByComment = RecordUtil.isDeprecatedByDocComment(tree, child);
            }
            else if (type == JVXElementType.MODIFIER_LIST) {
                hasDeprecatedAnnotation = RecordUtil.isDeprecatedByAnnotation(tree, child);
            }
            else if (type == JavaTokenType.AT) {
                isAnnotation = true;
            }
            else if (type == JavaTokenType.INTERFACE_KEYWORD) {
                isInterface = true;
            }
            else if (type == JavaTokenType.ENUM_KEYWORD) {
                isEnum = true;
            }
            else if (!isAnonymous && type == JavaTokenType.IDENTIFIER) {
                name = RecordUtil.intern(tree.getCharTable(), child);
            }
            else if (isAnonymous && !isEnumConst && type == JVXElementType.JAVA_CODE_REFERENCE) {
                baseRef = LightTreeUtil.toFilteredString(tree, child, null);
            }
        }

        if (name != null) {
            if (parentStub instanceof PsiJavaFileStub) {
                final String pkg = ((PsiJavaFileStub)parentStub).getPackageName();
                if (!pkg.isEmpty()) qualifiedName = pkg + '.' + name; else qualifiedName = name;
            }
            else if (parentStub instanceof PsiClassStub) {
                final String parentFqn = ((PsiClassStub)parentStub).getQualifiedName();
                qualifiedName = parentFqn != null ? parentFqn + '.' + name : null;
            }
        }

        if (isAnonymous) {
            final LighterASTNode parent = tree.getParent(node);
            if (parent != null && parent.getTokenType() == JVXElementType.NEW_EXPRESSION) {
                isInQualifiedNew = (LightTreeUtil.firstChildOfType(tree, parent, JavaTokenType.DOT) != null);
            }
        }

        final byte flags = PsiClassStubImpl.packFlags(isDeprecatedByComment, isInterface, isEnum, isEnumConst, isAnonymous, isAnnotation,
                isInQualifiedNew, hasDeprecatedAnnotation);
        final JVXClassElementType type = typeForClass(isAnonymous, isEnumConst);
        return new PsiClassStubImpl(type, parentStub, qualifiedName, name, baseRef, flags);
    }

    // TODO (skeswa): pick up here
    public static JVXClassElementType typeForClass(final boolean anonymous, final boolean enumConst) {
        return enumConst
                ? JVXStubElementTypes.ENUM_CONSTANT_INITIALIZER
                : anonymous ? JVXStubElementTypes.ANONYMOUS_CLASS : JVXStubElementTypes.CLASS;
    }

    @Override
    public void serialize(@NotNull final PsiClassStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeByte(((PsiClassStubImpl)stub).getFlags());
        if (!stub.isAnonymous()) {
            dataStream.writeName(stub.getName());
            dataStream.writeName(stub.getQualifiedName());
            dataStream.writeByte(stub.getLanguageLevel().ordinal());
            dataStream.writeName(stub.getSourceFileName());
        }
        else {
            dataStream.writeName(stub.getBaseClassReferenceText());
        }
    }

    @NotNull
    @Override
    public PsiClassStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        byte flags = dataStream.readByte();
        boolean isAnonymous = PsiClassStubImpl.isAnonymous(flags);
        boolean isEnumConst = PsiClassStubImpl.isEnumConstInitializer(flags);
        JVXClassElementType type = typeForClass(isAnonymous, isEnumConst);

        if (!isAnonymous) {
            StringRef name = dataStream.readName();
            StringRef qname = dataStream.readName();
            int languageLevelId = dataStream.readByte();
            StringRef sourceFileName = dataStream.readName();
            PsiClassStubImpl classStub = new PsiClassStubImpl(type, parentStub, qname, name, null, flags);
            classStub.setLanguageLevel(LanguageLevel.values()[languageLevelId]);
            classStub.setSourceFileName(sourceFileName);
            return classStub;
        }
        else {
            StringRef baseRef = dataStream.readName();
            return new PsiClassStubImpl(type, parentStub, null, null, baseRef, flags);
        }
    }

    @Override
    public void indexStub(@NotNull final PsiClassStub stub, @NotNull final IndexSink sink) {
        boolean isAnonymous = stub.isAnonymous();
        if (isAnonymous) {
            String baseRef = stub.getBaseClassReferenceText();
            if (baseRef != null) {
                sink.occurrence(JVXStubIndexKeys.ANONYMOUS_BASEREF, PsiNameHelper.getShortClassName(baseRef));
            }
        }
        else {
            final String shortName = stub.getName();
            if (shortName != null) {
                sink.occurrence(JVXStubIndexKeys.CLASS_SHORT_NAMES, shortName);
            }

            final String fqn = stub.getQualifiedName();
            if (fqn != null) {
                sink.occurrence(JVXStubIndexKeys.CLASS_FQN, fqn.hashCode());
            }
        }
    }

    @Override
    public String getId(final PsiClassStub stub) {
        final String name = stub.getName();
        return name != null ? name : super.getId(stub);
    }
}
