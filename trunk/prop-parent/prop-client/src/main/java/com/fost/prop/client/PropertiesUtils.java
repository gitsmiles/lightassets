package com.fost.prop.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fost.esb.hessian.FostHessianProxyFactory;
import com.fost.prop.api.PropertiesService;
import com.fost.prop.api.model.Attribute;
import com.fost.prop.api.model.Node;

public class PropertiesUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);

    private static final String CONFIG_FILENAME = "/prop.properties";

    private static final String DEFAULT_CHAR_SET = "UTF-8";

    private static final String PATH_DELIMITER = "/";

    private static final String NAME_DELIMITER = ",";

    private static final String VIRTUAL_NODE_NAME = "virtualRoot";

    private static final FostHessianProxyFactory HESSIAN_PROXY_FACTORY = new FostHessianProxyFactory();

    private static PropertiesService propertiesService;

    private static volatile Node rootNode;

    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    static {
        Properties config = new Properties();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(PropertiesUtils.class.getResourceAsStream(CONFIG_FILENAME), DEFAULT_CHAR_SET));
            config.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Can not find config file：" + CONFIG_FILENAME, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // ignore
                    LOGGER.error("", e);
                }
            }
        }

        String url = StringUtils.trim(config.getProperty("prop.hessian.url"));
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("Can not find \"prop.hessian.url\" in " + CONFIG_FILENAME);
        }

        String applicationName = StringUtils.trim(config.getProperty("prop.applicationName"));
        if (StringUtils.isEmpty(applicationName)) {
            throw new RuntimeException("Can not find \"prop.applicationName\" in " + CONFIG_FILENAME);
        }

        final String[] applicationNames = applicationName.split(NAME_DELIMITER);
        final int[] versions = new int[applicationNames.length];
        for (int i = 0; i < applicationNames.length; ++i) {
            applicationNames[i] = StringUtils.trim(applicationNames[i]);
            versions[i] = -1;
        }

        try {
            propertiesService = (PropertiesService) HESSIAN_PROXY_FACTORY.create(PropertiesService.class, url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        rootNode = propertiesService.getTree(applicationNames, versions);

        final PropertiesService finalPropertiesService = propertiesService;

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] nodeNames;
                    int[] nodeVersions;
                    if (rootNode != null) {
                        List<Node> nodes = rootNode.getChildList();
                        if (nodes.size() == applicationNames.length) {
                            nodeNames = new String[nodes.size()];
                            nodeVersions = new int[nodes.size()];
                            for (int i = 0; i < nodes.size(); ++i) {
                                nodeNames[i] = nodes.get(i).getName();
                                nodeVersions[i] = nodes.get(i).getVersion();
                            }
                        } else {
                            nodeNames = applicationNames;
                            nodeVersions = versions;
                        }
                    } else {
                        nodeNames = applicationNames;
                        nodeVersions = versions;
                    }
                    Node newNode = finalPropertiesService.getTree(nodeNames, nodeVersions);
                    if (newNode == null) {
                        return;
                    } else {
                        List<Node> newNodes = newNode.getChildList();
                        String[] newNodeNames = new String[newNodes.size()];
                        int[] newNodeVersions = new int[newNodes.size()];
                        for (int i = 0; i < newNodes.size(); ++i) {
                            newNodeNames[i] = newNodes.get(i).getName();
                            newNodeVersions[i] = newNodes.get(i).getVersion();
                        }
                        LOGGER.info("Parameters have been updated: old node {}, old version {}, new node {}, new version {}", new Object[] {
                                Arrays.asList(nodeNames), Arrays.asList(ArrayUtils.toObject(nodeVersions)),
                                Arrays.asList(newNodeNames), Arrays.asList(ArrayUtils.toObject(newNodeVersions))
                        });
                    }
                    rootNode = newNode;
                } catch (Throwable e) {
                    LOGGER.error("", e);
                }
            }
        }, 60, 120, TimeUnit.SECONDS);
    }

    public static List<Attribute> getProperties(String path) {
        Node node = getPropertiesNode(getNodeNames(path), rootNode, 0);
        if (node != null) {
            return node.getAttributeList();
        }

        throw new RuntimeException("Can not find node from path [" + path + "]");
    }

    public static String getProperty(String path, String key) {
        Node node = getPropertiesNode(getNodeNames(path), rootNode, 0);
        if (node != null) {
            for (Attribute attribute : node.getAttributeList()) {
                if (attribute.getKey().equalsIgnoreCase(key)) {
                    return attribute.getValue();
                }
            }
        } else {
            throw new RuntimeException("Can not find node from path [" + path + "]");
        }

        return null;
    }

    public static String getProperty(String path, String key, String defaultValue) {
        String value = getProperty(path, key);
        return value != null ? value : defaultValue;
    }

    public static Attribute getAttribute(String path, String key) {
        Node node = getPropertiesNode(getNodeNames(path), rootNode, 0);
        if (node != null) {
            for (Attribute attribute : node.getAttributeList()) {
                if (attribute.getKey().equalsIgnoreCase(key)) {
                    return attribute;
                }
            }
        } else {
            throw new RuntimeException("Can not find node from path [" + path + "]");
        }

        return null;
    }

    private static String[] getNodeNames(String path) {
        String[] nodeNames;

        if (path.startsWith(PATH_DELIMITER)) {
            path = VIRTUAL_NODE_NAME + path;
        } else {
            path = VIRTUAL_NODE_NAME + PATH_DELIMITER + path;
        }

        if (path.indexOf(PATH_DELIMITER) == -1) {
            nodeNames = new String[1];
            nodeNames[0] = path;
        } else {
            nodeNames = path.split(PATH_DELIMITER);
        }

        return nodeNames;
    }

    private static Node getPropertiesNode(String[] nodeNames, Node parentNode, int index) {
        Node node = null;

        if (parentNode == null) {
            return node;
        }

        if (parentNode.getName().equalsIgnoreCase((nodeNames[index]))) {
            if (index == nodeNames.length - 1) {
                return parentNode;
            } else {
                List<Node> childList = parentNode.getChildList();
                for (int i = 0; i < childList.size(); ++i) {
                    Node tempNode = childList.get(i);
                    if (tempNode.getName().equalsIgnoreCase(nodeNames[index + 1])) {
                        node = getPropertiesNode(nodeNames, tempNode, index + 1);
                        if (node != null) {
                            break;
                        }
                    }
                }
            }
        }

        return node;
    }
}
