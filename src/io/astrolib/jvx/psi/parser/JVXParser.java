/*
 * Copyright 2000-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.astrolib.jvx.psi.parser;

import org.jetbrains.annotations.NotNull;

public class JVXParser {
  public static final JVXParser INSTANCE = new JVXParser();

  private final FileParser myFileParser;
  private final DeclarationParser myDeclarationParser;
  private final StatementParser myStatementParser;
  private final ExpressionParser myExpressionParser;
  private final ReferenceParser myReferenceParser;

  public JVXParser() {
    myFileParser = new FileParser(this);
    myDeclarationParser = new DeclarationParser(this);
    myStatementParser = new StatementParser(this);
    myExpressionParser = new ExpressionParser(this);
    myReferenceParser = new ReferenceParser(this);
  }

  @NotNull
  public FileParser getFileParser() {
    return myFileParser;
  }

  @NotNull
  public DeclarationParser getDeclarationParser() {
    return myDeclarationParser;
  }

  @NotNull
  public StatementParser getStatementParser() {
    return myStatementParser;
  }

  @NotNull
  public ExpressionParser getExpressionParser() {
    return myExpressionParser;
  }

  @NotNull
  public ReferenceParser getReferenceParser() {
    return myReferenceParser;
  }
}