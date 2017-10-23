package com.mmsdivino.json;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class JSONData {
    public JSONData() {

    }

    protected static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String getURLContent(String urlString) {
        String jsonText = null;
        try {
            URL url = new URL(urlString);
            InputStream stream = url.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            jsonText = readAll(rd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonText;
    }
}
