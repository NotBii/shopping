package com.zerobase.shopping.member.repository;

import com.zerobase.shopping.member.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

  Optional<MemberEntity> findByUsername(String username);
  Optional<MemberEntity> findByMail(String mail);
  Optional<MemberEntity> findByNickname(String nickname);
  Optional<MemberEntity> findByUsernameAndMail(String username, String mail);
  void deleteMemberEntityByUsername(String username);
}
