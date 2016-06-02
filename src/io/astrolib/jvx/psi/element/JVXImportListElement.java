package io.astrolib.jvx.psi.element;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatementBase;
import com.intellij.psi.impl.JavaPsiImplementationHelper;
import com.intellij.psi.impl.source.SourceTreeToPsiMap;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.TreeElement;

/**
 * @author skeswa
 */
public class JVXImportListElement extends CompositeElement {
    public JVXImportListElement() {
        super(JVXElementType.IMPORT_LIST);
    }

    @Override
    public TreeElement addInternal(TreeElement first, ASTNode last, ASTNode anchor, Boolean before){
        if (before == null){
            if (first == last && (first.getElementType() == JVXElementType.IMPORT_STATEMENT || first.getElementType() == JVXElementType.IMPORT_STATIC_STATEMENT)){
                final PsiImportList list = (PsiImportList) SourceTreeToPsiMap.treeElementToPsi(this);
                final PsiImportStatementBase statement = (PsiImportStatementBase)SourceTreeToPsiMap.treeElementToPsi(first);
                final JavaPsiImplementationHelper instance = JavaPsiImplementationHelper.getInstance(list.getProject());
                if (instance != null) {
                    anchor = instance.getDefaultImportAnchor(list, statement);
                }
                before = Boolean.TRUE;
            }
        }
        return super.addInternal(first, last, anchor, before);
    }
}
