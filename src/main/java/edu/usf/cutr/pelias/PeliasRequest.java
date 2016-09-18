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
package edu.usf.cutr.pelias;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
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
         * A Builder for making a request to the Pelias Search API
         *
         * @param apiKey the API key to be used in the request
         * @param text   the text to search for
         */
        public Builder(String apiKey, String text) {
            mApiKey = apiKey;
            mText = text;
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
        public Builder setBoundaryRect(String minLat, String minLon, String maxLat, String maxLon) {
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
         * Builds the PeliasRequest using the specified parameters
         * @return the PeliasRequest using the specified parameters
         */
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

    /**
     * Makes the request to the Pelias Search API, and returns a PeliasResponse parsed from the returned JSON
     * @return a PeliasResponse parsed from the returned JSON
     * @throws IOException
     */
    public PeliasResponse call() throws IOException {
        HttpURLConnection urlConnection = null;
        BufferedInputStream bis = null;
        IOException exception = null;
        PeliasResponse response = null;

        try {
            urlConnection = (HttpURLConnection) mUrl.openConnection();
            bis = new BufferedInputStream(urlConnection.getInputStream());
            response = mReader.readValue(bis);
        } catch (IOException e) {
            exception = e;
        } finally {
            // Make sure we close the connection no matter what
            if (bis != null) {
                bis.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        if (exception != null) {
            throw exception;
        } else {
            return response;
        }
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
            PeliasResponse response = new PeliasRequest.Builder(apiKey, text).build().call();
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
