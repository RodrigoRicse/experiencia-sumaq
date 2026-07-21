package com.sumaq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "debug=false")
@ActiveProfiles("test")
class ExperienciaSumaqApplicationTests {

	@Test
	void contextLoads() {
	}

}
