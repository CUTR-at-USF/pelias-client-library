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

import junit.framework.TestCase;
import org.geojson.Feature;
import org.geojson.Point;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Tests for AutocompleteRequest and PeliasResponse
 */
public class AutocompleteTest extends TestCase {

    private static final String AUTOCOMPLETE_WITH_FOCUS_ENDPOINT = "https://raw.githubusercontent.com/CUTR-at-USF/pelias-client-library/master/src/test/resources/autocomplete-with-focus.json";
    private static final String AUTOCOMPLETE_WITH_SOURCES_ENDPOINT = "https://raw.githubusercontent.com/CUTR-at-USF/pelias-client-library/master/src/test/resources/autocomplete-with-sources.json";
    private static final String AUTOCOMPLETE_WITH_CATEGORIES_ENDPOINT = "https://raw.githubusercontent.com/CUTR-at-USF/pelias-client-library/master/src/test/resources/autocomplete-with-categories.json";

    private static final String API_KEY = "dummyApiKey";
    private static final String TEXT = "union square";

    @Override
    protected void setUp() {
        // For tests, make sure that we can parse all known properties
        SearchRequest.setFailOnUnknownProperties(false);
    }

    @Test
    public void testAutocompleteWithFocus() throws IOException {
        PeliasRequest request = new SearchRequest.Builder(API_KEY, TEXT)
                .setApiEndpoint(AUTOCOMPLETE_WITH_FOCUS_ENDPOINT)
                .setFocusPoint(37.7d, -122.4d)
                .build();

        assertEquals("https://raw.githubusercontent.com/CUTR-at-USF/pelias-client-library/master/src/test/" +
                        "resources/autocomplete-with-focus.json?text=union+square&api_key=dummyApiKey&focus.point.lat=37.7&focus.point.lon=-122.4",
                request.getUrl().toString());

        PeliasResponse response = request.call();

        assertEquals("0.2", response.getGeocoding().getVersion());
        assertEquals("https://search.mapzen.com/v1/attribution", response.getGeocoding().getAttribution());
        assertEquals("union square", response.getGeocoding().getQuery().getText());
        assertEquals(10, response.getGeocoding().getQuery().getSize());
        assertEquals(false, response.getGeocoding().getQuery().isPrivate());
        assertEquals(37.7f, response.getGeocoding().getQuery().getFocusPointLat());
        assertEquals(-122.4f, response.getGeocoding().getQuery().getFocusPointLon());
        assertEquals(0, response.getGeocoding().getQuery().getQuerySize());
        assertEquals("Pelias", response.getGeocoding().getEngine().getName());
        assertEquals("Mapzen", response.getGeocoding().getEngine().getAuthor());
        assertEquals("1.0", response.getGeocoding().getEngine().getVersion());
        assertEquals(1483999860603L, response.getGeocoding().getTimestamp());
        assertEquals("FeatureCollection", response.getType());

        assertEquals("0.2", response.getGeocoding().getVersion());
        assertEquals("https://search.mapzen.com/v1/attribution", response.getGeocoding().getAttribution());
        assertEquals("union square", response.getGeocoding().getQuery().getText());
        assertEquals(10, response.getGeocoding().getQuery().getSize());
        assertEquals(false, response.getGeocoding().getQuery().isPrivate());
        assertEquals(0, response.getGeocoding().getQuery().getQuerySize());
        assertEquals("Pelias", response.getGeocoding().getEngine().getName());
        assertEquals("Mapzen", response.getGeocoding().getEngine().getAuthor());
        assertEquals("1.0", response.getGeocoding().getEngine().getVersion());
        assertEquals(1483999860603L, response.getGeocoding().getTimestamp());
        assertEquals("FeatureCollection", response.getType());

        Point p;
        Feature[] f = response.getFeatures();

        p = (Point) f[0].getGeometry();
        assertEquals(37.590715, p.getCoordinates().getLatitude());
        assertEquals(-122.020001, p.getCoordinates().getLongitude());
        assertEquals("way:93179455", (String) f[0].getProperties().get("id"));
        assertEquals("address", f[0].getProperties().get("layer"));
        assertEquals("2 Union Square", f[0].getProperties().get("name"));
        assertEquals("2", f[0].getProperties().get("housenumber"));
        assertEquals("Union Square", f[0].getProperties().get("street"));
        assertEquals("94587", f[0].getProperties().get("postalcode"));
        assertEquals(35.704, f[1].getProperties().get("distance"));
        assertEquals("point", f[0].getProperties().get("accuracy"));
        assertEquals("United States", f[0].getProperties().get("country"));
        assertEquals("California", f[0].getProperties().get("region"));
        assertEquals("Alameda County", f[0].getProperties().get("county"));
        assertEquals("2 Union Square, Union City, CA, USA", f[0].getProperties().get("label"));

        p = (Point) f[1].getGeometry();
        assertEquals(37.590628, p.getCoordinates().getLatitude());
        assertEquals(-122.019542, p.getCoordinates().getLongitude());
        assertEquals("way:93179457", (String) f[1].getProperties().get("id"));
        assertEquals("address", f[1].getProperties().get("layer"));
        assertEquals("4 Union Square", f[1].getProperties().get("name"));
        assertEquals("4", f[1].getProperties().get("housenumber"));
        assertEquals("Union Square", f[1].getProperties().get("street"));

        assertEquals(35.704, f[1].getProperties().get("distance"));
        assertEquals("United States", f[1].getProperties().get("country"));
        assertEquals("California", f[1].getProperties().get("region"));
        assertEquals("Alameda County", f[1].getProperties().get("county"));
        assertEquals("Union City", f[1].getProperties().get("locality"));
        assertEquals("Pabrico", f[1].getProperties().get("neighbourhood"));
        assertEquals("4 Union Square, Union City, CA, USA", f[1].getProperties().get("label"));

        Float[] bbox = response.getBbox();
        assertEquals(-122.4104F, bbox[0]);
        assertEquals(37.59063F, bbox[1]);
        assertEquals(-122.01954F, bbox[2]);
        assertEquals(37.7872F, bbox[3]);
    }

