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
package edu.usf.cutr.pelias.test;

import edu.usf.cutr.pelias.PeliasRequest;
import edu.usf.cutr.pelias.PeliasResponse;
import junit.framework.TestCase;
import org.geojson.Feature;
import org.geojson.Point;
import org.junit.Test;

import java.io.IOException;

/**
 * Tests for PeliasRequest and PeliasResponse
 */
public class PeliasTest extends TestCase {

    private static final String SIMPLE_SEARCH_ENDPOINT = "https://raw.githubusercontent.com/CUTR-at-USF/pelias-client-library/master/src/test/resources/simple-search.json";
    private static final String SEARCH_WITH_FOCUS_ENDPOINT = "https://raw.githubusercontent.com/CUTR-at-USF/pelias-client-library/master/src/test/resources/search-with-focus.json";
    private static final String API_KEY = "search-FdGeV9U";
    private static final String TEXT = "subway";

    @Override
    protected void setUp() {
        // For tests, make sure that we can parse all known properties
        PeliasRequest.setFailOnUnknownProperties(true);
    }

    @Test
    public void testSimpleSearch() throws IOException {
        PeliasResponse r = new PeliasRequest.Builder(API_KEY, TEXT)
                .setApiEndpoint(SIMPLE_SEARCH_ENDPOINT)
                .build().call();
        assertEquals("0.1", r.getGeocoding().getVersion());
        assertEquals("https://search.mapzen.com/v1/attribution", r.getGeocoding().getAttribution());
        assertEquals("subway", r.getGeocoding().getQuery().getText());
        assertEquals(10, r.getGeocoding().getQuery().getSize());
        assertEquals(false, r.getGeocoding().getQuery().isPrivate());
        assertEquals(20, r.getGeocoding().getQuery().getQuerySize());
        assertEquals("Pelias", r.getGeocoding().getEngine().getName());
        assertEquals("Mapzen", r.getGeocoding().getEngine().getAuthor());
        assertEquals("1.0", r.getGeocoding().getEngine().getVersion());
        assertEquals(1474249958653L, r.getGeocoding().getTimestamp());
        assertEquals("FeatureCollection", r.getType());

        Point p;
        Feature[] f = r.getFeatures();

        p = (Point) f[0].getGeometry();
        assertEquals(35.798729, p.getCoordinates().getLatitude());
        assertEquals(-117.872003, p.getCoordinates().getLongitude());
        assertEquals("node:2357470967", (String) f[0].getProperties().get("id"));
        assertEquals("venue", f[0].getProperties().get("layer"));
        assertEquals("Subway", f[0].getProperties().get("name"));
        assertEquals(0.6, f[0].getProperties().get("confidence"));
        assertEquals("United States", f[0].getProperties().get("country"));
        assertEquals("California", f[0].getProperties().get("region"));
        assertEquals("Inyo County", f[0].getProperties().get("county"));
        assertEquals("Subway, Pearsonville, CA, USA", f[0].getProperties().get("label"));

        p = (Point) f[1].getGeometry();
        assertEquals(-37.708951, p.getCoordinates().getLatitude());
        assertEquals(144.961816, p.getCoordinates().getLongitude());
        assertEquals("node:2321936555", (String) f[1].getProperties().get("id"));
        assertEquals("venue", f[1].getProperties().get("layer"));
        assertEquals("Subway", f[1].getProperties().get("name"));
        assertEquals(0.6, f[1].getProperties().get("confidence"));
        assertEquals("Australia", f[1].getProperties().get("country"));
        assertEquals("Victoria", f[1].getProperties().get("region"));
        assertEquals("Moreland (C)", f[1].getProperties().get("county"));
        assertEquals("Fawkner", f[1].getProperties().get("localadmin"));
        assertEquals("Fawkner", f[1].getProperties().get("locality"));
        assertEquals("Fawkner", f[1].getProperties().get("neighbourhood"));
        assertEquals("Subway, Fawkner, Victoria, Australia", f[1].getProperties().get("label"));

        Float[] bbox = r.getBbox();
        assertEquals(-122.33372F, bbox[0]);
        assertEquals(-37.708951F, bbox[1]);
        assertEquals(152.91174F, bbox[2]);
        assertEquals(64.166014F, bbox[3]);
    }

