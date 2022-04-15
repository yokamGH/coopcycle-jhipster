package com.mycompany.coopcycle.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.coopcycle.IntegrationTest;
import com.mycompany.coopcycle.domain.Restaurateur;
import com.mycompany.coopcycle.repository.RestaurateurRepository;
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
 * Integration tests for the {@link RestaurateurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RestaurateurResourceIT {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "GVm0G@N.ec{";
    private static final String UPDATED_EMAIL = "[=hSr@lfoBr\".zK";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_COMMISSIONS = 1F;
    private static final Float UPDATED_COMMISSIONS = 2F;

    private static final Boolean DEFAULT_EST_DG = false;
    private static final Boolean UPDATED_EST_DG = true;

    private static final Boolean DEFAULT_EST_MENBRE_CA = false;
    private static final Boolean UPDATED_EST_MENBRE_CA = true;

    private static final String ENTITY_API_URL = "/api/restaurateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RestaurateurRepository restaurateurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRestaurateurMockMvc;

    private Restaurateur restaurateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restaurateur createEntity(EntityManager em) {
        Restaurateur restaurateur = new Restaurateur()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .commissions(DEFAULT_COMMISSIONS)
            .estDG(DEFAULT_EST_DG)
            .estMenbreCA(DEFAULT_EST_MENBRE_CA);
        return restaurateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restaurateur createUpdatedEntity(EntityManager em) {
        Restaurateur restaurateur = new Restaurateur()
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .commissions(UPDATED_COMMISSIONS)
            .estDG(UPDATED_EST_DG)
            .estMenbreCA(UPDATED_EST_MENBRE_CA);
        return restaurateur;
    }

    @BeforeEach
    public void initTest() {
        restaurateur = createEntity(em);
    }

    @Test
    @Transactional
    void createRestaurateur() throws Exception {
        int databaseSizeBeforeCreate = restaurateurRepository.findAll().size();
        // Create the Restaurateur
        restRestaurateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurateur)))
            .andExpect(status().isCreated());

        // Validate the Restaurateur in the database
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeCreate + 1);
        Restaurateur testRestaurateur = restaurateurList.get(restaurateurList.size() - 1);
        assertThat(testRestaurateur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testRestaurateur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRestaurateur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRestaurateur.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testRestaurateur.getCommissions()).isEqualTo(DEFAULT_COMMISSIONS);
        assertThat(testRestaurateur.getEstDG()).isEqualTo(DEFAULT_EST_DG);
        assertThat(testRestaurateur.getEstMenbreCA()).isEqualTo(DEFAULT_EST_MENBRE_CA);
    }

    @Test
    @Transactional
    void createRestaurateurWithExistingId() throws Exception {
        // Create the Restaurateur with an existing ID
        restaurateur.setId(1L);

        int databaseSizeBeforeCreate = restaurateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestaurateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurateur)))
            .andExpect(status().isBadRequest());

        // Validate the Restaurateur in the database
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurateurRepository.findAll().size();
        // set the field null
        restaurateur.setPrenom(null);

        // Create the Restaurateur, which fails.

        restRestaurateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurateur)))
            .andExpect(status().isBadRequest());

        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurateurRepository.findAll().size();
        // set the field null
        restaurateur.setEmail(null);

        // Create the Restaurateur, which fails.

        restRestaurateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurateur)))
            .andExpect(status().isBadRequest());

        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurateurRepository.findAll().size();
        // set the field null
        restaurateur.setPhoneNumber(null);

        // Create the Restaurateur, which fails.

        restRestaurateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurateur)))
            .andExpect(status().isBadRequest());

        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRestaurateurs() throws Exception {
        // Initialize the database
        restaurateurRepository.saveAndFlush(restaurateur);

        // Get all the restaurateurList
        restRestaurateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].commissions").value(hasItem(DEFAULT_COMMISSIONS.doubleValue())))
            .andExpect(jsonPath("$.[*].estDG").value(hasItem(DEFAULT_EST_DG.booleanValue())))
            .andExpect(jsonPath("$.[*].estMenbreCA").value(hasItem(DEFAULT_EST_MENBRE_CA.booleanValue())));
    }

    @Test
    @Transactional
    void getRestaurateur() throws Exception {
        // Initialize the database
        restaurateurRepository.saveAndFlush(restaurateur);

        // Get the restaurateur
        restRestaurateurMockMvc
            .perform(get(ENTITY_API_URL_ID, restaurateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(restaurateur.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.commissions").value(DEFAULT_COMMISSIONS.doubleValue()))
            .andExpect(jsonPath("$.estDG").value(DEFAULT_EST_DG.booleanValue()))
            .andExpect(jsonPath("$.estMenbreCA").value(DEFAULT_EST_MENBRE_CA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingRestaurateur() throws Exception {
        // Get the restaurateur
        restRestaurateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRestaurateur() throws Exception {
        // Initialize the database
        restaurateurRepository.saveAndFlush(restaurateur);

        int databaseSizeBeforeUpdate = restaurateurRepository.findAll().size();

        // Update the restaurateur
        Restaurateur updatedRestaurateur = restaurateurRepository.findById(restaurateur.getId()).get();
        // Disconnect from session so that the updates on updatedRestaurateur are not directly saved in db
        em.detach(updatedRestaurateur);
        updatedRestaurateur
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .commissions(UPDATED_COMMISSIONS)
            .estDG(UPDATED_EST_DG)
            .estMenbreCA(UPDATED_EST_MENBRE_CA);

        restRestaurateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRestaurateur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRestaurateur))
            )
            .andExpect(status().isOk());

        // Validate the Restaurateur in the database
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeUpdate);
        Restaurateur testRestaurateur = restaurateurList.get(restaurateurList.size() - 1);
        assertThat(testRestaurateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testRestaurateur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRestaurateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRestaurateur.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testRestaurateur.getCommissions()).isEqualTo(UPDATED_COMMISSIONS);
        assertThat(testRestaurateur.getEstDG()).isEqualTo(UPDATED_EST_DG);
        assertThat(testRestaurateur.getEstMenbreCA()).isEqualTo(UPDATED_EST_MENBRE_CA);
    }

    @Test
    @Transactional
    void putNonExistingRestaurateur() throws Exception {
        int databaseSizeBeforeUpdate = restaurateurRepository.findAll().size();
        restaurateur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, restaurateur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restaurateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurateur in the database
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRestaurateur() throws Exception {
        int databaseSizeBeforeUpdate = restaurateurRepository.findAll().size();
        restaurateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restaurateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurateur in the database
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRestaurateur() throws Exception {
        int databaseSizeBeforeUpdate = restaurateurRepository.findAll().size();
        restaurateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurateurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurateur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Restaurateur in the database
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRestaurateurWithPatch() throws Exception {
        // Initialize the database
        restaurateurRepository.saveAndFlush(restaurateur);

        int databaseSizeBeforeUpdate = restaurateurRepository.findAll().size();

        // Update the restaurateur using partial update
        Restaurateur partialUpdatedRestaurateur = new Restaurateur();
        partialUpdatedRestaurateur.setId(restaurateur.getId());

        partialUpdatedRestaurateur
            .prenom(UPDATED_PRENOM)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .estDG(UPDATED_EST_DG)
            .estMenbreCA(UPDATED_EST_MENBRE_CA);

        restRestaurateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestaurateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestaurateur))
            )
            .andExpect(status().isOk());

        // Validate the Restaurateur in the database
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeUpdate);
        Restaurateur testRestaurateur = restaurateurList.get(restaurateurList.size() - 1);
        assertThat(testRestaurateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testRestaurateur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRestaurateur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRestaurateur.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testRestaurateur.getCommissions()).isEqualTo(DEFAULT_COMMISSIONS);
        assertThat(testRestaurateur.getEstDG()).isEqualTo(UPDATED_EST_DG);
        assertThat(testRestaurateur.getEstMenbreCA()).isEqualTo(UPDATED_EST_MENBRE_CA);
    }

    @Test
    @Transactional
    void fullUpdateRestaurateurWithPatch() throws Exception {
        // Initialize the database
        restaurateurRepository.saveAndFlush(restaurateur);

        int databaseSizeBeforeUpdate = restaurateurRepository.findAll().size();

        // Update the restaurateur using partial update
        Restaurateur partialUpdatedRestaurateur = new Restaurateur();
        partialUpdatedRestaurateur.setId(restaurateur.getId());

        partialUpdatedRestaurateur
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .commissions(UPDATED_COMMISSIONS)
            .estDG(UPDATED_EST_DG)
            .estMenbreCA(UPDATED_EST_MENBRE_CA);

        restRestaurateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestaurateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestaurateur))
            )
            .andExpect(status().isOk());

        // Validate the Restaurateur in the database
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeUpdate);
        Restaurateur testRestaurateur = restaurateurList.get(restaurateurList.size() - 1);
        assertThat(testRestaurateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testRestaurateur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRestaurateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRestaurateur.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testRestaurateur.getCommissions()).isEqualTo(UPDATED_COMMISSIONS);
        assertThat(testRestaurateur.getEstDG()).isEqualTo(UPDATED_EST_DG);
        assertThat(testRestaurateur.getEstMenbreCA()).isEqualTo(UPDATED_EST_MENBRE_CA);
    }

    @Test
    @Transactional
    void patchNonExistingRestaurateur() throws Exception {
        int databaseSizeBeforeUpdate = restaurateurRepository.findAll().size();
        restaurateur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, restaurateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restaurateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurateur in the database
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRestaurateur() throws Exception {
        int databaseSizeBeforeUpdate = restaurateurRepository.findAll().size();
        restaurateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restaurateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurateur in the database
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRestaurateur() throws Exception {
        int databaseSizeBeforeUpdate = restaurateurRepository.findAll().size();
        restaurateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurateurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(restaurateur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Restaurateur in the database
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRestaurateur() throws Exception {
        // Initialize the database
        restaurateurRepository.saveAndFlush(restaurateur);

        int databaseSizeBeforeDelete = restaurateurRepository.findAll().size();

        // Delete the restaurateur
        restRestaurateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, restaurateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Restaurateur> restaurateurList = restaurateurRepository.findAll();
        assertThat(restaurateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
