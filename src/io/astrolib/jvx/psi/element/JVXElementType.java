package io.astrolib.jvx.psi.element;

import com.intellij.lang.*;
import com.intellij.lang.java.parser.ReferenceParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import com.intellij.psi.impl.source.*;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.impl.source.tree.ICodeFragmentElementType;
import com.intellij.psi.impl.source.tree.java.*;
import com.intellij.psi.tree.*;
import com.intellij.psi.tree.java.IJavaElementType;
import com.intellij.util.ReflectionUtil;
import com.intellij.util.diff.FlyweightCapableTreeStructure;
import io.astrolib.jvx.psi.JVXLanguage;
import io.astrolib.jvx.psi.JVXParserDefinition;
import io.astrolib.jvx.psi.parser.JVXParser;
import io.astrolib.jvx.psi.parser.JVXParserUtil;
import io.astrolib.jvx.psi.parser.expressions.impl.PsiTagAttributeExpressionImpl;
import io.astrolib.jvx.psi.parser.statements.impl.PsiClosingTagStatementImpl;
import io.astrolib.jvx.psi.parser.statements.impl.PsiOpeningTagStatementImpl;
import io.astrolib.jvx.psi.parser.statements.impl.PsiSelfClosingTagStatementImpl;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.reflect.ConstructorAccessor;

import java.lang.reflect.Constructor;

public interface JVXElementType {
    @SuppressWarnings("deprecation")
    class JVXCompositeElementType extends IJavaElementType implements ICompositeElementType {
        private final ConstructorAccessor myConstructor;

        private JVXCompositeElementType(@NonNls final String debugName, final Class<? extends ASTNode> nodeClass) {
            this(debugName, nodeClass, false);
        }

        private JVXCompositeElementType(@NonNls final String debugName, final Class<? extends ASTNode> nodeClass, final boolean leftBound) {
            super(debugName, leftBound);
            Constructor<? extends ASTNode> constructor = ReflectionUtil.getDefaultConstructor(nodeClass);
            myConstructor = ReflectionUtil.getConstructorAccessor(constructor);
        }

        @NotNull
        @Override
        public ASTNode createCompositeNode() {
            return ReflectionUtil.createInstanceViaConstructorAccessor(myConstructor);
        }
    }

    IElementType CLASS = JavaStubElementTypes.CLASS;
    IElementType ANONYMOUS_CLASS = JavaStubElementTypes.ANONYMOUS_CLASS;
    IElementType ENUM_CONSTANT_INITIALIZER = JavaStubElementTypes.ENUM_CONSTANT_INITIALIZER;
    IElementType TYPE_PARAMETER_LIST = JavaStubElementTypes.TYPE_PARAMETER_LIST;
    IElementType TYPE_PARAMETER = JavaStubElementTypes.TYPE_PARAMETER;
    IElementType IMPORT_LIST = JavaStubElementTypes.IMPORT_LIST;
    IElementType IMPORT_STATEMENT = JavaStubElementTypes.IMPORT_STATEMENT;
    IElementType IMPORT_STATIC_STATEMENT = JavaStubElementTypes.IMPORT_STATIC_STATEMENT;
    IElementType MODIFIER_LIST = JavaStubElementTypes.MODIFIER_LIST;
    IElementType ANNOTATION = JavaStubElementTypes.ANNOTATION;
    IElementType NAME_VALUE_PAIR = JavaStubElementTypes.NAME_VALUE_PAIR;
    IElementType ANNOTATION_PARAMETER_LIST = JavaStubElementTypes.ANNOTATION_PARAMETER_LIST;
    IElementType EXTENDS_LIST = JavaStubElementTypes.EXTENDS_LIST;
    IElementType IMPLEMENTS_LIST = JavaStubElementTypes.IMPLEMENTS_LIST;
    IElementType FIELD = JavaStubElementTypes.FIELD;
    IElementType ENUM_CONSTANT = JavaStubElementTypes.ENUM_CONSTANT;
    IElementType METHOD = JavaStubElementTypes.METHOD;
    IElementType ANNOTATION_METHOD = JavaStubElementTypes.ANNOTATION_METHOD;
    IElementType CLASS_INITIALIZER = JavaStubElementTypes.CLASS_INITIALIZER;
    IElementType PARAMETER = JavaStubElementTypes.PARAMETER;
    IElementType PARAMETER_LIST = JavaStubElementTypes.PARAMETER_LIST;
    IElementType EXTENDS_BOUND_LIST = JavaStubElementTypes.EXTENDS_BOUND_LIST;
    IElementType THROWS_LIST = JavaStubElementTypes.THROWS_LIST;

