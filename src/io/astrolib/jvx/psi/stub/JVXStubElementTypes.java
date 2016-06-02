package io.astrolib.jvx.psi.stub;

import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IStubFileElementType;
import io.astrolib.jvx.psi.elements.*;
import io.astrolib.jvx.psi.file.elements.JVXFileElementType;
import io.astrolib.jvx.psi.stub.elements.JVXClassElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Created by skeswa on 5/31/16.
 */
public interface JVXStubElementTypes {
    IStubFileElementType JVX_FILE = new JVXFileElementType();
    JVXImportListElementType IMPORT_LIST = new JVXImportListElementType();
    
    JVXClassElementType CLASS = new JVXClassElementType("CLASS") {
        @NotNull
        @Override
        public ASTNode createCompositeNode() {
            return new JVXClassElement(this);
        }
    };
    JVXClassElementType ANONYMOUS_CLASS = new JVXClassElementType("ANONYMOUS_CLASS") {
        @NotNull
        @Override
        public ASTNode createCompositeNode() {
            return new JVXAnonymousClassElement();
        }
    };
    JVXClassElementType ENUM_CONSTANT_INITIALIZER = new JVXClassElementType("ENUM_CONSTANT_INITIALIZER") {
        @NotNull
        @Override
        public ASTNode createCompositeNode() {
            return new JVXEnumConstantInitializerElement();
        }
    };
    
    // TODO(skeswa): this next
//    JVXMethodElementType METHOD = new JVXMethodElementType("METHOD") {
//        @NotNull
//        @Override
//        public ASTNode createCompositeNode() {
//            return new MethodElement();
//        }
//    };

    // TODO(skeswa): this next
//    JVXMethodElementType ANNOTATION_METHOD = new JVXMethodElementType("ANNOTATION_METHOD") {
//        @NotNull
//        @Override
//        public ASTNode createCompositeNode() {
//            return new AnnotationMethodElement();
//        }
//    };
    
//    JVXFieldStubElementType FIELD = new JVXFieldStubElementType("FIELD") {
//        @NotNull
//        @Override
//        public ASTNode createCompositeNode() {
//            return new FieldElement();
//        }
//    };
//    JVXFieldStubElementType ENUM_CONSTANT = new JVXFieldStubElementType("ENUM_CONSTANT") {
//        @NotNull
//        @Override
//        public ASTNode createCompositeNode() {
//            return new EnumConstantElement();
//        }
//    };

    // TODO(skeswa): this next
//    JVXClassReferenceListElementType EXTENDS_LIST = new JVXClassReferenceListElementType("EXTENDS_LIST") {
//        @NotNull
//        @Override
//        public ASTNode createCompositeNode() {
//            return new ExtendsListElement();
//        }
//    };
//    JVXClassReferenceListElementType IMPLEMENTS_LIST = new JVXClassReferenceListElementType("IMPLEMENTS_LIST") {
//        @NotNull
//        @Override
//        public ASTNode createCompositeNode() {
//            return new ImplementsListElement();
//        }
//    };
//    JVXClassReferenceListElementType THROWS_LIST = new JVXClassReferenceListElementType("THROWS_LIST") {
//        @NotNull
//        @Override
//        public ASTNode createCompositeNode() {
//            return new PsiThrowsListImpl();
//        }
//    };
//    JVXClassReferenceListElementType EXTENDS_BOUND_LIST = new JVXClassReferenceListElementType("EXTENDS_BOUND_LIST") {
//        @NotNull
//        @Override
//        public ASTNode createCompositeNode() {
//            return new TypeParameterExtendsBoundsListElement();
//        }
//    };
//
//    JVXImportStatementElementType IMPORT_STATEMENT = new JVXImportStatementElementType("IMPORT_STATEMENT") {
//        @NotNull
//        @Override
//        public ASTNode createCompositeNode() {
//            return new ImportStatementElement();
//        }
//    };
//    JVXImportStatementElementType IMPORT_STATIC_STATEMENT = new JVXImportStatementElementType("IMPORT_STATIC_STATEMENT") {
//        @NotNull
//        @Override
//        public ASTNode createCompositeNode() {
//            return new ImportStaticStatementElement();
//        }
//    };
}
