package com.fost.prop.api;

import com.fost.prop.api.model.Node;

public interface PropertiesService {
    Node getPropertiesTree(String applicationName, int version);
    Node getTree(String[] applicationNames, int[] versions);
}
