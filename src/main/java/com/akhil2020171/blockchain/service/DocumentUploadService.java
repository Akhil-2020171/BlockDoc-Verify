package com.akhil2020171.blockchain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.akhil2020171.blockchain.Helper.HelperMethods;
import com.akhil2020171.blockchain.model.Block;
import com.akhil2020171.blockchain.model.Document;
import com.akhil2020171.blockchain.repository.DocumentUploadRepository;

import jakarta.transaction.Transactional;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class DocumentUploadService {
    /*****************************************************************************************************/
    // Get the FTP server address, port, username, and password from
    // application.properties
    @Value("${ftp.server.host}")
    private String ftpServerAddress;

    @Value("${ftp.server.port}")
    private int ftpServerPort;

    @Value("${ftp.server.username}")
    private String ftpServerUsername;

    @Value("${ftp.server.password}")
    private String ftpServerPassword;

    @Value("${ftp.server.directory}")
    private String ftpServerDirectory;
    /*****************************************************************************************************/

    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private DocumentUploadRepository documentUploadRepository;

    // Method to upload a document to the FTP server
    @Transactional(rollbackOn = Exception.class)
    public String uploadDocument(Document document) {
        FTPSClient ftpClient = new FTPSClient(false); // Use FTPSClient if using FTPS, or FTPClient otherwise
        try {
            // Upload the document to the Database. If successful, upload to the FTP server
            documentUploadRepository.uploadDocument(document.getDocument_base64(), document.getDocumentExtension(), document.getDocumentName());
            System.out.println("Document uploaded to database: " + document);

            System.out.println("Connecting to FTP server...");
            System.out.println("FTP Server: " + ftpServerAddress + ":" + ftpServerPort);
            System.out.println("FTP Username: " + ftpServerUsername);
            System.out.println("FTP Password: " + ftpServerPassword);
            System.out.println("FTP Directory: " + ftpServerDirectory);

            ftpClient.connect(ftpServerAddress, ftpServerPort);
            ftpClient.login(ftpServerUsername, ftpServerPassword);
            
            // FTPS specific commands, if applicable:
            ftpClient.execPBSZ(0);
            ftpClient.execPROT("P");
            
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
            // Ensure the target directory exists and change to it
            boolean changedDir = ftpClient.changeWorkingDirectory(ftpServerDirectory);
            if (!changedDir) {
                System.err.println("Failed to change directory to: " + ftpServerDirectory);
                return "Upload failed: unable to change working directory.";
            }
        
            // Decode the Base64 file content if necessary
            byte[] fileBytes = java.util.Base64.getDecoder().decode(document.getDocument_base64());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);

            if(document.getDocumentId() == null) {
                document.setDocumentId(HelperMethods.generateDocumentId(document.getDocumentName(), document.getDocumentExtension()));
                System.out.println("Document ID: " + document.getDocumentId());
            }
        
            String remoteFileName = document.getDocumentId() + "_" + document.getDocumentName() + "." + document.getDocumentExtension();
            boolean done = ftpClient.storeFile(remoteFileName, inputStream);
            
            // Debug: Print FTP server reply
            int replyCode = ftpClient.getReplyCode();
            String replyString = ftpClient.getReplyString();
            System.err.println("FTP Reply Code: " + replyCode);
            System.err.println("FTP Reply String: " + replyString);
        
            inputStream.close();
        
            if (done) {
                try {
                    String docID = String.valueOf(document.getDocumentId());
                    Block newBlock = blockchainService.addBlock(docID, document.getDocument_base64(), "ftpService");
                    return "Upload successful. Block added: " + newBlock;
                } catch (Exception blockchainException) {
                    System.err.println("Blockchain error: " + blockchainException.getMessage());
                    return "Upload successful, but blockchain registration failed: " + blockchainException.getMessage();
                }
            } else {
                return "Upload failed";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error during FTP upload: " + ex.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during saving into Database: " + e.getMessage();
        }
        finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<String> getDocument(String iD) {
        try {
            String documentBase64 = documentUploadRepository.getDocument(iD);
            return ResponseEntity.ok(documentBase64);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    public List<Document> getAllDocumentsMetaData() {
        return documentUploadRepository.getAllDocumentsMetaData();
    }
}
