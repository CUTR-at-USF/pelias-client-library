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
        
        private String mSources;
        private Double mFocusPointLat;
        private Double mFocusPointLon;
        private String mBoundaryMinLat;
        private String mBoundaryMinLon;
        private String mBoundaryMaxLat;
        private String mBoundaryMaxLon;
        private Integer mSize;
    
        /**
         * A Builder for making a request to the Search API
         * @param apiKey the API key to be used in the request
         * @param text the text to search for
         */
        public Builder(String apiKey, String text) {
            mApiKey = apiKey;
            mText = text;
        }

        public Builder setSources(String sources) {
            mSources = sources;
            return this;
        }
        public Builder setFocusPoint(Double lat, Double lon) {
            mFocusPointLat = lat;
            mFocusPointLon = lon;
            return this;
        }
        public Builder setBoundaryRect(String minLat, String minLon, String maxLat, String maxLon) {
            mBoundaryMinLat = minLat;
            mBoundaryMinLon = minLon;
            mBoundaryMaxLat = maxLat;
            mBoundaryMaxLon = maxLon;
            return this;
        }
        public Builder setSize(Integer size) {
            mSize = size;
            return this;
        }
        
        public PeliasRequest build() {            
            StringBuilder builder = new StringBuilder();
            builder.append("https://search.mapzen.com/v1/search?text=");
            builder.append(mText);
            builder.append("&api_key=");
            builder.append(mApiKey);
            
            if (mSources != null) {
                builder.append("&sources=");
                builder.append(mSources);
            }
        
            if (mSize != null) {
                builder.append("&size=");
                builder.append(mSize);                
            }
            
            if (mFocusPointLat != null && mFocusPointLon != null) {
                builder.append("&focus.point.lat=");
                builder.append(mFocusPointLat);                
                builder.append("&focus.point.lon=");
                builder.append(mFocusPointLon);                                
            }
            
            if (mBoundaryMinLat != null) {
                builder.append("&boundary.rect.min_lat=");
                builder.append(mBoundaryMinLat);
                builder.append("&boundary.rect.min_lon=");
                builder.append(mBoundaryMinLon);
                builder.append("&boundary.rect.max_lat=");
                builder.append(mBoundaryMaxLat);
                builder.append("&boundary.rect.max_lon=");
                builder.append(mBoundaryMaxLon);                
            }
                                                
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
