package com.example.emailVerificationPractice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class EmailVerificationPracticeApplicationTests {

	Calculator calculator = new Calculator();
	@Test
	void contextLoads() {
	}

	@Test
	void itShouldAddNumbers() {
		//given
		int first = 8;
		int second = 6;

		//when
		int sum = calculator.add(first,second);

		//then
		int expected = 14;
		assertThat(sum).isEqualTo(expected);
	}

	class Calculator{

		int add(int a, int b){
			return a + b;
		}
	}

}
