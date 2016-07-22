/*
 * Copyright (c) 2015 Alex Hart
 *
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.broxhouse.h5api.models.stats.servicerecords;

import com.broxhouse.h5api.models.stats.reports.BaseStats;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CustomStat extends BaseStats {

    /**
     * List of custom stats by CustomGameBaseVariant
     */
    @JsonProperty("CustomGameBaseVariantStats")
    private List<GameBaseVariantStats> customGameBaseVariantStats;

    /**
     * A list of up to 3 game base variants with the highest win rate by the user.
     * If there is a tie, the one with more completions is higher. If there's still
     * a tie, the GUIDs are sorted and selected.
     */
    @JsonProperty("TopGameBaseVariants")
    private List<VariantRecord> topGameBaseVariants;

    public List<GameBaseVariantStats> getCustomGameBaseVariantStats() {
        return customGameBaseVariantStats;
    }

    public List<VariantRecord> getTopGameBaseVariants() {
        return topGameBaseVariants;
    }
}
