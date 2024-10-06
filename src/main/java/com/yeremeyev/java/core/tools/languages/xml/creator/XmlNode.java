package com.yeremeyev.java.core.tools.languages.xml.creator;

import com.yeremeyev.java.core.interfaces.common.Releasable;
import com.yeremeyev.java.core.tools.languages.xml.exceptions.XmlException;
import com.yeremeyev.java.core.tools.languages.xml.tools.StringTransformer;
import com.yeremeyev.java.core.tools.languages.xml.tools.XmlOptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;

public class XmlNode implements Releasable {
    Document document;
    Element node;
    private List<XmlNode> childrenList;

    private void initializeDocument(XmlNode parentNode) throws XmlException {
        if (parentNode == null) {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                document = documentBuilder.newDocument();
            } catch (ParserConfigurationException exception) {
                throw new XmlException(exception.getMessage());
            }
        } else {
            document = parentNode.document;
        }
    }

    public XmlNode(XmlNode parentNode, String nodeName) throws XmlException {
        initializeDocument(parentNode);
        node = document.createElement(nodeName);

        if (parentNode == null) {
            document.appendChild(node);
        } else {
            parentNode.node.appendChild(node);
        }
        childrenList = new ArrayList<>();
    }

    public XmlNode(String nodeName) throws XmlException {
        this(null, nodeName);
    }

    @Override
    public void release() {
        childrenList.stream().forEach(XmlNode::release);
        node = null;
        document = null;
    }

    public XmlNode appendChild(String tagName) throws XmlException {
        XmlNode child = new XmlNode(this, tagName);
        childrenList.add(child);
        return child;
    }

    public void setAttribute(String name, String value) {
        node.setAttribute(name, value);
    }

    public void setValue(String value) {
        node.setTextContent(value);
    }

    public String toXml() throws XmlException {
        return StringTransformer.toXml(document, new XmlOptions());
    }

    public String toXml(XmlOptions xmlOptions) throws XmlException {
        return StringTransformer.toXml(document, xmlOptions);
    }
}