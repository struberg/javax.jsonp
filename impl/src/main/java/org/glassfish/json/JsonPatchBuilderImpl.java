/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.json;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import javax.json.JsonPatchBuilder;
import javax.json.JsonStructure;
import javax.json.JsonValue;

public final class JsonPatchBuilderImpl implements JsonPatchBuilder {

    private JsonArrayBuilder builder;

    /**
     * Creates a JsonPatchBuilder, starting with the specified
     * JSON Patch
     * @param patch the JSON Patch
     */
    public JsonPatchBuilderImpl(JsonArray patch) {
        builder = Json.createArrayBuilder(patch);
    }

    /**
     * Creates JsonPatchBuilder with empty JSON Patch
     */
    public JsonPatchBuilderImpl() {
        builder = Json.createArrayBuilder();
    }

    /**
     * A convenience method for {@code new JsonPatch(build()).apply(target) }
     *
     * @param target the target to apply the patch operations
     * @return the transformed target after the patch
     * @throws JsonException if the supplied JSON Patch is malformed or if
     *    it contains references to non-existing members
     */
    public JsonStructure apply(JsonStructure target) {
        return build().apply(target);
    }

    /**
     * A convenience method for {@code build().apply(target) }
     *
     * @param target the target to apply the patch operations
     * @return the transformed target after the patch
     * @throws JsonException if the supplied JSON Patch is malformed or if
     *    it contains references to non-existing members
     * @see #apply(JsonStructure)
     */
    public JsonObject apply(JsonObject target) {
        return build().apply(target);
    }

    /**
     * A convenience method for {@code build().apply(target) }
     *
     * @param target the target to apply the patch operations
     * @return the transformed target after the patch
     * @throws JsonException if the supplied JSON Patch is malformed or if
     *    it contains references to non-existing members
     * @see #apply(JsonStructure)
     */
    public JsonArray apply(JsonArray target) {
        return build().apply(target);
    }

    /**
     * Adds an "add" JSON Patch operation.
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl add(String path, JsonValue value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "add")
                           .add("path", path)
                           .add("value", value)
                   );
        return this;
    }

    /**
     * Adds an "add" JSON Patch operation
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl add(String path, String value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "add")
                           .add("path", path)
                           .add("value", value)
                   );
        return this;
    }

    /**
     * Adds an "add" JSON Patch operation
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl add(String path, int value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "add")
                           .add("path", path)
                           .add("value", value)
                  );
        return this;
    }

    /**
     * Adds an "add" JSON Patch operation
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl add(String path, boolean value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "add")
                           .add("path", path)
                           .add("value", value)
                   );
        return this;
    }

    /**
     * Adds a "remove" JSON Patch operation.
     * @param path the "path" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl remove(String path) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "remove")
                           .add("path", path)
                    );
        return this;
    }

    /**
     * Adds a "replace" JSON Patch operation.
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl replace(String path, JsonValue value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "replace")
                           .add("path", path)
                           .add("value", value)
                  );
        return this;
    }

    /**
     * Adds a "replace" JSON Patch operation.
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl replace(String path, String value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "replace")
                           .add("path", path)
                           .add("value", value)
                  );
        return this;
    }

    /**
     * Adds a "replace" JSON Patch operation.
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl replace(String path, int value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "replace")
                           .add("path", path)
                           .add("value", value)
                  );
        return this;
    }

    /**
     * Adds a "replace" JSON Patch operation.
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl replace(String path, boolean value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "replace")
                           .add("path", path)
                           .add("value", value)
                  );
        return this;
    }

    /**
     * Adds a "move" JSON Patch operation.
     * @param path the "path" member of the operation
     * @param from the "from" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl move(String path, String from) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "move")
                           .add("path", path)
                           .add("from", from)
                  );
        return this;
    }
 
    /**
     * Adds a "copy" JSON Patch operation.
     * @param path the "path" member of the operation
     * @param from the "from" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl copy(String path, String from) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "copy")
                           .add("path", path)
                           .add("from", from)
                  );
        return this;
    }
 
    /**
     * Adds a "test" JSON Patch operation.
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl test(String path, JsonValue value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "test")
                           .add("path", path)
                           .add("value", value)
                  );
        return this;
    }

    /**
     * Adds a "test" JSON Patch operation.
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl test(String path, String value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "test")
                           .add("path", path)
                           .add("value", value)
                  );
        return this;
    }

    /**
     * Adds a "test" JSON Patch operation.
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl test(String path, int value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "test")
                           .add("path", path)
                           .add("value", value)
                  );
        return this;
    }

    /**
     * Adds a "test" JSON Patch operation.
     * @param path the "path" member of the operation
     * @param value the "value" member of the operation
     * @return this JsonPatch
     */
    public JsonPatchBuilderImpl test(String path, boolean value) {
        builder.add(Json.createObjectBuilder()
                           .add("op", "test")
                           .add("path", path)
                           .add("value", value)
                  );
        return this;
    }

    /**
     * Returns the patch operations in a JsonArray
     * @return the patch operations in a JsonArray
     */
    public JsonArray buildAsJsonArray() {
        return builder.build();
    }

    /**
     * Returns the patch operation in a JsonPatch
     * @return the patch operation in a JsonPatch
     */
    public JsonPatch build() {
        return new JsonPatchImpl(buildAsJsonArray());
    }
}

