package com.cristobalrivas.matchservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "riot.api.key=test-key",
        "riot.api.platform-url-template=http://localhost:9999/{region}",
        "player.service.url=http://localhost:9998"
})
class MatchServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
