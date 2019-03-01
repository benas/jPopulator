/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package io.github.benas.randombeans.randomizers.range;

import static io.github.benas.randombeans.randomizers.range.OffsetDateTimeRangeRandomizer.aNewOffsetDateTimeRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.benas.randombeans.util.Constants;

public class OffsetDateTimeRangeRandomizerTest extends AbstractRangeRandomizerTest<OffsetDateTime> {

    private OffsetDateTime minOffsetDateTime, maxOffsetDateTime;

    @BeforeEach
    public void setUp() {
        minOffsetDateTime = Constants.DEFAULT_DATES_RANGE.getMin().toOffsetDateTime().minusYears(50);
        maxOffsetDateTime = Constants.DEFAULT_DATES_RANGE.getMax().toOffsetDateTime().plusYears(50);
        randomizer = aNewOffsetDateTimeRangeRandomizer(minOffsetDateTime, maxOffsetDateTime);
    }

    @Test
    public void generatedOffsetDateTimeShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    public void generatedOffsetDateTimeShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minOffsetDateTime, maxOffsetDateTime);
    }

    @Test
    public void generatedOffsetDateTimeShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = aNewOffsetDateTimeRangeRandomizer(minOffsetDateTime, maxOffsetDateTime, SEED);
        OffsetDateTime expected = OffsetDateTime.parse("2046-10-12T17:24:27+01:00");

        // When
        OffsetDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    public void whenSpecifiedMinOffsetDateTimeIsAfterMaxOffsetDateTime_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> aNewOffsetDateTimeRangeRandomizer(maxOffsetDateTime, minOffsetDateTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenSpecifiedMinOffsetDateTimeIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = aNewOffsetDateTimeRangeRandomizer(null, maxOffsetDateTime);

        // When
        OffsetDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBeforeOrEqualTo(maxOffsetDateTime);
    }

    @Test
    public void whenSpecifiedMaxOffsetDateTimeIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = aNewOffsetDateTimeRangeRandomizer(minOffsetDateTime, null);

        // when
        OffsetDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isAfterOrEqualTo(minOffsetDateTime);
    }
}
