package com.gym.service;

import com.gym.dto.MemberDto;
import com.gym.entity.Member;
import com.gym.repository.MemberRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Page<MemberDto> getMembers(Pageable pageable) {
        return memberRepository.findAll(pageable).map(m -> {
            MemberDto dto = new MemberDto();
            BeanUtils.copyProperties(m, dto);
            return dto;
        });
    }

    public MemberDto getMemberById(java.util.UUID id) {
        Member member = memberRepository.findById(id).orElseThrow();
        MemberDto dto = new MemberDto();
        BeanUtils.copyProperties(member, dto);
        return dto;
    }

    public MemberDto createMember(MemberDto memberDto) {
        Member member = new Member();
        BeanUtils.copyProperties(memberDto, member);
        member.setStatus("active");
        Member saved = memberRepository.save(member);
        MemberDto response = new MemberDto();
        BeanUtils.copyProperties(saved, response);
        return response;
    }
}
