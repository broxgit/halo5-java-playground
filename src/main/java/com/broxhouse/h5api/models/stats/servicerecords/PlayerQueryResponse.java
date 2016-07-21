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


import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerQueryResponse<T> {

    /**
     * The player's gamertag.
     */
    @JsonProperty("Id")
    private String id;

    /**
     * The result of the query for the player. One of the following:
     *   Success = 0,
     *   NotFound = 1,
     *   ServiceFailure = 2,
     *   ServiceUnavailable = 3
     * It is possible for different requests from the batch to succeed and fail
     * independently.
     */
    @JsonProperty("ResultCode")
    private int resultCode;

    /**
     * The Service Record result for the player. Only set if ResultCode is Success.
     */
    @JsonProperty("Result")
    private T result;

    public String getId() {
        return id;
    }

    public int getResultCode() {
        return resultCode;
    }

    public T getResult() {
        return result;
    }
}
