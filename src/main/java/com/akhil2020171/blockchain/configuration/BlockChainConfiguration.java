package com.akhil2020171.blockchain.configuration;

import com.akhil2020171.blockchain.model.Block;
import com.akhil2020171.blockchain.repository.BlockRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class BlockChainConfiguration {

    private final BlockRepository blockRepository;

    public BlockChainConfiguration(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Bean
    public List<Block> blockchain() {
        List<Block> blockchain = blockRepository.findAll();
        if (blockchain.isEmpty()) {
            // Create genesis block if none exist
            Block genesisBlock = new Block("0", "0", "0", String.valueOf(System.currentTimeMillis()), "system");
            blockchain.add(genesisBlock);
            blockRepository.save(genesisBlock);
        }
        return blockchain;
    }
}
