package com.mycompany.coopcycle.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.coopcycle.IntegrationTest;
import com.mycompany.coopcycle.domain.Livreur;
import com.mycompany.coopcycle.repository.LivreurRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LivreurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LivreurResourceIT {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "D'*~@|`o.o^jQD";
    private static final String UPDATED_EMAIL = "|@s=p.hs=N$u";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_COMMISSIONS = 0F;
    private static final Float UPDATED_COMMISSIONS = 1F;

    private static final Float DEFAULT_NB_ETOILES = 0F;
    private static final Float UPDATED_NB_ETOILES = 1F;

    private static final Boolean DEFAULT_EST_DG = false;
    private static final Boolean UPDATED_EST_DG = true;

    private static final Boolean DEFAULT_EST_MENBRE_CA = false;
    private static final Boolean UPDATED_EST_MENBRE_CA = true;

    private static final String ENTITY_API_URL = "/api/livreurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LivreurRepository livreurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLivreurMockMvc;

    private Livreur livreur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livreur createEntity(EntityManager em) {
        Livreur livreur = new Livreur()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .commissions(DEFAULT_COMMISSIONS)
            .nbEtoiles(DEFAULT_NB_ETOILES)
            .estDG(DEFAULT_EST_DG)
            .estMenbreCA(DEFAULT_EST_MENBRE_CA);
        return livreur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livreur createUpdatedEntity(EntityManager em) {
        Livreur livreur = new Livreur()
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .commissions(UPDATED_COMMISSIONS)
            .nbEtoiles(UPDATED_NB_ETOILES)
            .estDG(UPDATED_EST_DG)
            .estMenbreCA(UPDATED_EST_MENBRE_CA);
        return livreur;
    }

    @BeforeEach
    public void initTest() {
        livreur = createEntity(em);
    }

    @Test
    @Transactional
    void createLivreur() throws Exception {
        int databaseSizeBeforeCreate = livreurRepository.findAll().size();
        // Create the Livreur
        restLivreurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livreur)))
            .andExpect(status().isCreated());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeCreate + 1);
        Livreur testLivreur = livreurList.get(livreurList.size() - 1);
        assertThat(testLivreur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testLivreur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testLivreur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLivreur.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testLivreur.getCommissions()).isEqualTo(DEFAULT_COMMISSIONS);
        assertThat(testLivreur.getNbEtoiles()).isEqualTo(DEFAULT_NB_ETOILES);
        assertThat(testLivreur.getEstDG()).isEqualTo(DEFAULT_EST_DG);
        assertThat(testLivreur.getEstMenbreCA()).isEqualTo(DEFAULT_EST_MENBRE_CA);
    }

    @Test
    @Transactional
    void createLivreurWithExistingId() throws Exception {
        // Create the Livreur with an existing ID
        livreur.setId(1L);

        int databaseSizeBeforeCreate = livreurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivreurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livreur)))
            .andExpect(status().isBadRequest());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = livreurRepository.findAll().size();
        // set the field null
        livreur.setPrenom(null);

        // Create the Livreur, which fails.

        restLivreurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livreur)))
            .andExpect(status().isBadRequest());

        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = livreurRepository.findAll().size();
        // set the field null
        livreur.setEmail(null);

        // Create the Livreur, which fails.

        restLivreurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livreur)))
            .andExpect(status().isBadRequest());

        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = livreurRepository.findAll().size();
        // set the field null
        livreur.setPhoneNumber(null);

        // Create the Livreur, which fails.

        restLivreurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livreur)))
            .andExpect(status().isBadRequest());

        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLivreurs() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get all the livreurList
        restLivreurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livreur.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].commissions").value(hasItem(DEFAULT_COMMISSIONS.doubleValue())))
            .andExpect(jsonPath("$.[*].nbEtoiles").value(hasItem(DEFAULT_NB_ETOILES.doubleValue())))
            .andExpect(jsonPath("$.[*].estDG").value(hasItem(DEFAULT_EST_DG.booleanValue())))
            .andExpect(jsonPath("$.[*].estMenbreCA").value(hasItem(DEFAULT_EST_MENBRE_CA.booleanValue())));
    }

    @Test
    @Transactional
    void getLivreur() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        // Get the livreur
        restLivreurMockMvc
            .perform(get(ENTITY_API_URL_ID, livreur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livreur.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.commissions").value(DEFAULT_COMMISSIONS.doubleValue()))
            .andExpect(jsonPath("$.nbEtoiles").value(DEFAULT_NB_ETOILES.doubleValue()))
            .andExpect(jsonPath("$.estDG").value(DEFAULT_EST_DG.booleanValue()))
            .andExpect(jsonPath("$.estMenbreCA").value(DEFAULT_EST_MENBRE_CA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingLivreur() throws Exception {
        // Get the livreur
        restLivreurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLivreur() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        int databaseSizeBeforeUpdate = livreurRepository.findAll().size();

        // Update the livreur
        Livreur updatedLivreur = livreurRepository.findById(livreur.getId()).get();
        // Disconnect from session so that the updates on updatedLivreur are not directly saved in db
        em.detach(updatedLivreur);
        updatedLivreur
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .commissions(UPDATED_COMMISSIONS)
            .nbEtoiles(UPDATED_NB_ETOILES)
            .estDG(UPDATED_EST_DG)
            .estMenbreCA(UPDATED_EST_MENBRE_CA);

        restLivreurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLivreur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLivreur))
            )
            .andExpect(status().isOk());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeUpdate);
        Livreur testLivreur = livreurList.get(livreurList.size() - 1);
        assertThat(testLivreur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testLivreur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLivreur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLivreur.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testLivreur.getCommissions()).isEqualTo(UPDATED_COMMISSIONS);
        assertThat(testLivreur.getNbEtoiles()).isEqualTo(UPDATED_NB_ETOILES);
        assertThat(testLivreur.getEstDG()).isEqualTo(UPDATED_EST_DG);
        assertThat(testLivreur.getEstMenbreCA()).isEqualTo(UPDATED_EST_MENBRE_CA);
    }

    @Test
    @Transactional
    void putNonExistingLivreur() throws Exception {
        int databaseSizeBeforeUpdate = livreurRepository.findAll().size();
        livreur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivreurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, livreur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(livreur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLivreur() throws Exception {
        int databaseSizeBeforeUpdate = livreurRepository.findAll().size();
        livreur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivreurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(livreur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLivreur() throws Exception {
        int databaseSizeBeforeUpdate = livreurRepository.findAll().size();
        livreur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivreurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livreur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLivreurWithPatch() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        int databaseSizeBeforeUpdate = livreurRepository.findAll().size();

        // Update the livreur using partial update
        Livreur partialUpdatedLivreur = new Livreur();
        partialUpdatedLivreur.setId(livreur.getId());

        partialUpdatedLivreur.prenom(UPDATED_PRENOM).nom(UPDATED_NOM).commissions(UPDATED_COMMISSIONS).nbEtoiles(UPDATED_NB_ETOILES);

        restLivreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLivreur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLivreur))
            )
            .andExpect(status().isOk());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeUpdate);
        Livreur testLivreur = livreurList.get(livreurList.size() - 1);
        assertThat(testLivreur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testLivreur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLivreur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLivreur.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testLivreur.getCommissions()).isEqualTo(UPDATED_COMMISSIONS);
        assertThat(testLivreur.getNbEtoiles()).isEqualTo(UPDATED_NB_ETOILES);
        assertThat(testLivreur.getEstDG()).isEqualTo(DEFAULT_EST_DG);
        assertThat(testLivreur.getEstMenbreCA()).isEqualTo(DEFAULT_EST_MENBRE_CA);
    }

    @Test
    @Transactional
    void fullUpdateLivreurWithPatch() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        int databaseSizeBeforeUpdate = livreurRepository.findAll().size();

        // Update the livreur using partial update
        Livreur partialUpdatedLivreur = new Livreur();
        partialUpdatedLivreur.setId(livreur.getId());

        partialUpdatedLivreur
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .commissions(UPDATED_COMMISSIONS)
            .nbEtoiles(UPDATED_NB_ETOILES)
            .estDG(UPDATED_EST_DG)
            .estMenbreCA(UPDATED_EST_MENBRE_CA);

        restLivreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLivreur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLivreur))
            )
            .andExpect(status().isOk());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeUpdate);
        Livreur testLivreur = livreurList.get(livreurList.size() - 1);
        assertThat(testLivreur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testLivreur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLivreur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLivreur.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testLivreur.getCommissions()).isEqualTo(UPDATED_COMMISSIONS);
        assertThat(testLivreur.getNbEtoiles()).isEqualTo(UPDATED_NB_ETOILES);
        assertThat(testLivreur.getEstDG()).isEqualTo(UPDATED_EST_DG);
        assertThat(testLivreur.getEstMenbreCA()).isEqualTo(UPDATED_EST_MENBRE_CA);
    }

    @Test
    @Transactional
    void patchNonExistingLivreur() throws Exception {
        int databaseSizeBeforeUpdate = livreurRepository.findAll().size();
        livreur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, livreur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(livreur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLivreur() throws Exception {
        int databaseSizeBeforeUpdate = livreurRepository.findAll().size();
        livreur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(livreur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLivreur() throws Exception {
        int databaseSizeBeforeUpdate = livreurRepository.findAll().size();
        livreur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivreurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(livreur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Livreur in the database
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLivreur() throws Exception {
        // Initialize the database
        livreurRepository.saveAndFlush(livreur);

        int databaseSizeBeforeDelete = livreurRepository.findAll().size();

        // Delete the livreur
        restLivreurMockMvc
            .perform(delete(ENTITY_API_URL_ID, livreur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Livreur> livreurList = livreurRepository.findAll();
        assertThat(livreurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
