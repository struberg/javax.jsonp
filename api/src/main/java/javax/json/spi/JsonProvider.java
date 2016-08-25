/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2015 Oracle and/or its affiliates. All rights reserved.
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

package javax.json.spi;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Service provider for JSON processing objects.
 * <p>
 * <p>All the methods in this class are safe for use by multiple concurrent
 * threads.
 *
 * @see ServiceLoader
 */
public abstract class JsonProvider {

    /**
     * A constant representing the name of the default
     * {@code JsonProvider} implementation class.
     */
    private static final String DEFAULT_PROVIDER
            = "org.glassfish.json.JsonProviderImpl";
    private static final ThreadLocal<ServiceLoader<JsonProvider>> threadLoader =
            new ThreadLocal<ServiceLoader<JsonProvider>>() {
                @Override
                protected ServiceLoader<JsonProvider> initialValue() {
                    return ServiceLoader.load(JsonProvider.class);
                }
            };

    //Lazy initialization holder class idiom
    private static class JsonProviderHolder {
        static final JsonProvider defaultJsonProvider = initDefault();

        static JsonProvider initDefault() {
            try {
                Class<?> clazz = Class.forName(DEFAULT_PROVIDER);
                return (JsonProvider) clazz.newInstance();
            } catch (ClassNotFoundException x) {
                throw new JsonException(
                        "Provider " + DEFAULT_PROVIDER + " not found", x);
            } catch (Exception x) {
                throw new JsonException(
                        "Provider " + DEFAULT_PROVIDER + " could not be instantiated: " + x,
                        x);
            }
        }
    }

    protected JsonProvider() {
    }

    /**
     * Creates a JSON provider object. The provider is loaded using the
     * {@link ServiceLoader#load(Class)} method. If there are no available
     * service providers, this method returns the default service provider.
     *
     * @return a JSON provider
     * @see ServiceLoader
     */
    public static JsonProvider provider() {
        Iterator<JsonProvider> it = threadLoader.get().iterator();
        if (it.hasNext()) {
            return it.next();
        }

        return JsonProviderHolder.defaultJsonProvider;
    }

    /**
     * Creates a JSON parser from a character stream.
     *
     * @param reader i/o reader from which JSON is to be read
     * @return a JSON parser
     */
    public abstract JsonParser createParser(Reader reader);

    /**
     * Creates a JSON parser from the specified byte stream.
     * The character encoding of the stream is determined
     * as defined in <a href="http://tools.ietf.org/rfc/rfc7159.txt">RFC 7159
     * </a>.
     *
     * @param in i/o stream from which JSON is to be read
     * @return a JSON parser
     * @throws JsonException if encoding cannot be determined
     *                       or i/o error (IOException would be cause of JsonException)
     */
    public abstract JsonParser createParser(InputStream in);

    /**
     * Creates a parser factory for creating {@link JsonParser} instances.
     *
     * @return a JSON parser factory
     *
    public abstract JsonParserFactory createParserFactory();
     */

    /**
     * Creates a parser factory for creating {@link JsonParser} instances.
     * The factory is configured with the specified map of
     * provider specific configuration properties. Provider implementations
     * should ignore any unsupported configuration properties specified in
     * the map.
     *
     * @param config a map of provider specific properties to configure the
     *               JSON parsers. The map may be empty or null
     * @return a JSON parser factory
     */
    public abstract JsonParserFactory createParserFactory(Map<String, ?> config);

    /**
     * Creates a JSON generator for writing JSON text to a character stream.
     *
     * @param writer a i/o writer to which JSON is written
     * @return a JSON generator
     */
    public abstract JsonGenerator createGenerator(Writer writer);

    /**
     * Creates a JSON generator for writing JSON text to a byte stream.
     *
     * @param out i/o stream to which JSON is written
     * @return a JSON generator
     */
    public abstract JsonGenerator createGenerator(OutputStream out);

    /**
     * Creates a generator factory for creating {@link JsonGenerator} instances.
     *
     * @return a JSON generator factory
     *
    public abstract JsonGeneratorFactory createGeneratorFactory();
     */

    /**
     * Creates a generator factory for creating {@link JsonGenerator} instances.
     * The factory is configured with the specified map of provider specific
     * configuration properties. Provider implementations should
     * ignore any unsupported configuration properties specified in the map.
     *
     * @param config a map of provider specific properties to configure the
     *               JSON generators. The map may be empty or null
     * @return a JSON generator factory
     */
    public abstract JsonGeneratorFactory createGeneratorFactory(Map<String, ?> config);

    /**
     * Creates a JSON reader from a character stream.
     *
     * @param reader a reader from which JSON is to be read
     * @return a JSON reader
     */
    public abstract JsonReader createReader(Reader reader);

    /**
     * Creates a JSON reader from a byte stream. The character encoding of
     * the stream is determined as described in
     * <a href="http://tools.ietf.org/rfc/rfc7159.txt">RFC 7159</a>.
     *
     * @param in a byte stream from which JSON is to be read
     * @return a JSON reader
     */
    public abstract JsonReader createReader(InputStream in);

