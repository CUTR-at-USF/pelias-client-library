/*
 * Copyright (C) 2016 University of South Florida, Sean J. Barbeau (sjbarbeau@gmail.com)
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
package edu.usf.cutr.pelias;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Encapsulates a request to the Mapzen Pelias Search API - https://mapzen.com/documentation/search/search/
 */
public class SearchRequest {

    private static ObjectMapper mMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static ObjectReader mReader = mMapper.readerFor(SearchResponse.class);

    private URL mUrl;

    public static class Builder {
        private String mApiEndPoint = "https://search.mapzen.com/v1/search";
        private String mApiKey;
        private String mText;
        private String mSources;
        private Double mFocusPointLat;
        private Double mFocusPointLon;
        private Double mBoundaryMinLat;
        private Double mBoundaryMinLon;
        private Double mBoundaryMaxLat;
        private Double mBoundaryMaxLon;
        private Integer mSize;

        /**
         * A Builder for making a request to the Pelias Search API
         *
         * @param apiKey the API key to be used in the request
         * @param text   the text to search for
         */
        public Builder(String apiKey, String text) {
            mApiKey = apiKey;

            try {
                mText = URLEncoder.encode(text, "UTF-8");
            } catch (java.io.UnsupportedEncodingException e) {
                   System.err.println( String.format("URLEncoder.encode failed (%s), using unencoded text parameter.", e.getMessage()) );
                   mText = text;
            }

        }

        /**
         * Sets the source to search.  Mapzen Search brings together data from multiple open sources and combines a
         * variety of place types into a single database, allowing you options for selecting the dataset you want to
         * search.
         * <p>
         * If you wanted to combine several data sources together, set sources to a comma separated list of desired
         * source names. Note that the order of the comma separated values does not impact sorting order of the results;
         * they are still sorted based on the linguistic match quality to text and distance from focus, if you specified
         * one.
         * <p>
         * Source           Name	        Short name
         * OpenStreetMap	openstreetmap	osm
         * OpenAddresses	openaddresses	oa
         * Who’s on First	whosonfirst	    wof
         * GeoNames	        geonames	    gn
         * <p>
         * See https://mapzen.com/documentation/search/data-sources/ for more details.
         *
         * @param sources the source to search.  Valid values as of Sept. 2016 are osm, oa, wof, and gn (see above).
         * @return this same Builder so Builder calls can be chained
         */
        public Builder setSources(String sources) {
            mSources = sources;
            return this;
        }

        /**
         * Sets a focus point for this search.  By specifying a focus.point, nearby places will be scored higher
         * depending on how close they are to the focus.point so that places with higher scores will appear higher in
         * the results list. The effect of this scoring boost diminishes to zero after 100 kilometers away from the
         * focus.point. After all the nearby results have been found, additional results will come from the rest of the
         * world, without any further location-based prioritization.
         *
         * @param lat Latitude of the focus point
         * @param lon Longitude of the focus point
         * @return this same Builder so Builder calls can be chained
         */
        public Builder setFocusPoint(Double lat, Double lon) {
            mFocusPointLat = lat;
            mFocusPointLon = lon;
            return this;
        }

        /**
         * Sets the bounding box in which to search
         * @param minLat Minimum latitude of the bounding box
         * @param minLon Minimum longitude of the bounding box
         * @param maxLat Maximum latitude of the bounding box
         * @param maxLon Maximum longitude of the bounding box
         * @return this same Builder so Builder calls can be chained
         */
        public Builder setBoundaryRect(Double minLat, Double minLon, Double maxLat, Double maxLon) {
            mBoundaryMinLat = minLat;
            mBoundaryMinLon = minLon;
            mBoundaryMaxLat = maxLat;
            mBoundaryMaxLon = maxLon;
            return this;
        }

        /**
         * Sets the number of results to return from the API (default is 10 if unset)
         * @param size the number of results to return from the API (default is 10 if unset)
         * @return this same Builder so Builder calls can be chained
         */
        public Builder setSize(Integer size) {
            mSize = size;
            return this;
        }

        /**
         * Sets the API endpoint that will be used for the API call.  By default https://search.mapzen.com/v1/search
         * will be used.
         *
         * @param apiEndpoint the API endpoint to be used in the API call
         * @return this same Builder so Builder calls can be chained
         */
        public Builder setApiEndpoint(String apiEndpoint) {
            mApiEndPoint = apiEndpoint;
            return this;
        }

        /**
         * Builds the SearchRequest using the specified parameters
         * @return the SearchRequest using the specified parameters
         */
        public SearchRequest build() {
            StringBuilder builder = new StringBuilder();
            builder.append(mApiEndPoint);
            builder.append("?text=");
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

            return new SearchRequest(builder.toString());
        }
    }

    protected SearchRequest(String url) {
        try {
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the URL that will be used in the API request
     *
     * @return the URL that will be used in the API request
     */
    URL getUrl() {
        return mUrl;
    }

    /**
     * Makes the request to the Pelias Search API, and returns a SearchResponse parsed from the returned JSON
     * @return a SearchResponse parsed from the returned JSON
     * @throws IOException
     */
    public SearchResponse call() throws IOException {
        return mReader.readValue(mUrl.openStream());
    }

    /**
     * Sets the Jackson value for "fail on unknown properties" for all SearchRequest instances.  Note that while this
     * method is threadsafe, it does not guarantee that existing instances of SearchRequest using the ObjectReader won't
     * be negatively impacted / interrupted.  Default is false.
     *
     * @param value true if Jackson should fail on unknown properties, false of it should not (default is false)
     */
    public synchronized static void setFailOnUnknownProperties(boolean value) {
        mMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, value);
        mReader = mMapper.readerFor(SearchResponse.class);
    }

    /**
     * A temporary method to test requests
     * TODO - remove this method and create demo project
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            String apiKey = "search-FdGeV9U";
            String text = "London";
            SearchResponse response = new SearchRequest.Builder(apiKey, text).build().call();
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