    @Test
    public void testAutocompleteWithSources() throws IOException {
        PeliasRequest request = new SearchRequest.Builder(API_KEY, "pennsylvania")
                .setApiEndpoint(AUTOCOMPLETE_WITH_SOURCES_ENDPOINT)
                .setSources("openaddresses")
                .build();

        assertEquals("https://raw.githubusercontent.com/CUTR-at-USF/pelias-client-library/master/src/test/" +
                        "resources/autocomplete-with-sources.json?" +
                        "text=pennsylvania&api_key=dummyApiKey&sources=openaddresses",
                request.getUrl().toString());

        PeliasResponse response = request.call();

        assertEquals("0.2", response.getGeocoding().getVersion());
        assertEquals("https://search.mapzen.com/v1/attribution", response.getGeocoding().getAttribution());
        assertEquals("pennsylvania", response.getGeocoding().getQuery().getText());
        assertEquals(false, response.getGeocoding().getQuery().isPrivate());
        assertEquals(0, response.getGeocoding().getQuery().getQuerySize());
        assertEquals("Pelias", response.getGeocoding().getEngine().getName());
        assertEquals("Mapzen", response.getGeocoding().getEngine().getAuthor());
        assertEquals("1.0", response.getGeocoding().getEngine().getVersion());
        assertEquals(1483999981344L, response.getGeocoding().getTimestamp());
        assertEquals("FeatureCollection", response.getType());

        Point p;
        Feature[] f = response.getFeatures();

        p = (Point) f[0].getGeometry();
        assertEquals(40.089612, p.getCoordinates().getLatitude());
        assertEquals(-74.903515, p.getCoordinates().getLongitude());
        assertEquals("us/pa/bucks:d0713c98d1d941ea", (String) f[0].getProperties().get("id"));
        assertEquals("address", f[0].getProperties().get("layer"));
        assertEquals("openaddresses", f[1].getProperties().get("source"));
        assertEquals("912 Pennsylvania", f[0].getProperties().get("name"));
        assertEquals("912", f[0].getProperties().get("housenumber"));
        assertEquals("Pennsylvania", f[0].getProperties().get("street"));
        assertEquals("point", f[0].getProperties().get("accuracy"));
        assertEquals("United States", f[0].getProperties().get("country"));
        assertEquals("Pennsylvania", f[0].getProperties().get("region"));
        assertEquals("Bucks County", f[0].getProperties().get("county"));
        assertEquals("912 Pennsylvania, Croydon, PA, USA", f[0].getProperties().get("label"));

        p = (Point) f[1].getGeometry();
        assertEquals(40.090544, p.getCoordinates().getLatitude());
        assertEquals(-74.90221, p.getCoordinates().getLongitude());
        assertEquals("us/pa/bucks:2ade01267f68afe1", (String) f[1].getProperties().get("id"));
        assertEquals("address", f[1].getProperties().get("layer"));
        assertEquals("openaddresses", f[1].getProperties().get("source"));
        assertEquals("1105 Pennsylvania", f[1].getProperties().get("name"));
        assertEquals("United States", f[1].getProperties().get("country"));
        assertEquals("Pennsylvania", f[1].getProperties().get("region"));
        assertEquals("Bucks County", f[1].getProperties().get("county"));
        assertEquals("Bristol", f[1].getProperties().get("localadmin"));
        assertEquals("Croydon", f[1].getProperties().get("locality"));
        assertEquals("Croydon Heights", f[1].getProperties().get("neighbourhood"));
        assertEquals("1105 Pennsylvania, Croydon, PA, USA", f[1].getProperties().get("label"));

        Float[] bbox = response.getBbox();
        assertEquals(-79.70637F, bbox[0]);
        assertEquals(39.816734F, bbox[1]);
        assertEquals(-74.90221F, bbox[2]);
        assertEquals(41.38278F, bbox[3]);
    }

