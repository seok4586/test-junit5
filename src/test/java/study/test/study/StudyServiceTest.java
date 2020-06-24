package study.test.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import study.test.domain.Member;
import study.test.domain.Study;
import study.test.member.MemberService;

// 선언을 하든 인자로 넣든 extendwith 어노테이션은 필요.
@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {

	@Mock
	MemberService memberService ;
	
	@Mock
	StudyRepository repository ;
	
	@Test
	@DisplayName("mockito 스터디.")
	void createStudyService() {
//		MemberService memberService = mock(MemberService.class);
//		StudyRepository repository = mock(StudyRepository.class);
		StudyService service = new StudyService(memberService, repository);
		assertNotNull(service);
	}
	
	@Test
	@DisplayName("mockito 스터디2.")
	void createStudyService2(@Mock MemberService memberService, @Mock StudyRepository repository ) {
		StudyService service = new StudyService(memberService, repository);
		assertNotNull(service);
	}
	
	@Test
	@DisplayName("mockito 스터디3.")
	void createStudyService3(@Mock MemberService memberService, @Mock StudyRepository repository ) {
		
		StudyService service = new StudyService(memberService, repository);
		assertNotNull(service);
		
		Member member = new Member();
		member.setId(1L);
		member.setEmail("test@test.com");
		// stubbing 
		when(memberService.findById(any())).thenReturn(Optional.of(member));
		
		Optional<Member> findById = memberService.findById(2L);
		assertEquals("test@test.com",findById.get().getEmail());
		
		doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

		assertThrows(IllegalArgumentException.class,() -> {
			memberService.validate(1L);
		});
		
		memberService.validate(2L);
		
//		Study study = new  Study(10, "java");
//		service.createNewStudy(1L, study);
	}
	
	@Test
	@DisplayName("mockito 스터디4.")
	void createStudyService4(@Mock MemberService memberService, @Mock StudyRepository repository ) {
		
		Member member = new Member();
		member.setId(1L);
		member.setEmail("test@test.com");
		
		when(memberService.findById(any()))
			.thenReturn(Optional.of(member))
			.thenThrow(new RuntimeException())
			.thenReturn(Optional.empty());
		
		Optional<Member> optional = memberService.findById(1L);
		assertEquals("test@test.com", optional.get().getEmail());
		
		//	두번째 호출하면 런타임 에러.
		assertThrows(RuntimeException.class, ()->{
			memberService.findById(1L);
		});
		
		assertEquals(Optional.empty(), memberService.findById(3L));
		
	}

}
