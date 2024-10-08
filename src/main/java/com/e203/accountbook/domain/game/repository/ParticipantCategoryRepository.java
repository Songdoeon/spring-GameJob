package com.e203.accountbook.domain.game.repository;

import com.e203.accountbook.domain.game.entity.Participant;
import com.e203.accountbook.domain.game.entity.ParticipantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantCategoryRepository extends JpaRepository<ParticipantCategory, Long> {
    @Modifying
    @Query("delete from ParticipantCategory pc where pc.participant = :participant")
    void deleteByParticipant(@Param("participant") Participant participant);

    List<ParticipantCategory> findByParticipantId(Long participantId);
}
