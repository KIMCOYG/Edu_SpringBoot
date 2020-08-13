package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //자동으로 스프링 빈 등록
@Transactional(readOnly = true) //jpa의 모든 데이터 변경이나 로직들은 이 안에서 실행되어야함, spring 제공 트랜젝션 어노테이션을 사용 권장
@RequiredArgsConstructor //final에 있는 필드만 가지고 생성자를 만들어줌, lombok 어노테이션
public class MemberService {

//        @Autowired //인젝션
    private final MemberRepository memberRepository; //변경할 일이 없기 때문에 final 권장

    //setMemberRepository: 세터 인잭션, 스프링이 바로 주입하는 것이 아님, 장점: 테스트 코드 작성 시 직접 주입 가능, 단점: 런타임 시 누군가가 바꿀 수도 있음

    /*@Autowired //생성자 인젝션: 의존성을 명확히 알려줌, 생성자가 하나만 있는 경우에는 자동으로 @Autowired 적용
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    //회원 가입
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //실무에서는 멀티쓰레드 상황을 고려해서 멤버 네임을 유니크 제약조건으로 설정하길 권장
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
