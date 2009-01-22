/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.basic

import org.codenarc.rule.AbstractAstVisitor
import org.codehaus.groovy.ast.expr.ConstructorCallExpression

/**
 * Abstract superclass for AST Visitor classes that deal with constructor calls
 *
 * @author Chris Mair
 * @version $Revision: 193 $ - $Date: 2009-01-13 21:04:52 -0500 (Tue, 13 Jan 2009) $
 */
abstract class AbstractConstructorCallAstVisitor extends AbstractAstVisitor {
    // If within class, the expression callback gets called twice, so keep track of visited expressions
    def visitedConstructorCalls = new HashSet()

    /**
     * Subclasses must implement to return true if the visited constructor call causes a rule violation
     */
    protected abstract isConstructorCallAViolation(ConstructorCallExpression constructorCall)

    void visitConstructorCallExpression(ConstructorCallExpression constructorCall) {
        if (!visitedConstructorCalls.contains(constructorCall) && isConstructorCallAViolation(constructorCall)) {
            addViolation(constructorCall)
            visitedConstructorCalls << constructorCall
        }
        super.visitConstructorCallExpression(constructorCall)
    }
}