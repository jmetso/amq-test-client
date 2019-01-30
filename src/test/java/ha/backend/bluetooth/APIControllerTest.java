package ha.backend.bluetooth;

import ha.backend.bluetooth.utils.ConfigUtils;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class APIControllerTest {

    @Autowired
    private MockMvc mvc;

    static {
        System.setProperty("AMQ_USERNAME", "user");
        System.setProperty("AMQ_PASSWORD", "-pas1word");
    }

    @BeforeClass
    public static void setUp() {
        System.setProperty(ConfigUtils.CONFIG_FILE_PROPERTY, "config.txt");
        ConfigUtils.init();
        System.setProperty("AMQ_USERNAME", "user");
        System.setProperty("AMQ_PASSWORD", "-pas1word");
        System.setProperty("TIME_TO_LIVE", "100");
    }

    @AfterClass
    public static void tearDown() {
        System.clearProperty(ConfigUtils.CONFIG_FILE_PROPERTY);
        System.clearProperty("AMQ_USERNAME");
        System.clearProperty("AMQ_PASSWORD");
    }

    @Ignore
    @Test
    public void sendMessage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/amq/api/v1/send/Huuhaa") .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("true")));

    }
}