    @Test
    public void testSearchWithFocus() throws IOException {
        PeliasResponse r = new PeliasRequest.Builder(API_KEY, TEXT)
                .setApiEndpoint(SEARCH_WITH_FOCUS_ENDPOINT)
                .build().call();

        assertEquals("0.1", r.getGeocoding().getVersion());
        assertEquals("https://search.mapzen.com/v1/attribution", r.getGeocoding().getAttribution());
        assertEquals("subway", r.getGeocoding().getQuery().getText());
        assertEquals(10, r.getGeocoding().getQuery().getSize());
        assertEquals(false, r.getGeocoding().getQuery().isPrivate());
        assertEquals(28.061062f, r.getGeocoding().getQuery().getFocusPointLat());
        assertEquals(-82.4132f, r.getGeocoding().getQuery().getFocusPointLon());
        assertEquals(20, r.getGeocoding().getQuery().getQuerySize());
        assertEquals("Pelias", r.getGeocoding().getEngine().getName());
        assertEquals("Mapzen", r.getGeocoding().getEngine().getAuthor());
        assertEquals("1.0", r.getGeocoding().getEngine().getVersion());
        assertEquals(1473772363957L, r.getGeocoding().getTimestamp());
        assertEquals("FeatureCollection", r.getType());

        assertEquals("0.1", r.getGeocoding().getVersion());
        assertEquals("https://search.mapzen.com/v1/attribution", r.getGeocoding().getAttribution());
        assertEquals("subway", r.getGeocoding().getQuery().getText());
        assertEquals(10, r.getGeocoding().getQuery().getSize());
        assertEquals(false, r.getGeocoding().getQuery().isPrivate());
        assertEquals(20, r.getGeocoding().getQuery().getQuerySize());
        assertEquals("Pelias", r.getGeocoding().getEngine().getName());
        assertEquals("Mapzen", r.getGeocoding().getEngine().getAuthor());
        assertEquals("1.0", r.getGeocoding().getEngine().getVersion());
        assertEquals(1473772363957L, r.getGeocoding().getTimestamp());
        assertEquals("FeatureCollection", r.getType());

        Point p;
        Feature[] f = r.getFeatures();

        p = (Point) f[0].getGeometry();
        assertEquals(28.0597, p.getCoordinates().getLatitude());
        assertEquals(-82.410544, p.getCoordinates().getLongitude());
        assertEquals("node:570760825", (String) f[0].getProperties().get("id"));
        assertEquals("venue", f[0].getProperties().get("layer"));
        assertEquals("Subway", f[0].getProperties().get("name"));
        assertEquals(0.95, f[0].getProperties().get("confidence"));
        assertEquals(1.784, f[1].getProperties().get("distance"));
        assertEquals("United States", f[0].getProperties().get("country"));
        assertEquals("Florida", f[0].getProperties().get("region"));
        assertEquals("Hillsborough County", f[0].getProperties().get("county"));
        assertEquals("Subway, Tampa, FL, USA", f[0].getProperties().get("label"));

        p = (Point) f[1].getGeometry();
        assertEquals(28.053915, p.getCoordinates().getLatitude());
        assertEquals(-82.396939, p.getCoordinates().getLongitude());
        assertEquals("node:832851671", (String) f[1].getProperties().get("id"));
        assertEquals("venue", f[1].getProperties().get("layer"));
        assertEquals("Subway", f[1].getProperties().get("name"));
        assertEquals(0.946, f[1].getProperties().get("confidence"));
        assertEquals(1.784, f[1].getProperties().get("distance"));
        assertEquals("United States", f[1].getProperties().get("country"));
        assertEquals("Florida", f[1].getProperties().get("region"));
        assertEquals("Hillsborough County", f[1].getProperties().get("county"));
        assertNull(f[1].getProperties().get("localadmin"));
        assertEquals("Temple Terrace", f[1].getProperties().get("locality"));
        assertEquals("Terrace Walk", f[1].getProperties().get("neighbourhood"));
        assertEquals("Subway, Temple Terrace, FL, USA", f[1].getProperties().get("label"));

        Float[] bbox = r.getBbox();
        assertEquals(-82.515286F, bbox[0]);
        assertEquals(27.959868F, bbox[1]);
        assertEquals(-82.367646F, bbox[2]);
        assertEquals(28.13147F, bbox[3]);

    }
}
