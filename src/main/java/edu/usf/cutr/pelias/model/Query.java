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
package edu.usf.cutr.pelias.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * POJO class for Query element in Pelias response
 */
public class Query {
    float boundaryCircleLat;
    float boundaryCircleLon;
    float boundaryCircleRadius;

    @JsonProperty("focus.point.lat")
    float focusPointLat;
    @JsonProperty("focus.point.lon")
    float focusPointLon;

    boolean isPrivate;
    int querySize;
    int size;
    String[] sources;
    String text;

    @JsonProperty("parsed_text")
    ParsedText parsedText;

    String[] tokens;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getBoundaryCircleLat() {
        return boundaryCircleLat;
    }

    public void setBoundaryCircleLat(float boundaryCircleLat) {
        this.boundaryCircleLat = boundaryCircleLat;
    }

    public float getBoundaryCircleLon() {
        return boundaryCircleLon;
    }

    public void setBoundaryCircleLon(float boundaryCircleLon) {
        this.boundaryCircleLon = boundaryCircleLon;
    }

    public float getBoundaryCircleRadius() {
        return boundaryCircleRadius;
    }

    public void setBoundaryCircleRadius(float boundaryCircleRadius) {
        this.boundaryCircleRadius = boundaryCircleRadius;
    }

    public float getFocusPointLat() {
        return focusPointLat;
    }

    public void setFocusPointLat(float pointLat) {
        this.focusPointLat = pointLat;
    }

    public float getFocusPointLon() {
        return focusPointLon;
    }

    public void setFocusPointLon(float pointLon) {
        this.focusPointLon = pointLon;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public int getQuerySize() {
        return querySize;
    }

    public void setQuerySize(int querySize) {
        this.querySize = querySize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String[] getSources() {
        return sources;
    }

    public void setSources(String[] sources) {
        this.sources = sources;
    }

    public ParsedText getParsedText() {
        return parsedText;
    }

    public void setParsedText(ParsedText parsedText) {
        this.parsedText = parsedText;
    }

    public String[] getTokens() {
        return tokens;
    }

    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "Query{" +
                "boundaryCircleLat=" + boundaryCircleLat +
                ", boundaryCircleLon=" + boundaryCircleLon +
                ", boundaryCircleRadius=" + boundaryCircleRadius +
                ", focusPointLat=" + focusPointLat +
                ", focusPointLon=" + focusPointLon +
                ", isPrivate=" + isPrivate +
                ", querySize=" + querySize +
                ", size=" + size +
                ", sources=" + Arrays.toString(sources) +
                ", text='" + text + '\'' +
                ", parsedText=" + parsedText +
                '}';
    }
}
