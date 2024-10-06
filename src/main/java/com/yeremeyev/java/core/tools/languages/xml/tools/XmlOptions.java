package com.yeremeyev.java.core.tools.languages.xml.tools;

/**
 * alexander yeremeyev
 */
public class XmlOptions {
    private final static String ENCODING_UTF_8 = "UTF-8";

    private boolean skipXmlDeclaration;
    private String encoding;
    private boolean useIndent; //indent-amount
    private boolean hideStandalone;

    public XmlOptions() {
        setEncoding(ENCODING_UTF_8);
        setSkipXmlDeclaration(true);
        setUseIndent(false);
        setHideStandalone(true);
    }

    public boolean isSkipXmlDeclaration() {
        return skipXmlDeclaration;
    }

    public void setSkipXmlDeclaration(boolean skipXmlDeclaration) {
        this.skipXmlDeclaration = skipXmlDeclaration;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isUseIndent() {
        return useIndent;
    }

    public void setUseIndent(boolean useIndent) {
        this.useIndent = useIndent;
    }

    public boolean isHideStandalone() {
        return hideStandalone;
    }

    public void setHideStandalone(boolean hideStandalone) {
        this.hideStandalone = hideStandalone;
    }
}
