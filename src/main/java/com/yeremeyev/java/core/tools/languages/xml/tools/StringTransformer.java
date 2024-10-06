package com.yeremeyev.java.core.tools.languages.xml.tools;

import com.yeremeyev.java.core.tools.languages.xml.exceptions.XmlException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class StringTransformer {

    public static String toXml(Node node, XmlOptions xmlOptions) throws XmlException {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            if (xmlOptions.isSkipXmlDeclaration()) {
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            }
            //transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            if (xmlOptions.isUseIndent()) {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            }
            transformer.setOutputProperty(OutputKeys.ENCODING, xmlOptions.getEncoding());

            if (xmlOptions.isHideStandalone() && node instanceof Document) {
                ((Document) node).setXmlStandalone(true);
                transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "");
            }

            DOMSource domSource = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);

            transformer.transform(domSource, streamResult);

            return stringWriter.toString();
        } catch (Exception exception) {
            throw new XmlException(exception.getMessage());
        }
    }

    public static String toXml(Node node) throws XmlException {
        return toXml(node, new XmlOptions());
    }
}
