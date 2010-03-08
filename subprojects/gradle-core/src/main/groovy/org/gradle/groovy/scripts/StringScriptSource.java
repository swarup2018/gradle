/*
 * Copyright 2010 the original author or authors.
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
package org.gradle.groovy.scripts;

import org.gradle.api.internal.resource.Resource;
import org.gradle.api.internal.resource.StringResource;
import org.gradle.util.HashUtil;

public class StringScriptSource implements ScriptSource {
    public static final String EMBEDDED_SCRIPT_ID = "embedded_script_";
    private final Resource resource;

    public StringScriptSource(String description, String content) {
        resource = new StringResource(description, content == null ? "" : content);
    }

    public String getClassName() {
        return EMBEDDED_SCRIPT_ID + HashUtil.createHash(resource.getText());
    }

    public Resource getResource() {
        return resource;
    }

    public String getFileName() {
        return getClassName();
    }

    public String getDisplayName() {
        return resource.getDisplayName();
    }
}