    /**
     * Creates a JSON writer to write a
     * JSON {@link JsonObject object} or {@link JsonArray array}
     * structure to the specified character stream.
     *
     * @param writer to which JSON object or array is written
     * @return a JSON writer
     */
    public abstract JsonWriter createWriter(Writer writer);

    /**
     * Creates a JSON writer to write a
     * JSON {@link JsonObject object} or {@link JsonArray array}
     * structure to the specified byte stream. Characters written to
     * the stream are encoded into bytes using UTF-8 encoding.
     *
     * @param out to which JSON object or array is written
     * @return a JSON writer
     */
    public abstract JsonWriter createWriter(OutputStream out);

    /**
     * Creates a writer factory for creating {@link JsonWriter} objects.
     * The factory is configured with the specified map of provider specific
     * configuration properties. Provider implementations should ignore any
     * unsupported configuration properties specified in the map.
     *
     * @param config a map of provider specific properties to configure the
     *               JSON writers. The map may be empty or null
     * @return a JSON writer factory
     */
    public abstract JsonWriterFactory createWriterFactory(Map<String, ?> config);

    /**
     * Creates a reader factory for creating {@link JsonReader} objects.
     * The factory is configured with the specified map of provider specific
     * configuration properties. Provider implementations should ignore any
     * unsupported configuration properties specified in the map.
     *
     * @param config a map of provider specific properties to configure the
     *               JSON readers. The map may be empty or null
     * @return a JSON reader factory
     */
    public abstract JsonReaderFactory createReaderFactory(Map<String, ?> config);

    /**
     * Creates a JSON object builder
     *
     * @return a JSON object builder
     */
    public abstract JsonObjectBuilder createObjectBuilder();

    /**
     * Creates a JSON object builder, initialized with the specified object.
     *
     * @param object the initial JSON object in the builder
     * @return a JSON object builder
     * @since 1.1
     */
    public JsonObjectBuilder createObjectBuilder(JsonObject object) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a JSON object builder, initialized with the specified Map.
     *
     * @return a JSON objecct builder
     * @since 1.1
     */
    public JsonObjectBuilder createObjectBuilder(Map<String, Object> map) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a JSON array builder
     *
     * @return a JSON array builder
     */
    public abstract JsonArrayBuilder createArrayBuilder();

    /**
     * Creates a JSON array builder, initialized with the specified array
     *
     * @param array the initial JSON array in the builder
     * @return a JSON array builder
     * @since 1.1
     */
    public JsonArrayBuilder createArrayBuilder(JsonArray array) {
        throw new UnsupportedOperationException();
    }


    /**
     * Creates a JSON array builder, initialized with the specified collection.
     *
     * @param collection the initial JSON collection in builder
     * @return a JSON array builder
     * @since 1.1
     */
    public JsonArrayBuilder createArrayBuilder(Collection<Object> collection) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a builder factory for creating {@link JsonArrayBuilder}
     * and {@link JsonObjectBuilder} objects.
     * The factory is configured with the specified map of provider specific
     * configuration properties. Provider implementations should ignore any
     * unsupported configuration properties specified in the map.
     *
     * @param config a map of provider specific properties to configure the
     *               JSON builders. The map may be empty or null
     * @return a JSON builder factory
     */
    public abstract JsonBuilderFactory createBuilderFactory(Map<String, ?> config);

    /**
     * Creates a JsonString
     *
     * @param value a JSON string
     * @return the JsonString for the string
     * @since 1.1
     */
    public JsonString createValue(String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a JsonNumber
     *
     * @param value a JSON number
     * @return the JsonNumber for the number
     * @since 1.1
     */
    public JsonNumber createValue(int value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a JsonNumber
     *
     * @param value a JSON number
     * @return the JsonNumber for the number
     * @since 1.1
     */
    public JsonNumber createValue(long value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a JsonNumber
     *
     * @param value a JSON number
     * @return the JsonNumber for the number
     * @since 1.1
     */
    public JsonNumber createValue(double value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a JsonNumber
     *
     * @param value a JSON number
     * @return the JsonNumber for the number
     * @since 1.1
     */
    public JsonNumber createValue(BigDecimal value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a JsonNumber
     *
     * @param value a JSON number
     * @return the JsonNumber for the number
     * @since 1.1
     */
    public JsonNumber createValue(BigInteger value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Construct and initialize a JsonPointer.
     *
     * @param jsonPointer the JSON Pointer string
     * @throws NullPointerException if {@code jsonPointer} is {@code null}
     * @throws JsonException        if {@code jsonPointer} is not a valid JSON Pointer
     */
    public abstract JsonPointer createJsonPointer(String jsonPointer);

    /**
     * Constructs a JsonPatch
     *
     * @param patch the JSON Patch
     */
    public abstract JsonPatch createJsonPatch(JsonArray patch);

    /**
     * Creates a JsonPatchBuilder, starting with the specified
     * JSON Patch
     *
     * @param patch the JSON Patch
     */
    public abstract JsonPatchBuilder createJsonPatchBuilder(JsonArray patch);

    /**
     * Creates JsonPatchBuilder with empty JSON Patch
     */
    public abstract JsonPatchBuilder createJsonPatchBuilder();
}