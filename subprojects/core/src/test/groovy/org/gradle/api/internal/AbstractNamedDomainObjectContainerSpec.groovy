/*
 * Copyright 2018 the original author or authors.
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

package org.gradle.api.internal


import org.gradle.api.NamedDomainObjectCollection
import org.gradle.internal.Actions
import spock.lang.Unroll

abstract class AbstractNamedDomainObjectContainerSpec<T> extends AbstractNamedDomainObjectCollectionSpec<T> {
    abstract NamedDomainObjectCollection<T> getContainer()

    @Override
    protected Map<String, Closure> getMutatingMethods() {
        return super.getMutatingMethods() + [
            "create(String)": { container.create("b") },
            "create(String, Action)": { container.create("b", Actions.doNothing()) },
            "register(String)": { container.register("b") },
            "register(String, Action)": { container.register("b", Actions.doNothing()) },
            "NamedDomainObjectProvider.configure(Action)": { container.named("a").configure(Actions.doNothing()) }
        ]
    }

    @Unroll
    def "allow query and mutating methods from create using #methods.key"() {
        setupContainerDefaults()
        String methodUnderTest = methods.key
        Closure method = bind(methods.value)

        when:
        container.create("a", method)
        then:
        noExceptionThrown()

        where:
        methods << getQueryMethods() + getMutatingMethods()
    }

    def "disallow mutating from register actions using #mutatingMethods.key"() {
        setupContainerDefaults()
        String methodUnderTest = mutatingMethods.key
        Closure method = bind(mutatingMethods.value)

        when:
        container.register("a", method).get()
        then:
        def ex = thrown(Throwable)
        assertDoesNotAllowMethod(ex, methodUnderTest)

        where:
        mutatingMethods << getMutatingMethods()
    }

    @Unroll
    def "allow query methods from register using #queryMethods.key"() {
        setupContainerDefaults()
        String methodUnderTest = queryMethods.key
        Closure method = bind(queryMethods.value)

        when:
        container.register("a", method).get()
        then:
        noExceptionThrown()

        where:
        queryMethods << getQueryMethods()
    }
}
