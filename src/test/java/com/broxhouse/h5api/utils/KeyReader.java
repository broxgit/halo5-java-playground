

package com.broxhouse.h5api.utils;

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
