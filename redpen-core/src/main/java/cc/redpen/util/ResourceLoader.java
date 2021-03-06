/**
 * redpen: a text inspection tool
 * Copyright (C) 2014 Recruit Technologies Co., Ltd. and contributors
 * (see CONTRIBUTORS.md)
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
package cc.redpen.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Load Default and user resources used in validators.
 */
public class ResourceLoader {

    private FileLoader loader = null;

    /**
     * Constructor.
     *
     * @param resourceExtractor resource extractor.
     */
    public ResourceLoader(ResourceExtractor resourceExtractor) {
        this.loader = new FileLoader(resourceExtractor);
    }

    /**
     * Load a given input file combined with jar package.
     *
     * @param inputFile a file included in the jar file
     */
    public void loadInternalResource(String inputFile) throws IOException {
        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(inputFile)) {
            if (inputStream == null) {
                throw new IOException("Failed to load input " + inputFile);
            }
            loader.loadFile(inputStream);
        }
    }

    /**
     * Load a given input file  not combined with jar.
     *
     * @param inputFile input file not combined with jar
     */
    public void loadExternalFile(String inputFile) throws IOException {
        loader.loadFile(inputFile);
    }

}
