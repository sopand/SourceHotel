package com.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.mapper.MemberMapper;
import com.hotel.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService { //UserDetailsService는 시큐리티 실행시
															//DB에 있는 값을 가져오는 역할을 한다.
	@Autowired
	// private final PasswordEncoder passwordEncoder;
	private final BCryptPasswordEncoder passwordEncoder; //멤버 회원가입시 멤버의 비밀번호를 암호화하기 위한 스프링 시큐리티,
	@Autowired
	private final MemberMapper mMapper;

	@Transactional
	public void MemberAdd(Member m) {
		m.setM_pwd(passwordEncoder.encode(m.getM_pwd()));	//request된 비밀번호의 입력값을 가져와 암호화시킨뒤 다시 저장,
		mMapper.MemberAdd(m);
	}

	public Member FindID(String m_id) {
		return mMapper.FindID(m_id);
	}

	@Override
	public UserDetails loadUserByUsername(String m_id) throws UsernameNotFoundException {
		Member m = FindID(m_id);
		if (m != null) {
			return new UserDetail(m);
		}
		return null;

	}
}
