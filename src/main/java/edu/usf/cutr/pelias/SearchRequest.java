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

/**
 * Encapsulates a request to the Mapzen Pelias Search API - https://mapzen.com/documentation/search/search/
 */
public class SearchRequest extends PeliasRequest {

    public static class Builder extends PeliasRequest.Builder {

        /**
         * A Builder for making a request to the Pelias Search API
         *
         * @param apiKey the API key to be used in the request
         * @param text   the text to search for
         */
        public Builder(String apiKey, String text) {
            super(apiKey, text);
            // Set the default API URL for Search requests
            mApiEndPoint = "https://search.mapzen.com/v1/search";
        }
    }

    protected SearchRequest(String url) {
        super(url);
    }
}
