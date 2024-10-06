package com.yeremeyev.java.core.tools.languages.xml.reader;

import com.yeremeyev.java.core.interfaces.common.Releasable;
import com.yeremeyev.java.core.tools.languages.xml.exceptions.XmlException;
import com.yeremeyev.java.core.tools.languages.xml.tools.StringTransformer;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XmlNodeReadable implements Releasable {
    private static final short TARGET_NODE_TYPE = Node.ELEMENT_NODE;

    private Node node;
    private List<XmlNodeReadable> childrenList;

    private void initializeChildren() {
        if (childrenList != null) {
            return;
        }
        childrenList = new ArrayList<>();

        NodeList childrenNodeList = node.getChildNodes();
        int childrenCount = childrenNodeList.getLength();
        for (int index = 0; index < childrenCount; index++) {
            Node childNode = childrenNodeList.item(index);
            short nodeType = childNode.getNodeType();
            if (nodeType != TARGET_NODE_TYPE) {
                continue;
            }
            childrenList.add(new XmlNodeReadable(childNode));
        }
    }

    XmlNodeReadable(Node node) {
        this.node = node;
        initializeChildren();
    }

    public String getName() {
        return node.getNodeName();
    }

    public String getValue() {
        return node.getTextContent();
    }

    public String getAttribute(String attributeName, String defaultValue) {
        NamedNodeMap attributesMap = node.getAttributes();
        Node nameAttributeNode = attributesMap.getNamedItem(attributeName);
        if (nameAttributeNode == null) {
            return defaultValue;
        }
        return nameAttributeNode.getNodeValue();
    }

    public String getAttribute(String attributeName) {
        return getAttribute(attributeName, null);
    }

    public Map<String, String> getAttributesMap() {
        Map<String, String> resultsMap = new HashMap<>();

        NamedNodeMap attributesMap = node.getAttributes();
        int count = attributesMap.getLength();
        for (int index = 0; index < count; index++) {
            Node attributeNode = attributesMap.item(index);
            resultsMap.put(attributeNode.getNodeName(), attributeNode.getNodeValue());
        }
        return resultsMap;
    }

    public int getChildrensCount() {
        return childrenList.size();
    }

    public XmlNodeReadable getItem(int itemIndex) {
        if (itemIndex < 0 || childrenList.size() <= itemIndex) {
            return null;
        }
        return childrenList.get(itemIndex);
    }

    public List<XmlNodeReadable> getItems() {
        return childrenList;
    }

    public List<XmlNodeReadable> getItems(String name) {
        return childrenList.stream()
                .filter(item -> item.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public void release() {
        childrenList.stream().forEach(XmlNodeReadable::release);
        node = null;
    }

    public String toXml() throws XmlException {
        return StringTransformer.toXml(node);
    }
}
