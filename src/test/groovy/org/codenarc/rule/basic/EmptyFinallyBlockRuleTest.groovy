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

import org.codenarc.rule.AbstractRuleTest
import org.codenarc.rule.Rule

/**
 * Tests for EmptyFinallyBlockRule
 *
 * @author Chris Mair
 * @version $Revision: 193 $ - $Date: 2009-01-13 21:04:52 -0500 (Tue, 13 Jan 2009) $
 */
class EmptyFinallyBlockRuleTest extends AbstractRuleTest {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.id == 'EmptyFinallyBlock'
    }

    void testApplyTo_Violation() {
        final SOURCE = '''
            class MyClass {
                def myMethod() {
                    try {
                        doSomething()
                    } finally {
                    }
                }
            }
        '''
        assertSingleViolation(SOURCE, 6, 'finally {')
    }

    void testApplyTo_Violation_FinallyBlockContainsComment() {
        final SOURCE = '''
            try {
                doSomething()
            } catch(MyException e) {
                e.printStackTrace()
            }
            finally
            {
                // TODO Should do something here
            }
        '''
        assertSingleViolation(SOURCE, 7, 'finally')
    }

    void testApplyTo_NoViolations() {
        final SOURCE = '''class MyClass {
                def myMethod() {
                    try {
                        doSomething()
                    }
                    finally {
                        println "cleanup"
                    }
                }
            }'''
        assertNoViolations(SOURCE)
    }

    protected Rule createRule() {
        return new EmptyFinallyBlockRule()
    }

}