package io.astrolib.jvx.psi.stub.impls;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.PsiClass;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.psi.impl.java.stubs.PsiClassStub;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.astrolib.jvx.psi.stub.elements.JVXClassElementType;
import org.jetbrains.annotations.Nullable;

/**
 * @author skeswa
 */
public class PsiClassStubImpl<T extends PsiClass> extends StubBase<T> implements PsiClassStub<T> {
    private final StringRef myQualifiedName;
    private final StringRef myName;
    private final StringRef myBaseRefText;
    private final byte myFlags;

    private static final int DEPRECATED = 0x01;
    private static final int INTERFACE = 0x02;
    private static final int ENUM = 0x04;
    private static final int ENUM_CONSTANT_INITIALIZER = 0x08;
    private static final int ANONYMOUS = 0x10;
    private static final int ANON_TYPE = 0x20;
    private static final int IN_QUALIFIED_NEW = 0x40;
    private static final int DEPRECATED_ANNOTATION = 0x80;

    private LanguageLevel myLanguageLevel = null;
    private StringRef mySourceFileName = null;

    public PsiClassStubImpl(final JVXClassElementType type,
                            final StubElement parent,
                            final String qualifiedName,
                            final String name,
                            @Nullable final String baseRefText,
                            final byte flags) {
        this(type, parent, StringRef.fromString(qualifiedName), StringRef.fromString(name), StringRef.fromString(baseRefText), flags);
    }

    public PsiClassStubImpl(final JVXClassElementType type,
                            final StubElement parent,
                            final StringRef qualifiedName,
                            final StringRef name,
                            final StringRef baseRefText,
                            final byte flags) {
        super(parent, type);
        myQualifiedName = qualifiedName;
        myName = name;
        myBaseRefText = baseRefText;
        myFlags = flags;
        if (StubBasedPsiElementBase.ourTraceStubAstBinding) {
            String creationTrace = "Stub creation thread: " + Thread.currentThread() + "\n" + DebugUtil.currentStackTrace();
            putUserData(StubBasedPsiElementBase.CREATION_TRACE, creationTrace);
        }
    }

    @Override
    public String getName() {
        return StringRef.toString(myName);
    }

    @Override
    public String getQualifiedName() {
        return StringRef.toString(myQualifiedName);
    }

    @Override
    public String getBaseClassReferenceText() {
        return StringRef.toString(myBaseRefText);
    }

    @Override
    public boolean isDeprecated() {
        return (myFlags & DEPRECATED) != 0;
    }

    @Override
    public boolean hasDeprecatedAnnotation() {
        return (myFlags & DEPRECATED_ANNOTATION) != 0;
    }

    @Override
    public boolean isInterface() {
        return (myFlags & INTERFACE) != 0;
    }

    @Override
    public boolean isEnum() {
        return (myFlags & ENUM) != 0;
    }

    @Override
    public boolean isEnumConstantInitializer() {
        return isEnumConstInitializer(myFlags);
    }

    public static boolean isEnumConstInitializer(final byte flags) {
        return (flags & ENUM_CONSTANT_INITIALIZER) != 0;
    }

    @Override
    public boolean isAnonymous() {
        return isAnonymous(myFlags);
    }

    public static boolean isAnonymous(final byte flags) {
        return (flags & ANONYMOUS) != 0;
    }

    @Override
    public boolean isAnnotationType() {
        return (myFlags & ANON_TYPE) != 0;
    }

    @Override
    public LanguageLevel getLanguageLevel() {
        return myLanguageLevel != null ? myLanguageLevel : LanguageLevel.HIGHEST;
    }

    @Override
    public String getSourceFileName() {
        return StringRef.toString(mySourceFileName);
    }

    public void setLanguageLevel(final LanguageLevel languageLevel) {
        myLanguageLevel = languageLevel;
    }

    public void setSourceFileName(final StringRef sourceFileName) {
        mySourceFileName = sourceFileName;
    }

    public void setSourceFileName(final String sourceFileName) {
        mySourceFileName = StringRef.fromString(sourceFileName);
    }

    @Override
    public boolean isAnonymousInQualifiedNew() {
        return (myFlags & IN_QUALIFIED_NEW) != 0;
    }

    public byte getFlags() {
        return myFlags;
    }

    public static byte packFlags(boolean isDeprecated,
                                 boolean isInterface,
                                 boolean isEnum,
                                 boolean isEnumConstantInitializer,
                                 boolean isAnonymous,
                                 boolean isAnnotationType,
                                 boolean isInQualifiedNew,
                                 boolean hasDeprecatedAnnotation) {
        byte flags = 0;
        if (isDeprecated) flags |= DEPRECATED;
        if (isInterface) flags |= INTERFACE;
        if (isEnum) flags |= ENUM;
        if (isEnumConstantInitializer) flags |= ENUM_CONSTANT_INITIALIZER;
        if (isAnonymous) flags |= ANONYMOUS;
        if (isAnnotationType) flags |= ANON_TYPE;
        if (isInQualifiedNew) flags |= IN_QUALIFIED_NEW;
        if (hasDeprecatedAnnotation) flags |= DEPRECATED_ANNOTATION;
        return flags;
    }

    @SuppressWarnings({"HardCodedStringLiteral"})
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PsiClassStub[");

        if (isInterface()) {
            builder.append("interface ");
        }

        if (isAnonymous()) {
            builder.append("anonymous ");
        }

        if (isEnum()) {
            builder.append("enum ");
        }

        if (isAnnotationType()) {
            builder.append("annotation ");
        }

        if (isEnumConstantInitializer()) {
            builder.append("enumInit ");
        }

        if (isDeprecated()) {
            builder.append("deprecated ");
        }

        if (hasDeprecatedAnnotation()) {
            builder.append("deprecatedA ");
        }

        builder.append("name=").append(getName()).append(" fqn=").append(getQualifiedName());

        if (getBaseClassReferenceText() != null) {
            builder.append(" baseref=").append(getBaseClassReferenceText());
        }

        if (isAnonymousInQualifiedNew()) {
            builder.append(" inqualifnew");
        }

        builder.append("]");

        return builder.toString();
    }
}