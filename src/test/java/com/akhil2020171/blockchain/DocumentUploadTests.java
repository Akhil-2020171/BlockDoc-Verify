package com.akhil2020171.blockchain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.akhil2020171.blockchain.controller.DocumentUploadController;

public class DocumentUploadTests {
    // Controller Endpoint : /api/v1/uploadDocument

    @Autowired
    private DocumentUploadController documentUploadController;

    // Test case 1
    // Test case for checking if the document is uploaded successfully
    
    @Test
    public void testUploadDocument() {
        // Create a mock MultipartFile object
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
        // Call the controller method
        ResponseEntity<?> response = documentUploadController.uploadDocumentFTP(file);
        // Check if the response is successful
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
