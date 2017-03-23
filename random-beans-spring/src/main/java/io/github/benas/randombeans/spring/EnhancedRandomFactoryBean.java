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

package io.github.benas.randombeans.spring;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.FieldDefinition;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import org.springframework.beans.factory.FactoryBean;

import java.util.ArrayList;
import java.util.List;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static io.github.benas.randombeans.FieldDefinitionBuilder.field;

/**
 * Spring Factory Bean that creates {@link EnhancedRandom} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EnhancedRandomFactoryBean implements FactoryBean<EnhancedRandom> {

    private List<RandomizerBean<?, ?>> randomizers = new ArrayList();

    @Override
    public EnhancedRandom getObject() throws Exception {
        EnhancedRandomBuilder enhancedRandomBuilder = aNewEnhancedRandomBuilder();
        for (RandomizerBean<?, ?> bean : randomizers) {
            FieldDefinition<?, ?> fieldDefinition = field()
                    .named(bean.getFieldName())
                    .ofType(bean.getFieldType())
                    .inClass(bean.getType())
                    .get();
            Randomizer<?> randomizer = bean.getRandomizer();
            enhancedRandomBuilder.randomize(fieldDefinition, randomizer);
        }

        return enhancedRandomBuilder.build();
    }

    @Override
    public Class<EnhancedRandom> getObjectType() {
        return EnhancedRandom.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setRandomizers(List<RandomizerBean<?, ?>> randomizers) {
        this.randomizers = randomizers;
    }

}
