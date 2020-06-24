package study.test.member;

import java.util.Optional;

import study.test.domain.Member;
import study.test.domain.Study;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
