package com.cristobalrivas.playerservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "riot.api.key=test-key",
        "riot.api.americas-url=http://localhost:9999"
})
class PlayerServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
