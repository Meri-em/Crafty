package com.crafty.repository;

import com.crafty.entity.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest {

	private static final String MEMBER_ID_FIRST = "MEMBER_ID_FIRST";
	private static final String MEMBER_ID_SECOND = "MEMBER_ID_SECOND";
	private static final String MEMBER_ID_THIRD = "MEMBER_ID_THIRD";

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void membersPersistedTest() {
		// given
		Member memberFirst = new Member();
		memberFirst.setId(MEMBER_ID_FIRST);
		Member memberSecond = new Member();
		memberSecond.setId(MEMBER_ID_SECOND);
		memberRepository.saveAll(Arrays.asList(memberFirst, memberSecond));

		// when
		Optional<Member> member = memberRepository.findById(MEMBER_ID_FIRST);
		Optional<Member> anotherMember = memberRepository.findById(MEMBER_ID_THIRD);

		// then
		assertTrue(member.isPresent());
		assertEquals(MEMBER_ID_FIRST, member.get().getId());
		assertFalse(anotherMember.isPresent());
	}

}
