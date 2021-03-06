/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cosmosdb.v2020_04_01;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The SpatialSpec model.
 */
public class SpatialSpec {
    /**
     * The path for which the indexing behavior applies to. Index paths
     * typically start with root and end with wildcard (/path/*).
     */
    @JsonProperty(value = "path")
    private String path;

    /**
     * List of path's spatial type.
     */
    @JsonProperty(value = "types")
    private List<SpatialType> types;

    /**
     * Get the path for which the indexing behavior applies to. Index paths typically start with root and end with wildcard (/path/*).
     *
     * @return the path value
     */
    public String path() {
        return this.path;
    }

    /**
     * Set the path for which the indexing behavior applies to. Index paths typically start with root and end with wildcard (/path/*).
     *
     * @param path the path value to set
     * @return the SpatialSpec object itself.
     */
    public SpatialSpec withPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * Get list of path's spatial type.
     *
     * @return the types value
     */
    public List<SpatialType> types() {
        return this.types;
    }

    /**
     * Set list of path's spatial type.
     *
     * @param types the types value to set
     * @return the SpatialSpec object itself.
     */
    public SpatialSpec withTypes(List<SpatialType> types) {
        this.types = types;
        return this;
    }

}
