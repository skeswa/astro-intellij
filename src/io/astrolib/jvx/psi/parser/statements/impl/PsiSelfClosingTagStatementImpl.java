package io.astrolib.jvx.psi.parser.statements.impl;

import io.astrolib.jvx.psi.parser.statements.api.PsiSelfClosingTagStatement;

/**
 * @author skeswa
 */
public class PsiSelfClosingTagStatementImpl extends PsiOpeningTagStatementImpl implements PsiSelfClosingTagStatement {
    public PsiSelfClosingTagStatementImpl() {
        super(SELF_CLOSING_TAG_STATEMENT);
    }
}
