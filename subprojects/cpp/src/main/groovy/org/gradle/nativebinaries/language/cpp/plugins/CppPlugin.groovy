/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.nativebinaries.language.cpp.plugins
import org.gradle.api.Incubating
import org.gradle.api.Plugin
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.language.cpp.CppSourceSet
import org.gradle.language.cpp.plugins.CppLangPlugin
import org.gradle.nativebinaries.language.cpp.tasks.CppCompile
import org.gradle.nativebinaries.plugins.NativeComponentPlugin
/**
 * A plugin for projects wishing to build native binary components from C++ sources.
 *
 * <p>Automatically includes the {@link CppLangPlugin} for core C++ support and the {@link NativeComponentPlugin} for native component support.</p>
 *
 * <li>Creates a {@link CppCompile} task for each {@link CppSourceSet} to compile the C++ sources.</li>
 */
@Incubating
class CppPlugin implements Plugin<ProjectInternal> {
    void apply(ProjectInternal project) {
        project.plugins.apply(NativeComponentPlugin)
        project.plugins.apply(CppLangPlugin)
    }
}