    IElementType LITERAL_EXPRESSION = new JVXElementType.JVXCompositeElementType("LITERAL_EXPRESSION", PsiLiteralExpressionImpl.class);
    IElementType IMPORT_STATIC_REFERENCE = new JVXElementType.JVXCompositeElementType("IMPORT_STATIC_REFERENCE", PsiImportStaticReferenceElementImpl.class);
    IElementType TYPE = new JVXElementType.JVXCompositeElementType("TYPE", PsiTypeElementImpl.class);
    IElementType DIAMOND_TYPE = new JVXElementType.JVXCompositeElementType("DIAMOND_TYPE", PsiDiamondTypeElementImpl.class);
    IElementType REFERENCE_PARAMETER_LIST = new JVXElementType.JVXCompositeElementType("REFERENCE_PARAMETER_LIST", PsiReferenceParameterListImpl.class, true);
    IElementType JAVA_CODE_REFERENCE = new JVXElementType.JVXCompositeElementType("JAVA_CODE_REFERENCE", PsiJavaCodeReferenceElementImpl.class);
    IElementType PACKAGE_STATEMENT = new JVXElementType.JVXCompositeElementType("PACKAGE_STATEMENT", PsiPackageStatementImpl.class);
    IElementType LOCAL_VARIABLE = new JVXElementType.JVXCompositeElementType("LOCAL_VARIABLE", PsiLocalVariableImpl.class);
    IElementType REFERENCE_EXPRESSION = new JVXElementType.JVXCompositeElementType("REFERENCE_EXPRESSION", PsiReferenceExpressionImpl.class);
    IElementType THIS_EXPRESSION = new JVXElementType.JVXCompositeElementType("THIS_EXPRESSION", PsiThisExpressionImpl.class);
    IElementType SUPER_EXPRESSION = new JVXElementType.JVXCompositeElementType("SUPER_EXPRESSION", PsiSuperExpressionImpl.class);
    IElementType PARENTH_EXPRESSION = new JVXElementType.JVXCompositeElementType("PARENTH_EXPRESSION", PsiParenthesizedExpressionImpl.class);
    IElementType METHOD_CALL_EXPRESSION = new JVXElementType.JVXCompositeElementType("METHOD_CALL_EXPRESSION", PsiMethodCallExpressionImpl.class);
    IElementType TYPE_CAST_EXPRESSION = new JVXElementType.JVXCompositeElementType("TYPE_CAST_EXPRESSION", PsiTypeCastExpressionImpl.class);
    IElementType PREFIX_EXPRESSION = new JVXElementType.JVXCompositeElementType("PREFIX_EXPRESSION", PsiPrefixExpressionImpl.class);
    IElementType POSTFIX_EXPRESSION = new JVXElementType.JVXCompositeElementType("POSTFIX_EXPRESSION", PsiPostfixExpressionImpl.class);
    IElementType BINARY_EXPRESSION = new JVXElementType.JVXCompositeElementType("BINARY_EXPRESSION", PsiBinaryExpressionImpl.class);
    IElementType POLYADIC_EXPRESSION = new JVXElementType.JVXCompositeElementType("POLYADIC_EXPRESSION", PsiPolyadicExpressionImpl.class);
    IElementType CONDITIONAL_EXPRESSION = new JVXElementType.JVXCompositeElementType("CONDITIONAL_EXPRESSION", PsiConditionalExpressionImpl.class);
    IElementType ASSIGNMENT_EXPRESSION = new JVXElementType.JVXCompositeElementType("ASSIGNMENT_EXPRESSION", PsiAssignmentExpressionImpl.class);
    IElementType NEW_EXPRESSION = new JVXElementType.JVXCompositeElementType("NEW_EXPRESSION", PsiNewExpressionImpl.class);
    IElementType ARRAY_ACCESS_EXPRESSION = new JVXElementType.JVXCompositeElementType("ARRAY_ACCESS_EXPRESSION", PsiArrayAccessExpressionImpl.class);
    IElementType ARRAY_INITIALIZER_EXPRESSION = new JVXElementType.JVXCompositeElementType("ARRAY_INITIALIZER_EXPRESSION", PsiArrayInitializerExpressionImpl.class);
    IElementType INSTANCE_OF_EXPRESSION = new JVXElementType.JVXCompositeElementType("INSTANCE_OF_EXPRESSION", PsiInstanceOfExpressionImpl.class);
    IElementType CLASS_OBJECT_ACCESS_EXPRESSION = new JVXElementType.JVXCompositeElementType("CLASS_OBJECT_ACCESS_EXPRESSION", PsiClassObjectAccessExpressionImpl.class);
    IElementType EMPTY_EXPRESSION = new JVXElementType.JVXCompositeElementType("EMPTY_EXPRESSION", PsiEmptyExpressionImpl.class, true);
    IElementType METHOD_REF_EXPRESSION = new JVXElementType.JVXCompositeElementType("METHOD_REF_EXPRESSION", PsiMethodReferenceExpressionImpl.class);
    IElementType LAMBDA_EXPRESSION = new JVXElementType.JVXCompositeElementType("LAMBDA_EXPRESSION", PsiLambdaExpressionImpl.class);
    IElementType EXPRESSION_LIST = new JVXElementType.JVXCompositeElementType("EXPRESSION_LIST", PsiExpressionListImpl.class, true);
    IElementType EMPTY_STATEMENT = new JVXElementType.JVXCompositeElementType("EMPTY_STATEMENT", PsiEmptyStatementImpl.class);
    IElementType BLOCK_STATEMENT = new JVXElementType.JVXCompositeElementType("BLOCK_STATEMENT", PsiBlockStatementImpl.class);
    IElementType EXPRESSION_STATEMENT = new JVXElementType.JVXCompositeElementType("EXPRESSION_STATEMENT", PsiExpressionStatementImpl.class);
    IElementType EXPRESSION_LIST_STATEMENT = new JVXElementType.JVXCompositeElementType("EXPRESSION_LIST_STATEMENT", PsiExpressionListStatementImpl.class);
    IElementType DECLARATION_STATEMENT = new JVXElementType.JVXCompositeElementType("DECLARATION_STATEMENT", PsiDeclarationStatementImpl.class);
    IElementType IF_STATEMENT = new JVXElementType.JVXCompositeElementType("IF_STATEMENT", PsiIfStatementImpl.class);
    IElementType WHILE_STATEMENT = new JVXElementType.JVXCompositeElementType("WHILE_STATEMENT", PsiWhileStatementImpl.class);
    IElementType FOR_STATEMENT = new JVXElementType.JVXCompositeElementType("FOR_STATEMENT", PsiForStatementImpl.class);
    IElementType FOREACH_STATEMENT = new JVXElementType.JVXCompositeElementType("FOREACH_STATEMENT", PsiForeachStatementImpl.class);
    IElementType DO_WHILE_STATEMENT = new JVXElementType.JVXCompositeElementType("DO_WHILE_STATEMENT", PsiDoWhileStatementImpl.class);
    IElementType SWITCH_STATEMENT = new JVXElementType.JVXCompositeElementType("SWITCH_STATEMENT", PsiSwitchStatementImpl.class);
    IElementType SWITCH_LABEL_STATEMENT = new JVXElementType.JVXCompositeElementType("SWITCH_LABEL_STATEMENT", PsiSwitchLabelStatementImpl.class);
    IElementType BREAK_STATEMENT = new JVXElementType.JVXCompositeElementType("BREAK_STATEMENT", PsiBreakStatementImpl.class);
    IElementType CONTINUE_STATEMENT = new JVXElementType.JVXCompositeElementType("CONTINUE_STATEMENT", PsiContinueStatementImpl.class);
    IElementType RETURN_STATEMENT = new JVXElementType.JVXCompositeElementType("RETURN_STATEMENT", PsiReturnStatementImpl.class);
    IElementType THROW_STATEMENT = new JVXElementType.JVXCompositeElementType("THROW_STATEMENT", PsiThrowStatementImpl.class);
    IElementType SYNCHRONIZED_STATEMENT = new JVXElementType.JVXCompositeElementType("SYNCHRONIZED_STATEMENT", PsiSynchronizedStatementImpl.class);
    IElementType TRY_STATEMENT = new JVXElementType.JVXCompositeElementType("TRY_STATEMENT", PsiTryStatementImpl.class);
    IElementType RESOURCE_LIST = new JVXElementType.JVXCompositeElementType("RESOURCE_LIST", PsiResourceListImpl.class);
    IElementType RESOURCE_VARIABLE = new JVXElementType.JVXCompositeElementType("RESOURCE_VARIABLE", PsiResourceVariableImpl.class);
    IElementType RESOURCE_EXPRESSION = new JVXElementType.JVXCompositeElementType("RESOURCE_EXPRESSION", PsiResourceExpressionImpl.class);
    IElementType CATCH_SECTION = new JVXElementType.JVXCompositeElementType("CATCH_SECTION", PsiCatchSectionImpl.class);
    IElementType LABELED_STATEMENT = new JVXElementType.JVXCompositeElementType("LABELED_STATEMENT", PsiLabeledStatementImpl.class);
    IElementType ASSERT_STATEMENT = new JVXElementType.JVXCompositeElementType("ASSERT_STATEMENT", PsiAssertStatementImpl.class);
    IElementType ANNOTATION_ARRAY_INITIALIZER = new JVXElementType.JVXCompositeElementType("ANNOTATION_ARRAY_INITIALIZER", PsiArrayInitializerMemberValueImpl.class);
    IElementType RECEIVER_PARAMETER = new JVXElementType.JVXCompositeElementType("RECEIVER", PsiReceiverParameterImpl.class);

