package com.akhil2020171.blockchain.repository;

import com.akhil2020171.blockchain.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    // Optionally add custom queries if needed
}