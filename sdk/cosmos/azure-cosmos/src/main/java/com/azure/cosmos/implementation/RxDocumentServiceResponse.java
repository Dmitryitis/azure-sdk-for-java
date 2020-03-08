// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.cosmos.implementation;

import com.azure.cosmos.model.ModelBridgeInternal;
import com.azure.cosmos.model.Permission;
import com.azure.cosmos.implementation.directconnectivity.Address;
import com.azure.cosmos.implementation.directconnectivity.StoreResponse;
import com.azure.cosmos.BridgeInternal;
import com.azure.cosmos.CosmosResponseDiagnostics;
import com.azure.cosmos.model.Resource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is core Transport/Connection agnostic response for the Azure Cosmos DB database service.
 */
public class RxDocumentServiceResponse {
    private final int statusCode;
    private final Map<String, String> headersMap;
    private final StoreResponse storeResponse;

    public RxDocumentServiceResponse(StoreResponse response) {
        String[] headerNames = response.getResponseHeaderNames();
        String[] headerValues = response.getResponseHeaderValues();

        this.headersMap = new HashMap<>(headerNames.length);

        // Gets status code.
        this.statusCode = response.getStatus();

        // Extracts headers.
        for (int i = 0; i < headerNames.length; i++) {
            this.headersMap.put(headerNames[i], headerValues[i]);
        }

        this.storeResponse = response;
    }

    public static <T extends Resource> String getResourceKey(Class<T> c) {
        if (c.equals(Conflict.class)) {
            return InternalConstants.ResourceKeys.CONFLICTS;
        } else if (c.equals(Database.class)) {
            return InternalConstants.ResourceKeys.DATABASES;
        } else if (Document.class.isAssignableFrom(c)) {
            return InternalConstants.ResourceKeys.DOCUMENTS;
        } else if (c.equals(DocumentCollection.class)) {
            return InternalConstants.ResourceKeys.DOCUMENT_COLLECTIONS;
        } else if (c.equals(Offer.class)) {
            return InternalConstants.ResourceKeys.OFFERS;
        } else if (c.equals(Permission.class)) {
            return InternalConstants.ResourceKeys.PERMISSIONS;
        } else if (c.equals(Trigger.class)) {
            return InternalConstants.ResourceKeys.TRIGGERS;
        } else if (c.equals(StoredProcedure.class)) {
            return InternalConstants.ResourceKeys.STOREDPROCEDURES;
        } else if (c.equals(User.class)) {
            return InternalConstants.ResourceKeys.USERS;
        } else if (c.equals(UserDefinedFunction.class)) {
            return InternalConstants.ResourceKeys.USER_DEFINED_FUNCTIONS;
        } else if (c.equals(Address.class)) {
            return InternalConstants.ResourceKeys.ADDRESSES;
        } else if (c.equals(PartitionKeyRange.class)) {
            return InternalConstants.ResourceKeys.PARTITION_KEY_RANGES;
        }

        throw new IllegalArgumentException("c");
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public Map<String, String> getResponseHeaders() {
        return this.headersMap;
    }

    public byte[] getResponseBodyAsByteArray() {
        return this.storeResponse.getResponseBody();
    }

    public String getResponseBodyAsString() {
        return Utils.utf8StringFromOrNull(this.getResponseBodyAsByteArray());
    }

    public <T extends Resource> T getResource(Class<T> c) {
        String responseBody = this.getResponseBodyAsString();
        if (StringUtils.isEmpty(responseBody))
            return null;

        T resource = null;
        try {
            resource =  c.getConstructor(String.class).newInstance(responseBody);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new IllegalStateException("Failed to instantiate class object.", e);
        }
        if(PathsHelper.isPublicResource(resource)) {
            BridgeInternal.setAltLink(resource, PathsHelper.generatePathForNameBased(resource, this.getOwnerFullName(),resource.getId()));
        }

        return resource;
    }

    public <T extends Resource> List<T> getQueryResponse(Class<T> c) {
        byte[] responseBody = this.getResponseBodyAsByteArray();
        if (responseBody == null) {
            return new ArrayList<T>();
        }

        JsonNode jobject = fromJson(responseBody);
        String resourceKey = RxDocumentServiceResponse.getResourceKey(c);
        ArrayNode jTokenArray = (ArrayNode) jobject.get(resourceKey);

        // Aggregate queries may return a nested array
        ArrayNode innerArray;
        while (jTokenArray != null && jTokenArray.size() == 1 && (innerArray = toArrayNode(jTokenArray.get(0))) != null) {
            jTokenArray = innerArray;
        }

        List<T> queryResults = new ArrayList<T>();

        if (jTokenArray != null) {
            for (int i = 0; i < jTokenArray.size(); ++i) {
                JsonNode jToken = jTokenArray.get(i);
                // Aggregate on single partition collection may return the aggregated value only
                // In that case it needs to encapsulated in a special document

                JsonNode resourceJson = jToken.isValueNode() || jToken.isArray()// to add nulls, arrays, objects
                        ? fromJson(String.format("{\"%s\": %s}", Constants.Properties.VALUE, jToken.toString()))
                                : jToken;

               T resource = (T) ModelBridgeInternal.instantiateJsonSerializable((ObjectNode) resourceJson, c);
               queryResults.add(resource);
            }
        }

        return queryResults;
    }

    private ArrayNode toArrayNode(JsonNode n) {
        if (n.isArray()) {
            return (ArrayNode) n;
        } else {
            return null;
        }
    }

    private static JsonNode fromJson(String json){
        try {
            return Utils.getSimpleObjectMapper().readTree(json);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Unable to parse JSON %s", json), e);
        }
    }

    private static JsonNode fromJson(byte[] json){
        try {
            return Utils.getSimpleObjectMapper().readTree(json);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Unable to parse JSON %s", Arrays.toString(json)), e);
        }
    }

    private static String toJson(Object object){
        try {
            return Utils.getSimpleObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Can't serialize the object into the json string", e);
        }
    }

    private String getOwnerFullName() {
        if (this.headersMap != null) {
            return this.headersMap.get(HttpConstants.HttpHeaders.OWNER_FULL_NAME);
        }
        return null;
    }

    CosmosResponseDiagnostics getCosmosResponseRequestDiagnosticStatistics() {
        if (this.storeResponse == null) {
            return null;
        }
        return this.storeResponse.getCosmosResponseDiagnostics();
    }
}
