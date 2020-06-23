package study.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StudyTest {

	@Test
	@DisplayName("첫 테스트 생성")
	@EnabledOnOs(OS.MAC)
	@EnabledOnJre(JRE.JAVA_8)
	@EnabledIfEnvironmentVariable(named = "TEST_ENV",matches = "local")
	void create_new_one() {
		
		String test_env = System.getenv("TEST_ENV");
		System.out.println(test_env);
		//	ture 일때만 밑에 코드가 실행됨.
//		assumeTrue("LOCAL".equalsIgnoreCase(test_env));
		
		assumingThat(test_env == null, () -> {
			System.out.println("null 이다.");
		});
		
		assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
			IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->new Study(-10));
			assertEquals("limit은 0보다 커야 한다.", exception.getMessage());
		});
		// TODO	복습.
		assertTimeout(Duration.ofSeconds(1), () -> {
				new Study(10); 
				Thread.sleep(1000);
			}
		);
		
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
				new Study(10); 
				Thread.sleep(1000);
			}
		);
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->new Study(-10));
		assertEquals("limit은 0보다 커야 한다.", exception.getMessage());
		
		Study study = new Study(-10);
		// 한번에 여러 테스트를 체크 할 수 있다.
		assertAll(
			() -> assertNotNull(study),
			() -> assertEquals(StudyStatus.DRAFT, study.getStatus(),"스터디를 처음 만들면 상태값이 draft여야 한다."),
			() -> assertTrue(study.getLimit()> 0, "스터디 최대 참석 가능 인원이 10명 이상이여야 한다. ")	
		);
		
		assertNotNull(study);
		//	기대하는 값, 실제값, 실패일때 메세지.
		//	3번째 인자를 람다로 하면 연산을 필요할때만 한다. 
		//	3번째 인자를 일반 스트링일 경우 무조건 연산되기 때문에 성능을 신경쓰려면 람다를 활용 
		assertEquals(StudyStatus.DRAFT, study.getStatus(),() -> "스터디를 처음 만들면 상태값이 draft여야 한다.");
		assertEquals(StudyStatus.DRAFT, study.getStatus(),"스터디를 처음 만들면 상태값이 draft여야 한다.");
		assertTrue(study.getLimit()> 0, "스터디 최대 참석 가능 인원이 10명 이상이여야 한다. ");
	}
	
	@Test
	void create_new_two() {
		System.out.println("create 1");
	}
	
	@BeforeAll
	static void beforeAll() {
		System.out.println("before all");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("after all");
	}
	
	@BeforeEach
	void beforeEach() {
		System.out.println("before each");
	}
	
	@AfterEach
	void afterEach() {
		System.out.println("after each");
	}
}
