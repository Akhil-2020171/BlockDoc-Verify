package com.akhil2020171.blockchain.controller;

import com.akhil2020171.blockchain.model.Document;
import com.akhil2020171.blockchain.service.DocumentUploadService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

@RestController()
public class DocumentUploadController {
    // Controller class for handling document upload requests

    @Autowired
    private final DocumentUploadService documentUploadService;

    public DocumentUploadController(DocumentUploadService documentUploadService) {
        this.documentUploadService = documentUploadService;
    }

    // Add request mapping for POST /uploadDocument
    @PostMapping("/uploadDocument")
    public ResponseEntity<?> uploadDocumentFTP(@RequestParam MultipartFile file) {
        try {
            // Read file contents and convert to Base64 if needed
            String documentBase64 = Base64.getEncoder().encodeToString(file.getBytes());
            String documentName = file.getOriginalFilename();

            // Extract extension if needed
            String documentExtension = "";
            if (documentName != null && documentName.contains(".")) {
                documentExtension = documentName.substring(documentName.lastIndexOf(".") + 1);
            }

            // Create a Document object
            Document document = new Document(documentBase64, documentExtension, documentName);
            //System.out.println(document);

            // Call your service for further processing
            Object result = documentUploadService.uploadDocument(document);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Add request mapping for GET /getDocumentById
    @GetMapping("/getDocumentById")
    public ResponseEntity<String> getDocument(@RequestParam String ID) {
        ResponseEntity<String> response = documentUploadService.getDocument(ID);
        return response;
    }

    @GetMapping("/documents")
    public String getDocuments(Model model) {
        List<Document> documents = documentUploadService.getAllDocumentsMetaData();
        model.addAttribute("documents", documents);
        return "index";  // Assumes your Thymeleaf template is named index.html
    }
}