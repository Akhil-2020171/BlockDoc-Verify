package com.akhil2020171.blockchain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlockchainApplicationTests {
	private final DocumentUploadTests documentUploadTests = new DocumentUploadTests();

	@Test
	void contextLoads() { // Test case for checking if the document is uploaded successfully
		documentUploadTests.testUploadDocument();
	}

}
