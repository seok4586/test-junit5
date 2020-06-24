package study.test.study;

import org.springframework.data.jpa.repository.JpaRepository;

import study.test.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