    @Test
    public void testAutocompleteWithCategories() throws IOException {
        PeliasRequest request = new SearchRequest.Builder(API_KEY, TEXT)
                .setApiEndpoint(AUTOCOMPLETE_WITH_CATEGORIES_ENDPOINT)
                .setFocusPoint(32.85254317331236d, -117.10268815207026d)
                .setCategories("")
                .build();

        assertEquals("https://raw.githubusercontent.com/CUTR-at-USF/pelias-client-library/master/src/test/" +
                        "resources/autocomplete-with-categories.json?text=union+square&api_key=dummyApiKey&focus.point.lat=32.85254317331236&focus.point.lon=-117.10268815207026&categories=",
                request.getUrl().toString());

        PeliasResponse response = request.call();

        assertEquals("0.2", response.getGeocoding().getVersion());
        assertEquals("https://geocode.earth/guidelines", response.getGeocoding().getAttribution());
        assertEquals("iris ave", response.getGeocoding().getQuery().getText());
        assertEquals(false, response.getGeocoding().getQuery().isPrivate());
        assertEquals(32.85254317331236f, response.getGeocoding().getQuery().getFocusPointLat());
        assertEquals(-117.10268815207026f, response.getGeocoding().getQuery().getFocusPointLon());
        assertEquals("Pelias", response.getGeocoding().getEngine().getName());
        assertEquals("Mapzen", response.getGeocoding().getEngine().getAuthor());
        assertEquals("1.0", response.getGeocoding().getEngine().getVersion());
        assertEquals(1588344563735L, response.getGeocoding().getTimestamp());
        assertEquals("FeatureCollection", response.getType());

        Point p;
        Feature[] f = response.getFeatures();

        p = (Point) f[0].getGeometry();
        assertEquals(32.56993, p.getCoordinates().getLatitude());
        assertEquals(-117.067507, p.getCoordinates().getLongitude());
        assertEquals("node/1815010914", (String) f[0].getProperties().get("id"));
        assertEquals("venue", f[0].getProperties().get("layer"));
        assertEquals("Iris Avenue", f[0].getProperties().get("name"));
        assertEquals(31.632, f[0].getProperties().get("distance"));
        assertEquals("point", f[0].getProperties().get("accuracy"));
        assertEquals("United States", f[0].getProperties().get("country"));
        assertEquals("California", f[0].getProperties().get("region"));
        assertEquals("San Diego County", f[0].getProperties().get("county"));
        assertEquals("Iris Avenue, San Diego, CA, USA", f[0].getProperties().get("label"));

        ArrayList<String> categories = (ArrayList<String>) f[0].getProperties().get("category");
        assertEquals("transport", categories.get(0));
        assertEquals("transport:public", categories.get(1));

        Float[] bbox = response.getBbox();
        assertEquals(-122.247064F, bbox[0]);
        assertEquals(32.569744F, bbox[1]);
        assertEquals(-105.268425F, bbox[2]);
        assertEquals(40.036903F, bbox[3]);
    }

    @Test
    public void testRequestParameters() throws IOException {
        PeliasRequest request = new AutocompleteRequest.Builder(API_KEY, TEXT)
                .setApiEndpoint(AUTOCOMPLETE_WITH_FOCUS_ENDPOINT)
                .setFocusPoint(28.061062d, -82.4132d)
                .setSources("osm")
                .setBoundaryRect(27.959868, -82.515286, 28.131471, -82.367646)
                .setCategories("")
                .build();

        assertEquals("https://raw.githubusercontent.com/CUTR-at-USF/pelias-client-library/master/src/test/resources/" +
                        "autocomplete-with-focus.json?text=union+square&api_key=dummyApiKey&sources=osm" +
                        "&focus.point.lat=28.061062&focus.point.lon=-82.4132" +
                        "&boundary.rect.min_lat=27.959868&boundary.rect.min_lon=-82.515286" +
                        "&boundary.rect.max_lat=28.131471&boundary.rect.max_lon=-82.367646" +
                        "&categories=",
                request.getUrl().toString());

        // Test space in text parameter
        request = new AutocompleteRequest.Builder(API_KEY, "burger king")
                .setApiEndpoint(AUTOCOMPLETE_WITH_FOCUS_ENDPOINT)
                .setFocusPoint(28.061062d, -82.4132d)
                .setSources("osm")
                .setBoundaryRect(27.959868, -82.515286, 28.131471, -82.367646)
                .build();

        assertEquals("https://raw.githubusercontent.com/CUTR-at-USF/pelias-client-library/master/src/test/resources/" +
                        "autocomplete-with-focus.json?text=burger+king&api_key=dummyApiKey&sources=osm" +
                        "&focus.point.lat=28.061062&focus.point.lon=-82.4132" +
                        "&boundary.rect.min_lat=27.959868&boundary.rect.min_lon=-82.515286" +
                        "&boundary.rect.max_lat=28.131471&boundary.rect.max_lon=-82.367646",
                request.getUrl().toString());

    }
}
