package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // 자동으로 스프링 빈으로 관리
//@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext //jpa가 제공하는 표준 어노테이션, spring-boot-data-jpa가 제공하여서 @Autowired로 변경 가능
    private EntityManager em;

    public void save(Member member) {
        em.persist(member); //jpa가 저장
   }

    public Member findOne(Long id) {
         return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) //jpql
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
