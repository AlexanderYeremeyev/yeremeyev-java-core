package com.yeremeyev.java.core.tools.languages.xml.reader;

import com.yeremeyev.java.core.tools.languages.xml.exceptions.XmlException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class XmlReader {

    public static XmlNodeReadable readXml(String xmlContent) throws XmlException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document document = docBuilder.parse(new ByteArrayInputStream(xmlContent.getBytes()));

            Node rootNode = document.getFirstChild();
            if (rootNode == null) {
                throw new XmlException("Cannot parse xml content");
            }
            return new XmlNodeReadable(rootNode);
        } catch (Exception exception) {
            throw new XmlException(exception.getMessage());
        }
    }
}
