package com.akhil2020171.blockchain.model;

public class Document {
    
    private String documentId;
    private String document_base64;
    private String documentExtension;
    private String documentName;
    private long documentSize;

    public Document(String document_base64, String documentExtension, String documentName) {
        this.document_base64 = document_base64;
        this.documentExtension = documentExtension;
        this.documentName = documentName;
    }

    public Document() {}

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocument_base64() {
        return document_base64;
    }

    public void setDocument_base64(String document_base64) {
        this.document_base64 = document_base64;
    }

    public String getDocumentExtension() {
        return documentExtension;
    }

    public void setDocumentExtension(String documentExtension) {
        this.documentExtension = documentExtension;
    }

    public long getDocumentSize() {
        return documentSize;
    }

    public void setDocumentSize(long long1) {
        this.documentSize = long1;
    }

    @Override
    public String toString() {
        return "Document [documentId=" + documentId + ", document_base64=" + document_base64 + ", documentExtension="
                + documentExtension + ", documentName=" + documentName + ", documentSize=" + documentSize + "]";
    }
}
