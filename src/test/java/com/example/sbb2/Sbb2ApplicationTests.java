package com.example.sbb2;

import com.example.sbb2.Answer.Answer;
import com.example.sbb2.Answer.AnswerRepository;
import com.example.sbb2.Answer.AnswerService;
import com.example.sbb2.Question.Question;
import com.example.sbb2.Question.QuestionRepository;
import com.example.sbb2.Question.QuestionService;
import com.example.sbb2.User.SiteUser;
import com.example.sbb2.User.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class Sbb2ApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;
	@Autowired
	private AnswerService answerService;

	@Test
	void testJpa() {
		SiteUser siteUser = this.userService.getUser("user2");

		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		q1.setAuthor(siteUser);
		this.questionRepository.save(q1);  // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		q2.setAuthor(siteUser);
		this.questionRepository.save(q2);  // 두번째 질문 저장
	}

	@Test
	@DisplayName("답변 생성")
	void testAnswer() {
		SiteUser siteUser = this.userService.getUser("user2");
		Question q1 = this.questionService.getQuestion(1);
		Question q2 = this.questionService.getQuestion(2);

		Answer a1 = new Answer();
		a1.setAuthor(siteUser);
		a1.setContent("sbb는 스프링부트 기반의 게시판 프로그램입니다.");
		a1.setCreateDate(LocalDateTime.now());
		a1.setQuestion(q1);
		this.answerRepository.save(a1);

		Answer a2 = new Answer();
		a2.setAuthor(siteUser);
		a2.setContent("네, 자동으로 생성됩니다.");
		a2.setCreateDate(LocalDateTime.now());
		a2.setQuestion(q2);
		this.answerRepository.save(a2);
	}

	@Test
	@DisplayName("대량 데이터 생성")
	void testData() {
		for(int i = 3; i <= 150; i++) {
			String subject = String.format("테스트 제목입니다. [%03d]", i);
			String content = "테스트 내용입니다.";
			SiteUser siteUser = this.userService.getUser("user1");
			this.questionService.create(subject, content, siteUser);
		}
	}

	@Test
	@DisplayName("회원 생성")
	void testMember() {
		userService.create("admin", "admin@test.com", "admin");
		userService.create("user1", "user1@test.com", "user1");
		userService.create("user2", "user2@test.com", "user2");
	}
}
