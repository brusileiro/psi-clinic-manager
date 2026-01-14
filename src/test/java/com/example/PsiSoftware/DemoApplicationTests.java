import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Teste de contexto será reativado nos testes de integração")
@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
