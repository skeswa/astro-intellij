package io.astrolib.jvx.psi.stub;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.lang.LighterLazyParseableNode;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.impl.java.stubs.impl.PsiJavaFileStubImpl;
import com.intellij.psi.impl.source.tree.*;
import com.intellij.psi.stubs.LightStubBuilder;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.io.StringRef;
import io.astrolib.jvx.psi.element.JVXElementType;
import org.jetbrains.annotations.NotNull;

public class JVXLightStubBuilder extends LightStubBuilder {
    @NotNull
    @Override
    protected StubElement createStubForFile(@NotNull PsiFile file, @NotNull LighterAST tree) {
        if (!(file instanceof PsiJavaFile)) {
            return super.createStubForFile(file, tree);
        }

        String refText = "";
        LighterASTNode pkg = LightTreeUtil.firstChildOfType(tree, tree.getRoot(), JVXElementType.PACKAGE_STATEMENT);
        if (pkg != null) {
            LighterASTNode ref = LightTreeUtil.firstChildOfType(tree, pkg, JVXElementType.JAVA_CODE_REFERENCE);
            if (ref != null) {
                refText = JavaSourceUtil.getReferenceText(tree, ref);
            }
        }
        return new PsiJavaFileStubImpl((PsiJavaFile)file, StringRef.fromString(refText), false);
    }

    @Override
    public boolean skipChildProcessingWhenBuildingStubs(@NotNull ASTNode parent, @NotNull ASTNode node) {
        IElementType parentType = parent.getElementType();
        IElementType nodeType = node.getElementType();

        if (checkByTypes(parentType, nodeType)) return true;

        if (nodeType == JVXElementType.CODE_BLOCK) {
            JVXLightStubBuilder.CodeBlockVisitor visitor = new JVXLightStubBuilder.CodeBlockVisitor();
            ((TreeElement)node).acceptTree(visitor);
            return visitor.result;
        }

        return false;
    }

    @Override
    protected boolean skipChildProcessingWhenBuildingStubs(@NotNull LighterAST tree, @NotNull LighterASTNode parent, @NotNull LighterASTNode node) {
        IElementType parentType = parent.getTokenType();
        IElementType nodeType = node.getTokenType();

        if (checkByTypes(parentType, nodeType)) return true;

        if (nodeType == JVXElementType.CODE_BLOCK) {
            JVXLightStubBuilder.CodeBlockVisitor visitor = new JVXLightStubBuilder.CodeBlockVisitor();
            ((LighterLazyParseableNode)node).accept(visitor);
            return visitor.result;
        }

        return false;
    }

    private static boolean checkByTypes(IElementType parentType, IElementType nodeType) {
        if (ElementType.IMPORT_STATEMENT_BASE_BIT_SET.contains(parentType)) {
            return true;
        }
        if (nodeType == JVXElementType.RECEIVER_PARAMETER) {
            return true;
        }
        if (nodeType == JVXElementType.PARAMETER && parentType != JVXElementType.PARAMETER_LIST) {
            return true;
        }
        if (nodeType == JVXElementType.PARAMETER_LIST && parentType == JVXElementType.LAMBDA_EXPRESSION) {
            return true;
        }

        return false;
    }

    private static class CodeBlockVisitor extends RecursiveTreeElementWalkingVisitor implements LighterLazyParseableNode.Visitor {
        private static final TokenSet BLOCK_ELEMENTS = TokenSet.create(
                JVXElementType.ANNOTATION, JVXElementType.CLASS, JVXElementType.ANONYMOUS_CLASS);

        private boolean result = true;

        @Override
        protected void visitNode(TreeElement element) {
            if (BLOCK_ELEMENTS.contains(element.getElementType())) {
                result = false;
                stopWalking();
                return;
            }
            super.visitNode(element);
        }

        private IElementType last = null;
        private boolean seenNew = false;

        @Override
        public boolean visit(IElementType type) {
            if (ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET.contains(type)) {
                return true;
            }

            // annotations
            if (type == JavaTokenType.AT) {
                return (result = false);
            }
            // anonymous classes
            else if (type == JavaTokenType.NEW_KEYWORD) {
                seenNew = true;
            }
            else if (seenNew && type == JavaTokenType.SEMICOLON) {
                seenNew = false;
            }
            else if (seenNew && type == JavaTokenType.LBRACE && last != JavaTokenType.RBRACKET) {
                return (result = false);
            }
            // local classes
            else if (type == JavaTokenType.CLASS_KEYWORD && last != JavaTokenType.DOT) {
                return (result = false);
            }

            last = type;
            return true;
        }
    }
}