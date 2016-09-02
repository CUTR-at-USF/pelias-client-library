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
package edu.cutr.pelias.model;

/**
 * Created by Sean on 8/31/2016.
 */
public class Query {
    float boundaryCircleLat;
    float boundaryCircleLon;
    float boundaryCircleRadius;
    float pointLat;
    float pointLon;
    boolean isPrivate;
    int querySize;
    int size;
    String text;

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

    public float getPointLat() {
        return pointLat;
    }

    public void setPointLat(float pointLat) {
        this.pointLat = pointLat;
    }

    public float getPointLon() {
        return pointLon;
    }

    public void setPointLon(float pointLon) {
        this.pointLon = pointLon;
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

    @Override
    public String toString() {
        return "Query{" +
                "boundaryCircleLat=" + boundaryCircleLat +
                ", boundaryCircleLon=" + boundaryCircleLon +
                ", boundaryCircleRadius=" + boundaryCircleRadius +
                ", pointLat=" + pointLat +
                ", pointLon=" + pointLon +
                ", isPrivate=" + isPrivate +
                ", querySize=" + querySize +
                ", size=" + size +
                ", text='" + text + '\'' +
                '}';
    }
}
