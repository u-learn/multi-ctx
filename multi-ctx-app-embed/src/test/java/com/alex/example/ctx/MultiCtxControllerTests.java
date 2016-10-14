package com.alex.example.ctx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.alex.demo.ctx.parent.ParentCtxConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ParentCtxConfig.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class MultiCtxControllerTests {

    @SuppressWarnings("unchecked")
    @Test
    public void testChild()
            throws Exception {

        Map<String, String> response = new RestTemplate().getForObject("http://localhost:8082/child", Map.class);

        assertNull(response.get("parentBean"));
        assertEquals("child_bean", response.get("childBean"));
        assertEquals("null", response.get("parentProperty"));
        assertEquals("prop_child", response.get("childProperty"));
    }

	@SuppressWarnings("unchecked")
	@Test
	public void testParent() throws Exception {

		Map<String, String> response = new RestTemplate().getForObject("http://localhost:8080/parent", Map.class);

		assertEquals("parent_bean", response.get("parentBean"));
		assertNull(response.get("childBean"));
		assertEquals("common_prop", response.get("parentProperty"));
		assertEquals("null", response.get("childProperty"));
	}
}