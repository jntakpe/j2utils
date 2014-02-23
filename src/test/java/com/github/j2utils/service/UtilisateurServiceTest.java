package com.github.j2utils.service;

import com.github.jntakpe.j2utils.domain.Utilisateur;
import com.github.jntakpe.j2utils.service.UtilisateurService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Tests des services associés à l'entité {@link com.github.jntakpe.j2utils.domain.Utilisateur}
 *
 * @author jntakpe
 */
@ActiveProfiles("dev")
@ContextConfiguration("classpath:/spring/applicationContext-test.xml")
public class UtilisateurServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UtilisateurService utilisateurService;

    @Test
    public void findByLoginTest() {
        assertThat(utilisateurService.findByLogin("ImNull")).isNull();
        Utilisateur toto = utilisateurService.findByLogin("toto");
        assertThat(toto.getLogin()).isEqualTo("toto");
        assertThat(toto.getRole().getNom()).isEqualTo("ROLE_ADMIN");
        Utilisateur titi = utilisateurService.findByLogin("titi");
        assertThat(titi.getRole().getNom()).isEqualTo("ROLE_USER");
    }
}
