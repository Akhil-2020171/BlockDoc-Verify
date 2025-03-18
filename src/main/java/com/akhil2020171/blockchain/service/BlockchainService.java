package com.akhil2020171.blockchain.service;

import com.akhil2020171.blockchain.Helper.HelperMethods;
import com.akhil2020171.blockchain.model.Block;
import com.akhil2020171.blockchain.repository.BlockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockchainService {

    private final List<Block> blockchain;
    private final BlockRepository blockRepository;

    public BlockchainService(List<Block> blockchain, BlockRepository blockRepository) {
        this.blockchain = blockchain;
        this.blockRepository = blockRepository;
    }

    public synchronized Block addBlock(String documentId, String documentContent, String createdBy) throws Exception {
        try {
            // Compute hash of the document content.
            String documentHash = HelperMethods.computeSHA256(documentContent);
            // Get previous block hash from the last block in the blockchain.
            String previousHash = blockchain.get(blockchain.size() - 1).getDocumentHash();
            String timestamp = String.valueOf(System.currentTimeMillis());
            
            Block newBlock = new Block(documentId, documentHash, previousHash, timestamp, createdBy);
            blockchain.add(newBlock);
            blockRepository.save(newBlock); // persist the new block
            
            System.out.println("New block added: " + newBlock);
            return newBlock;
        } catch (Exception e) {
            System.err.println("Error adding block to blockchain: " + e.getMessage());
            throw new Exception("Blockchain block creation failed", e);
        }
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }
}
