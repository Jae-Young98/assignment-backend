package org.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "KAFKA_GROUP_ID=test")
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
