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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import study.test.domain.Study;


//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
// static 일 필요가 없다.
//@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudyTest_bk {
	//	각 메소드마다 다른 인스턴스를 가지고 있고 메소드마다 의존성은 없다.
	int value = 1;
	
	@Test
	@FastTest
//	@Tag("fast")
	@Order(1)
	void create_new() {
		System.out.println(value++);
	}
	
	@Test
	@SlowTest
//	@Tag("slow")
	void create_new_two() {
		System.out.println(value++);
	}
	@DisplayName("study make")
	@RepeatedTest(value=1,name = "{displayName},{currentRepetition}/{totalRepetitions}")
	@Order(2)
	void repeatTest(RepetitionInfo info) {
		System.out.println("test" + info.getCurrentRepetition() + "/ " + 
				info.getTotalRepetitions());
	}
	@DisplayName("study make")
	@ParameterizedTest(name = "{index},{displayName} message={0}")
	@ValueSource(strings = {"weather","very","cold"})
	@EmptySource
	@NullSource
	@NullAndEmptySource
	void parameterizedTest(String message) {
		System.out.println(message);
	}
	
	@DisplayName("study make2")
	@ParameterizedTest(name = "{index},{displayName} message={0}")
	@ValueSource(ints =  {10,20,40})
	void parameterizedTest2(@ConvertWith(StudyConverter.class)Study study) {
		System.out.println(study.getLimit());
	}
	// 하나의 아규먼트만 
	static class StudyConverter extends SimpleArgumentConverter {
		@Override
		protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
			assertEquals(Study.class, targetType,"Can only convert to Study");
			return new Study(Integer.parseInt(source.toString()));
		}
		
	}
	
	@DisplayName("study make2")
	@ParameterizedTest(name = "{index},{displayName} message={0}")
	@CsvSource({"10,'java'","20,'spring'"})
	void parameterizedTest3(Integer limit, String name) {
		Study study = new Study(limit,name);
		System.out.println(study);
	}
	
	@DisplayName("study make2")
	@ParameterizedTest(name = "{index},{displayName} message={0}")
	@CsvSource({"10,'java'","20,'spring'"})
	void parameterizedTest4(ArgumentsAccessor accessor) {
		Study study = new Study(accessor.getInteger(0),accessor.getString(1));
		System.out.println(study);
	}
	
	@DisplayName("study make2")
	@ParameterizedTest(name = "{index},{displayName} message={0}")
	@CsvSource({"10,'java'","20,'spring'"})
	void parameterizedTest5(@AggregateWith(StudyAggregator.class) Study study) {
		System.out.println(study);
	}
	
	static class StudyAggregator implements ArgumentsAggregator {
		@Override
		public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
				throws ArgumentsAggregationException {
			return new Study(accessor.getInteger(0),accessor.getString(1));
		}
		
	}
//	@Test
//	@DisplayName("첫 테스트 생성")
//	@EnabledOnOs(OS.MAC)
//	@EnabledOnJre(JRE.JAVA_8)
//	@EnabledIfEnvironmentVariable(named = "TEST_ENV",matches = "local")
//	void create_new_one() {
//		
//		String test_env = System.getenv("TEST_ENV");
//		System.out.println(test_env);
//		//	ture 일때만 밑에 코드가 실행됨.
////		assumeTrue("LOCAL".equalsIgnoreCase(test_env));
//		
//		assumingThat(test_env == null, () -> {
//			System.out.println("null 이다.");
//		});
//		
//		assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
//			IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->new Study(-10));
//			assertEquals("limit은 0보다 커야 한다.", exception.getMessage());
//		});
//		// TODO	복습.
//		assertTimeout(Duration.ofSeconds(1), () -> {
//				new Study(10); 
//				Thread.sleep(1000);
//			}
//		);
//		
//		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
//				new Study(10); 
//				Thread.sleep(1000);
//			}
//		);
//		
//		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->new Study(-10));
//		assertEquals("limit은 0보다 커야 한다.", exception.getMessage());
//		
//		Study study = new Study(-10);
//		// 한번에 여러 테스트를 체크 할 수 있다.
//		assertAll(
//			() -> assertNotNull(study),
//			() -> assertEquals(StudyStatus.DRAFT, study.getStatus(),"스터디를 처음 만들면 상태값이 draft여야 한다."),
//			() -> assertTrue(study.getLimit()> 0, "스터디 최대 참석 가능 인원이 10명 이상이여야 한다. ")	
//		);
//		
//		assertNotNull(study);
//		//	기대하는 값, 실제값, 실패일때 메세지.
//		//	3번째 인자를 람다로 하면 연산을 필요할때만 한다. 
//		//	3번째 인자를 일반 스트링일 경우 무조건 연산되기 때문에 성능을 신경쓰려면 람다를 활용 
//		assertEquals(StudyStatus.DRAFT, study.getStatus(),() -> "스터디를 처음 만들면 상태값이 draft여야 한다.");
//		assertEquals(StudyStatus.DRAFT, study.getStatus(),"스터디를 처음 만들면 상태값이 draft여야 한다.");
//		assertTrue(study.getLimit()> 0, "스터디 최대 참석 가능 인원이 10명 이상이여야 한다. ");
//	}
	
	@BeforeAll
	void beforeAll() {
		System.out.println("before all");
	}
	
	@AfterAll
	void afterAll() {
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
