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

import edu.usf.cutr.pelias.model.Geocoding;
import org.geojson.Feature;

import java.util.Arrays;

/**
 * Encapsulates a response from the Mapzen Pelias Search API - https://mapzen.com/documentation/search/search/
 */
public class PeliasResponse {
    Geocoding geocoding;
    String type;
    Feature[] features;
    Float[] bbox;

    public Geocoding getGeocoding() {
        return geocoding;
    }

    public void setGeocoding(Geocoding geocoding) {
        this.geocoding = geocoding;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Feature[] getFeatures() {
        return features;
    }

    public void setFeatures(Feature[] features) {
        this.features = features;
    }

    public Float[] getBbox() {
        return bbox;
    }

    public void setBbox(Float[] bbox) {
        this.bbox = bbox;
    }

    @Override
    public String toString() {
        return "PeliasResponse{" +
                "geocoding=" + geocoding +
                ", type='" + type + '\'' +
                ", features=" + Arrays.toString(features) +
                ", bbox=" + Arrays.toString(bbox) +
                '}';
    }
}