    // JVX extension expressions and statements.
    IElementType TAG_ATTRIBUTE_EXPRESSION = new JVXElementType.JVXCompositeElementType("TAG_ATTRIBUTE_EXPRESSION", PsiTagAttributeExpressionImpl.class);
    IElementType OPENING_TAG_STATEMENT = new JVXElementType.JVXCompositeElementType("OPENING_TAG_STATEMENT", PsiOpeningTagStatementImpl.class);
    IElementType CLOSING_TAG_STATEMENT = new JVXElementType.JVXCompositeElementType("CLOSING_TAG_STATEMENT", PsiClosingTagStatementImpl.class);
    IElementType SELF_CLOSING_TAG_STATEMENT = new JVXElementType.JVXCompositeElementType("SELF_CLOSING_TAG_STATEMENT", PsiSelfClosingTagStatementImpl.class);

    class ICodeBlockElementType extends IErrorCounterReparseableElementType implements ICompositeElementType, ILightLazyParseableElementType {
        private ICodeBlockElementType() {
            super("CODE_BLOCK", JVXLanguage.INSTANCE);
        }

        @Override
        public ASTNode createNode(final CharSequence text) {
            return new PsiCodeBlockImpl(text);
        }

        @NotNull
        @Override
        public ASTNode createCompositeNode() {
            return new PsiCodeBlockImpl(null);
        }

