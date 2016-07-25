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

import com.google.gson.annotations.SerializedName;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@SerializedNameOrder(
//        {
//                "MedalId",
//                "Count"
//        }
//)

public class MedalAward {

    /**
     * The ID of the Medal. Medals are available via the Metadata API.
     */
    @SerializedName("medalId")
    private long medalId;

    /**
     * The number of times the Medal was earned.
     */
    @SerializedName("count")
    private int count;

    private String name;

    public long getMedalId() {
        return medalId;
    }


    public int getCount() {
        return count;
    }

    public String getName(){return name;}


    public void setMedalId(long medalId){
        this.medalId = medalId;
    }

    public void setCount(Integer count){
        this.count = count;
    }

    public void setName(String name){this.name = name;}
}
