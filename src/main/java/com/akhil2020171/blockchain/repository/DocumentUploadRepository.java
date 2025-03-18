package com.akhil2020171.blockchain.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.akhil2020171.blockchain.model.Document;

@Repository
public class DocumentUploadRepository {
    // Repository class for handling document upload requests

    private JdbcTemplate jdbcTemplate;

    public DocumentUploadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to upload a document to the database
    public void uploadDocument(String documentBase64, String documentExtension, String documentName) {
        try{
            String sql = "INSERT INTO documents (document_base64, document_extension, document_name) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, documentBase64, documentExtension, documentName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error uploading document");
        }
    }

        // Method to get all documents metadata for the table view
    public List<Document> getAllDocumentsMetaData() {
        String sql = "SELECT ID, document_name, document_extension, document_size, document_base64 FROM documents";
        return jdbcTemplate.query(sql, new RowMapper<Document>() {
            @Override
            public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
                Document document = new Document();
                document.setDocumentId(rs.getString("ID"));
                document.setDocumentName(rs.getString("document_name"));
                document.setDocumentExtension(rs.getString("document_extension"));
                document.setDocumentSize(rs.getLong("document_size"));
                document.setDocument_base64(rs.getString("document_base64"));
                return document;
            }
        });
    }

    // Existing method to get a document by ID (if needed)
    public String getDocument(String ID) {
        try {
            String sql = "SELECT document_base64 FROM documents WHERE ID = ?";
            return jdbcTemplate.queryForObject(sql, String.class, ID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error getting document by ID: " + ID);
        }
    }
}