        @Override
        public ASTNode parseContents(final ASTNode chameleon) {
            final PsiBuilder builder = JVXParserUtil.createBuilder(chameleon);
            JVXParser.INSTANCE.getStatementParser().parseCodeBlockDeep(builder, true);
            return builder.getTreeBuilt().getFirstChildNode();
        }

        @Override
        public FlyweightCapableTreeStructure<LighterASTNode> parseContents(final LighterLazyParseableNode chameleon) {
            final PsiBuilder builder = JVXParserUtil.createBuilder(chameleon);
            JVXParser.INSTANCE.getStatementParser().parseCodeBlockDeep(builder, true);
            return builder.getLightTree();
        }

        @Override
        public int getErrorsCount(final CharSequence seq, Language fileLanguage, final Project project) {
            Lexer lexer = JVXParserDefinition.createLexer(LanguageLevel.HIGHEST);

            lexer.start(seq);
            if (lexer.getTokenType() != JavaTokenType.LBRACE) return IErrorCounterReparseableElementType.FATAL_ERROR;
            lexer.advance();
            int balance = 1;
            while (true) {
                IElementType type = lexer.getTokenType();
                if (type == null) break;
                if (balance == 0) return IErrorCounterReparseableElementType.FATAL_ERROR;
                if (type == JavaTokenType.LBRACE) {
                    balance++;
                }
                else if (type == JavaTokenType.RBRACE) {
                    balance--;
                }
                lexer.advance();
            }
            return balance;
        }
    }
    ILazyParseableElementType CODE_BLOCK = new JVXElementType.ICodeBlockElementType();

