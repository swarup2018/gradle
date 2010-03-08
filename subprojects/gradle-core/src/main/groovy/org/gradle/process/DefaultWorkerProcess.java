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
package org.gradle.process;

import org.gradle.messaging.ObjectConnection;
import org.gradle.util.exec.ExecHandle;
import org.gradle.util.exec.ExecHandleListener;
import org.gradle.util.exec.ExecHandleState;

public class DefaultWorkerProcess implements WorkerProcess {
    private final ObjectConnection connection;
    private final ExecHandle execHandle;

    public DefaultWorkerProcess(final ObjectConnection connection, ExecHandle execHandle) {
        this.connection = connection;
        this.execHandle = execHandle;
        execHandle.addListener(new ExecHandleListener() {
            public void executionStarted(ExecHandle execHandle) {
            }

            public void executionFinished(ExecHandle execHandle) {
                connection.stop();
            }
        });
    }

    public ObjectConnection getConnection() {
        return connection;
    }

    public void start() {
        execHandle.start();
    }

    public void waitForStop() {
        execHandle.waitForFinish();
    }

    public ExecHandleState getState() {
        return execHandle.getState();
    }
}
