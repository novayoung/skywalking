/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.oap.server.core.query;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.skywalking.oap.server.core.analysis.IDManager;
import org.apache.skywalking.oap.server.core.query.type.Database;
import org.apache.skywalking.oap.server.core.query.type.Endpoint;
import org.apache.skywalking.oap.server.core.query.type.EndpointInfo;
import org.apache.skywalking.oap.server.core.query.type.Service;
import org.apache.skywalking.oap.server.core.query.type.ServiceInstance;
import org.apache.skywalking.oap.server.core.storage.StorageModule;
import org.apache.skywalking.oap.server.core.storage.query.IMetadataQueryDAO;
import org.apache.skywalking.oap.server.library.module.ModuleManager;

public class MetadataQueryService implements org.apache.skywalking.oap.server.library.module.Service {

    private final ModuleManager moduleManager;
    private IMetadataQueryDAO metadataQueryDAO;

    public MetadataQueryService(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    private IMetadataQueryDAO getMetadataQueryDAO() {
        if (metadataQueryDAO == null) {
            metadataQueryDAO = moduleManager.find(StorageModule.NAME).provider().getService(IMetadataQueryDAO.class);
        }
        return metadataQueryDAO;
    }

    public List<Service> getAllServices(final String group) throws IOException {
        return distinct(getMetadataQueryDAO().getAllServices(group), Comparator.comparing(service -> service.getId() + service.getName() + service.getGroup()));
    }

    public List<Service> getAllBrowserServices() throws IOException {
        return getMetadataQueryDAO().getAllBrowserServices();
    }

    public List<Database> getAllDatabases() throws IOException {
        return getMetadataQueryDAO().getAllDatabases();
    }

    public List<Service> searchServices(final long startTimestamp, final long endTimestamp,
                                        final String keyword) throws IOException {
        return distinct(getMetadataQueryDAO().searchServices(keyword), Comparator.comparing(service -> service.getId() + service.getName() + service.getGroup()));
    }

    public List<ServiceInstance> getServiceInstances(final long startTimestamp, final long endTimestamp,
                                                     final String serviceId) throws IOException {
        return getMetadataQueryDAO().getServiceInstances(startTimestamp, endTimestamp, serviceId);
    }

    public List<Endpoint> searchEndpoint(final String keyword, final String serviceId,
                                         final int limit) throws IOException {
        return distinct(getMetadataQueryDAO().searchEndpoint(keyword, serviceId, limit), Comparator.comparing(endpoint -> endpoint.getId() + endpoint.getName()));
    }

    public Service searchService(final String serviceCode) throws IOException {
        return getMetadataQueryDAO().searchService(serviceCode);
    }

    public EndpointInfo getEndpointInfo(final String endpointId) {
        final IDManager.EndpointID.EndpointIDDefinition endpointIDDefinition = IDManager.EndpointID.analysisId(
            endpointId);
        final IDManager.ServiceID.ServiceIDDefinition serviceIDDefinition = IDManager.ServiceID.analysisId(
            endpointIDDefinition.getServiceId());

        EndpointInfo endpointInfo = new EndpointInfo();
        endpointInfo.setId(endpointId);
        endpointInfo.setName(endpointIDDefinition.getEndpointName());
        endpointInfo.setServiceId(endpointIDDefinition.getServiceId());
        endpointInfo.setServiceName(serviceIDDefinition.getName());
        return endpointInfo;
    }

    private <T> List<T> distinct(List<T> list, Comparator<T> comparator) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(comparator)), ArrayList::new));
    }
}
