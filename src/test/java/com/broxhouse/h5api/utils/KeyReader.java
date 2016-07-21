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

package com.broxhouse.h5api.utils;
import com.broxhouse.h5api.MetadataTest;

import java.io.*;
import java.net.URL;

public class KeyReader {

    private KeyReader() { }

    public static String getApiKey() {
//        URL url = MetadataTest.class.getResource("/apiKey");
//        File apiFile = new File(url.getFile());
//        try {
//            FileInputStream inputStream = new FileInputStream(apiFile);
//            Reader r = new InputStreamReader(inputStream, "UTF-8");
//            StringBuilder sb = new StringBuilder();
//            char[] buf = new char[1024];
//            int amt = r.read(buf);
//            while (amt > 0) {
//                sb.append(buf, 0, amt);
//                amt = r.read(buf);
//            }
//            return sb.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
        String apiKey = "293bb4a86da743bdb983b97efa5bb265";
        return apiKey;
    }
}
