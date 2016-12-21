/*
 * Copyright 2014 the original author or authors.
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

package org.gradle.api.internal.tasks.compile.incremental.jar;

import org.gradle.api.internal.tasks.compile.incremental.analyzer.ClassNamesCache;
import org.gradle.api.internal.tasks.compile.incremental.deps.ClassSetAnalysis;
import org.gradle.api.internal.tasks.compile.incremental.deps.DependentsSet;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class PreviousCompilation {

    private ClassSetAnalysis analysis;
    private LocalJarClasspathSnapshotStore classpathSnapshotStore;
    private final JarSnapshotCache jarSnapshotCache;
    private Map<File, JarSnapshot> jarSnapshots;
    private final ClassNamesCache classNamesCache;

    public PreviousCompilation(ClassSetAnalysis analysis, LocalJarClasspathSnapshotStore classpathSnapshotStore, JarSnapshotCache jarSnapshotCache, ClassNamesCache classNamesCache) {
        this.analysis = analysis;
        this.classpathSnapshotStore = classpathSnapshotStore;
        this.jarSnapshotCache = jarSnapshotCache;
        this.classNamesCache = classNamesCache;
    }

    public DependentsSet getDependents(Set<String> allClasses, Set<Integer> constants) {
        return analysis.getRelevantDependents(allClasses, constants);
    }

    public String getClassName(String path) {
        return classNamesCache.get(path);
    }

    public JarSnapshot getJarSnapshot(File file) {
        if (jarSnapshots == null) {
            JarClasspathSnapshotData data = classpathSnapshotStore.get();
            jarSnapshots = jarSnapshotCache.getJarSnapshots(data.getJarHashes());
        }
        return jarSnapshots.get(file);
    }

    public DependentsSet getDependents(String className) {
        Set<Integer> constants = analysis.getData().getConstants(className);
        return analysis.getRelevantDependents(className, constants);
    }
}
