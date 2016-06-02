package io.astrolib.jvx.psi.stub;

import com.intellij.lang.ASTNode;
import com.intellij.psi.impl.java.stubs.JavaClassElementType;
import com.intellij.psi.tree.IStubFileElementType;
import io.astrolib.jvx.psi.element.JVXClassElement;
import io.astrolib.jvx.psi.element.JVXFileElementType;
import io.astrolib.jvx.psi.element.JVXImportListElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Created by skeswa on 5/31/16.
 */
public interface JVXStubElementTypes {
    IStubFileElementType JVX_FILE = new JVXFileElementType();
    JVXImportListElementType IMPORT_LIST = new JVXImportListElementType();
    JavaClassElementType CLASS = new JavaClassElementType("CLASS") {
        @NotNull
        @Override
        public ASTNode createCompositeNode() {
            return new JVXClassElement(this);
        }
    };
}
