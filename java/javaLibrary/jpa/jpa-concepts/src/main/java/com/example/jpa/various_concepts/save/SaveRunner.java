package com.example.jpa.various_concepts.save;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.example.jpa.various_concepts.save.entities.IdentityMember;
import com.example.jpa.various_concepts.save.entities.SequenceMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Component
public class SaveRunner implements ApplicationRunner {

    private final EntityManager em;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============= Save Runner Started =============");
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start("identity member save");
        for (int i = 0; i < 10000; i++) {
            final IdentityMember identityMember = IdentityMember.builder()
                .name("name" + i)
                .build();

            em.persist(identityMember);
        }
        stopWatch.stop();

        stopWatch.start("sequence member save");
        for (int i = 0; i < 10000; i++) {
            final SequenceMember sequenceMember = SequenceMember.builder()
                .name("name" + i)
                .build();

            em.persist(sequenceMember);
        }
        stopWatch.stop();

        log.info("result value is!");
        log.info(stopWatch.prettyPrint());
    }
}
