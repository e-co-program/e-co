package jp.co.e_co;

import jp.co.e_co.app.ECoApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ECoApplication.class)
@WebAppConfiguration
public class ECoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
