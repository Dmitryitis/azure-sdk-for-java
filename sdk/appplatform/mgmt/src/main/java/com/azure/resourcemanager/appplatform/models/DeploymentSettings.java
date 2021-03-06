// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.appplatform.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.util.logging.ClientLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/** The DeploymentSettings model. */
@Fluent
public final class DeploymentSettings {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(DeploymentSettings.class);

    /*
     * Required CPU, basic tier should be 1, standard tier should be in range
     * (1, 4)
     */
    @JsonProperty(value = "cpu")
    private Integer cpu;

    /*
     * Required Memory size in GB, basic tier should be in range (1, 2),
     * standard tier should be in range (1, 8)
     */
    @JsonProperty(value = "memoryInGB")
    private Integer memoryInGB;

    /*
     * JVM parameter
     */
    @JsonProperty(value = "jvmOptions")
    private String jvmOptions;

    /*
     * Instance count, basic tier should be in range (1, 25), standard tier
     * should be in range (1, 500)
     */
    @JsonProperty(value = "instanceCount")
    private Integer instanceCount;

    /*
     * Collection of environment variables
     */
    @JsonProperty(value = "environmentVariables")
    private Map<String, String> environmentVariables;

    /*
     * Runtime version
     */
    @JsonProperty(value = "runtimeVersion")
    private RuntimeVersion runtimeVersion;

    /**
     * Get the cpu property: Required CPU, basic tier should be 1, standard tier should be in range (1, 4).
     *
     * @return the cpu value.
     */
    public Integer cpu() {
        return this.cpu;
    }

    /**
     * Set the cpu property: Required CPU, basic tier should be 1, standard tier should be in range (1, 4).
     *
     * @param cpu the cpu value to set.
     * @return the DeploymentSettings object itself.
     */
    public DeploymentSettings withCpu(Integer cpu) {
        this.cpu = cpu;
        return this;
    }

    /**
     * Get the memoryInGB property: Required Memory size in GB, basic tier should be in range (1, 2), standard tier
     * should be in range (1, 8).
     *
     * @return the memoryInGB value.
     */
    public Integer memoryInGB() {
        return this.memoryInGB;
    }

    /**
     * Set the memoryInGB property: Required Memory size in GB, basic tier should be in range (1, 2), standard tier
     * should be in range (1, 8).
     *
     * @param memoryInGB the memoryInGB value to set.
     * @return the DeploymentSettings object itself.
     */
    public DeploymentSettings withMemoryInGB(Integer memoryInGB) {
        this.memoryInGB = memoryInGB;
        return this;
    }

    /**
     * Get the jvmOptions property: JVM parameter.
     *
     * @return the jvmOptions value.
     */
    public String jvmOptions() {
        return this.jvmOptions;
    }

    /**
     * Set the jvmOptions property: JVM parameter.
     *
     * @param jvmOptions the jvmOptions value to set.
     * @return the DeploymentSettings object itself.
     */
    public DeploymentSettings withJvmOptions(String jvmOptions) {
        this.jvmOptions = jvmOptions;
        return this;
    }

    /**
     * Get the instanceCount property: Instance count, basic tier should be in range (1, 25), standard tier should be in
     * range (1, 500).
     *
     * @return the instanceCount value.
     */
    public Integer instanceCount() {
        return this.instanceCount;
    }

    /**
     * Set the instanceCount property: Instance count, basic tier should be in range (1, 25), standard tier should be in
     * range (1, 500).
     *
     * @param instanceCount the instanceCount value to set.
     * @return the DeploymentSettings object itself.
     */
    public DeploymentSettings withInstanceCount(Integer instanceCount) {
        this.instanceCount = instanceCount;
        return this;
    }

    /**
     * Get the environmentVariables property: Collection of environment variables.
     *
     * @return the environmentVariables value.
     */
    public Map<String, String> environmentVariables() {
        return this.environmentVariables;
    }

    /**
     * Set the environmentVariables property: Collection of environment variables.
     *
     * @param environmentVariables the environmentVariables value to set.
     * @return the DeploymentSettings object itself.
     */
    public DeploymentSettings withEnvironmentVariables(Map<String, String> environmentVariables) {
        this.environmentVariables = environmentVariables;
        return this;
    }

    /**
     * Get the runtimeVersion property: Runtime version.
     *
     * @return the runtimeVersion value.
     */
    public RuntimeVersion runtimeVersion() {
        return this.runtimeVersion;
    }

    /**
     * Set the runtimeVersion property: Runtime version.
     *
     * @param runtimeVersion the runtimeVersion value to set.
     * @return the DeploymentSettings object itself.
     */
    public DeploymentSettings withRuntimeVersion(RuntimeVersion runtimeVersion) {
        this.runtimeVersion = runtimeVersion;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }
}
