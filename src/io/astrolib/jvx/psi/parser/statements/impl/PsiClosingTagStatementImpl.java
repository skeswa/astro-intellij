package io.astrolib.jvx.psi.parser.statements.impl;

import io.astrolib.jvx.psi.parser.statements.api.PsiClosingTagStatement;

/**
 * @author skeswa
 */
public class PsiClosingTagStatementImpl extends PsiTagStatementImpl implements PsiClosingTagStatement {
    public PsiClosingTagStatementImpl() {
        super(CLOSING_TAG_STATEMENT);
    }
}
