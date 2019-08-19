/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.batch.protocol.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Specifies how Tasks should be distributed across Compute Nodes.
 */
public class TaskSchedulingPolicy {
    /**
     * How Tasks are distributed across Compute Nodes in a Pool.
     * If not specified, the default is spread. Possible values include:
     * 'spread', 'pack'.
     */
    @JsonProperty(value = "nodeFillType", required = true)
    private ComputeNodeFillType nodeFillType;

    /**
     * Get if not specified, the default is spread. Possible values include: 'spread', 'pack'.
     *
     * @return the nodeFillType value
     */
    public ComputeNodeFillType nodeFillType() {
        return this.nodeFillType;
    }

    /**
     * Set if not specified, the default is spread. Possible values include: 'spread', 'pack'.
     *
     * @param nodeFillType the nodeFillType value to set
     * @return the TaskSchedulingPolicy object itself.
     */
    public TaskSchedulingPolicy withNodeFillType(ComputeNodeFillType nodeFillType) {
        this.nodeFillType = nodeFillType;
        return this;
    }

}