    IElementType STATEMENTS = new ICodeFragmentElementType("STATEMENTS", JVXLanguage.INSTANCE) {
        private final JVXParserUtil.ParserWrapper myParser = new JVXParserUtil.ParserWrapper() {
            @Override
            public void parse(final PsiBuilder builder) {
                JVXParser.INSTANCE.getStatementParser().parseStatements(builder);
            }
        };

        @Nullable
        @Override
        public ASTNode parseContents(final ASTNode chameleon) {
            return JVXParserUtil.parseFragment(chameleon, myParser);
        }
    };

    IElementType EXPRESSION_TEXT = new ICodeFragmentElementType("EXPRESSION_TEXT", JVXLanguage.INSTANCE) {
        private final JVXParserUtil.ParserWrapper myParser = new JVXParserUtil.ParserWrapper() {
            @Override
            public void parse(final PsiBuilder builder) {
                JVXParser.INSTANCE.getExpressionParser().parse(builder);
            }
        };

        @Nullable
        @Override
        public ASTNode parseContents(final ASTNode chameleon) {
            return JVXParserUtil.parseFragment(chameleon, myParser);
        }
    };

    IElementType REFERENCE_TEXT = new ICodeFragmentElementType("REFERENCE_TEXT", JVXLanguage.INSTANCE) {
        private final JVXParserUtil.ParserWrapper myParser = new JVXParserUtil.ParserWrapper() {
            @Override
            public void parse(final PsiBuilder builder) {
                JVXParser.INSTANCE.getReferenceParser().parseJavaCodeReference(builder, false, true, false, false);
            }
        };

        @Nullable
        @Override
        public ASTNode parseContents(final ASTNode chameleon) {
            return JVXParserUtil.parseFragment(chameleon, myParser);
        }
    };

    IElementType TYPE_WITH_DISJUNCTIONS_TEXT = new JVXElementType.TypeTextElementType("TYPE_WITH_DISJUNCTIONS_TEXT", ReferenceParser.DISJUNCTIONS);
    IElementType TYPE_WITH_CONJUNCTIONS_TEXT = new JVXElementType.TypeTextElementType("TYPE_WITH_CONJUNCTIONS_TEXT", ReferenceParser.CONJUNCTIONS);

    class TypeTextElementType extends ICodeFragmentElementType {
        private final int myFlags;

        public TypeTextElementType(@NonNls String debugName, int flags) {
            super(debugName, JVXLanguage.INSTANCE);
            myFlags = flags;
        }

        private final JVXParserUtil.ParserWrapper myParser = new JVXParserUtil.ParserWrapper() {
            @Override
            public void parse(final PsiBuilder builder) {
                JVXParser.INSTANCE.getReferenceParser().parseType(builder, ReferenceParser.EAT_LAST_DOT |
                        ReferenceParser.ELLIPSIS |
                        ReferenceParser.WILDCARD |
                        myFlags);
            }
        };

        @Nullable
        @Override
        public ASTNode parseContents(final ASTNode chameleon) {
            return JVXParserUtil.parseFragment(chameleon, myParser);
        }
    }

    class JVXDummyElementType extends ILazyParseableElementType implements ICompositeElementType {
        private JVXDummyElementType() {
            super("DUMMY_ELEMENT", JVXLanguage.INSTANCE);
        }

        @NotNull
        @Override
        public ASTNode createCompositeNode() {
            return new CompositePsiElement(this) { };
        }

        @Nullable
        @Override
        public ASTNode parseContents(final ASTNode chameleon) {
            assert chameleon instanceof JVXDummyElement : chameleon;
            final JVXDummyElement dummyElement = (JVXDummyElement)chameleon;
            return JVXParserUtil.parseFragment(chameleon, dummyElement.getParser(), dummyElement.consumeAll(), dummyElement.getLanguageLevel());
        }
    }
    IElementType DUMMY_ELEMENT = new JVXElementType.JVXDummyElementType();
}