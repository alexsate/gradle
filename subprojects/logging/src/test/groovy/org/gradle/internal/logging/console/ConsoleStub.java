/*
 * Copyright 2016 the original author or authors.
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
package org.gradle.internal.logging.console;

import org.gradle.internal.logging.text.TestStyledTextOutput;

public class ConsoleStub implements Console {
    private final TextAreaImpl mainArea = new TextAreaImpl();

    public Label getStatusBar() {
        return new Label() {
            public void close() {
            }

            public void setText(String text) {
            }
        };
    }

    @Override
    public BuildProgressArea getBuildProgressArea() {
        return new BuildProgressArea() {
            @Override
            public Label[] getEntries() {
                return new Label[0];
            }

            @Override
            public Label getHeader() {
                // TODO(ew): impl
                return null;
            }
        };
    }

    @Override
    public void flush() {
    }

    public String getValue() {
        return mainArea.toString();
    }

    public TextArea getMainArea() {
        return mainArea;
    }

    private static class TextAreaImpl extends TestStyledTextOutput implements TextArea {
    }
}
