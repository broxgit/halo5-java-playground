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

package com.broxhouse.h5api.models.stats.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FlexibleStats {
    /**
     * The set of flexible stats that are derived from medal events.
     */
    @JsonProperty("MedalStatCounts")
    private List<StatCount> medalStatCounts;

    /**
     * The set of flexible stats that are derived from impulse events.
     */
    @JsonProperty("ImpulseStatCounts")
    private List<StatCount> impulseStatCounts;

    /**
     * The set of flexible stats that are derived from medal time lapses.
     */
    @JsonProperty("MedalTimelapses")
    private List<Timelapse> medalTimelapses;

    /**
     * The set of flexible stats that are derived from impulse time lapses.
     */
    @JsonProperty("ImpulseTimelapses")
    private List<Timelapse> impulseTimelapses;

    public List<StatCount> getMedalStatCounts() {
        return medalStatCounts;
    }

    public List<StatCount> getImpulseStatCounts() {
        return impulseStatCounts;
    }

    public List<Timelapse> getMedalTimelapses() {
        return medalTimelapses;
    }

    public List<Timelapse> getImpulseTimelapses() {
        return impulseTimelapses;
    }
}