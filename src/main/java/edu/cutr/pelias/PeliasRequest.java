/*
 * Copyright (C) 2016 University of South Florida (sjbarbeau@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.cutr.pelias;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Encapsulates a request to the Mapzen Pelias Search API - https://mapzen.com/documentation/search/search/
 */
public class PeliasRequest {

    private static final ObjectMapper mMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final ObjectReader mReader = mMapper.readerFor(PeliasResponse.class);

    private URL mUrl;

    public static class Builder {
        private String mApiKey;
        private String mText;

        /**
         * A Builder for making a request to the Search API
         * @param apiKey the API key to be used in the request
         * @param text the text to search for
         */
        public Builder(String apiKey, String text) {
            mApiKey = apiKey;
            mText = text;
        }

        public PeliasRequest build() {
            StringBuilder builder = new StringBuilder();
            builder.append("https://search.mapzen.com/v1/search?text=");
            builder.append(mText);
            builder.append("&api_key=");
            builder.append(mApiKey);
            return new PeliasRequest(builder.toString());
        }
    }

    protected PeliasRequest(String url) {
        try {
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public PeliasResponse call() throws IOException {
        return mReader.readValue(mUrl.openStream());
    }

    /**
     * A temporary method to test requests
     * TODO - remove this method and create demo project
     * @param args
     */
    public static void main(String[] args) {
        try {
            String apiKey = "search-FdGeV9U";
            String text = "London";
            PeliasResponse response = new PeliasRequest.Builder(apiKey, text).build().call();
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
