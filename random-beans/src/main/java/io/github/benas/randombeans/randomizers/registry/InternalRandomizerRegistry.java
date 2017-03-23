/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */

package io.github.benas.randombeans.randomizers.registry;

import io.github.benas.randombeans.annotation.Priority;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Registry for Java built-in types.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
@Priority(-255)
public class InternalRandomizerRegistry implements RandomizerRegistry {

    private final Map<Class<?>, Randomizer<?>> randomizers = new HashMap();

    @Override
    public void setSeed(final long seed) {
        randomizers.put(String.class, new StringRandomizer(seed));
        randomizers.put(Character.class, new CharacterRandomizer(seed));
        randomizers.put(char.class, new CharacterRandomizer(seed));
        randomizers.put(Boolean.class, new BooleanRandomizer(seed));
        randomizers.put(boolean.class, new BooleanRandomizer(seed));
        randomizers.put(Byte.class, new ByteRandomizer(seed));
        randomizers.put(byte.class, new ByteRandomizer(seed));
        randomizers.put(Short.class, new ShortRandomizer(seed));
        randomizers.put(short.class, new ShortRandomizer(seed));
        randomizers.put(Integer.class, new IntegerRandomizer(seed));
        randomizers.put(int.class, new IntegerRandomizer(seed));
        randomizers.put(Long.class, new LongRandomizer(seed));
        randomizers.put(long.class, new LongRandomizer(seed));
        randomizers.put(Double.class, new DoubleRandomizer(seed));
        randomizers.put(double.class, new DoubleRandomizer(seed));
        randomizers.put(Float.class, new FloatRandomizer(seed));
        randomizers.put(float.class, new FloatRandomizer(seed));
        randomizers.put(BigInteger.class, new BigIntegerRandomizer(seed));
        randomizers.put(BigDecimal.class, new BigDecimalRandomizer(seed));
        randomizers.put(AtomicLong.class, new AtomicLongRandomizer(seed));
        randomizers.put(AtomicInteger.class, new AtomicIntegerRandomizer(seed));
        randomizers.put(Date.class, new DateRandomizer(seed));
        randomizers.put(java.sql.Date.class, new SqlDateRandomizer(seed));
        randomizers.put(java.sql.Time.class, new SqlTimeRandomizer(seed));
        randomizers.put(java.sql.Timestamp.class, new SqlTimestampRandomizer(seed));
        randomizers.put(Calendar.class, new CalendarRandomizer(seed));
        randomizers.put(URL.class, new UrlRandomizer(seed));
        randomizers.put(URI.class, new UriRandomizer(seed));
    }

    @Override
    public Randomizer<?> getRandomizer(final Field field) {
        return getRandomizer(field.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Randomizer<?> getRandomizer(Class<?> type) {
        return randomizers.get(type);
    }
}
