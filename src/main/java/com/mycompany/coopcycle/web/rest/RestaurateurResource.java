package com.mycompany.coopcycle.web.rest;

import com.mycompany.coopcycle.domain.Restaurateur;
import com.mycompany.coopcycle.repository.RestaurateurRepository;
import com.mycompany.coopcycle.service.RestaurateurService;
import com.mycompany.coopcycle.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.coopcycle.domain.Restaurateur}.
 */
@RestController
@RequestMapping("/api")
public class RestaurateurResource {

    private final Logger log = LoggerFactory.getLogger(RestaurateurResource.class);

    private static final String ENTITY_NAME = "restaurateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RestaurateurService restaurateurService;

    private final RestaurateurRepository restaurateurRepository;

    public RestaurateurResource(RestaurateurService restaurateurService, RestaurateurRepository restaurateurRepository) {
        this.restaurateurService = restaurateurService;
        this.restaurateurRepository = restaurateurRepository;
    }

    /**
     * {@code POST  /restaurateurs} : Create a new restaurateur.
     *
     * @param restaurateur the restaurateur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new restaurateur, or with status {@code 400 (Bad Request)} if the restaurateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/restaurateurs")
    public ResponseEntity<Restaurateur> createRestaurateur(@Valid @RequestBody Restaurateur restaurateur) throws URISyntaxException {
        log.debug("REST request to save Restaurateur : {}", restaurateur);
        if (restaurateur.getId() != null) {
            throw new BadRequestAlertException("A new restaurateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Restaurateur result = restaurateurService.save(restaurateur);
        return ResponseEntity
            .created(new URI("/api/restaurateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /restaurateurs/:id} : Updates an existing restaurateur.
     *
     * @param id the id of the restaurateur to save.
     * @param restaurateur the restaurateur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restaurateur,
     * or with status {@code 400 (Bad Request)} if the restaurateur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the restaurateur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/restaurateurs/{id}")
    public ResponseEntity<Restaurateur> updateRestaurateur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Restaurateur restaurateur
    ) throws URISyntaxException {
        log.debug("REST request to update Restaurateur : {}, {}", id, restaurateur);
        if (restaurateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, restaurateur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!restaurateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Restaurateur result = restaurateurService.update(restaurateur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restaurateur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /restaurateurs/:id} : Partial updates given fields of an existing restaurateur, field will ignore if it is null
     *
     * @param id the id of the restaurateur to save.
     * @param restaurateur the restaurateur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restaurateur,
     * or with status {@code 400 (Bad Request)} if the restaurateur is not valid,
     * or with status {@code 404 (Not Found)} if the restaurateur is not found,
     * or with status {@code 500 (Internal Server Error)} if the restaurateur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/restaurateurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Restaurateur> partialUpdateRestaurateur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Restaurateur restaurateur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Restaurateur partially : {}, {}", id, restaurateur);
        if (restaurateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, restaurateur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!restaurateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Restaurateur> result = restaurateurService.partialUpdate(restaurateur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restaurateur.getId().toString())
        );
    }

    /**
     * {@code GET  /restaurateurs} : get all the restaurateurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of restaurateurs in body.
     */
    @GetMapping("/restaurateurs")
    public ResponseEntity<List<Restaurateur>> getAllRestaurateurs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Restaurateurs");
        Page<Restaurateur> page = restaurateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /restaurateurs/:id} : get the "id" restaurateur.
     *
     * @param id the id of the restaurateur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the restaurateur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/restaurateurs/{id}")
    public ResponseEntity<Restaurateur> getRestaurateur(@PathVariable Long id) {
        log.debug("REST request to get Restaurateur : {}", id);
        Optional<Restaurateur> restaurateur = restaurateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restaurateur);
    }

    /**
     * {@code DELETE  /restaurateurs/:id} : delete the "id" restaurateur.
     *
     * @param id the id of the restaurateur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/restaurateurs/{id}")
    public ResponseEntity<Void> deleteRestaurateur(@PathVariable Long id) {
        log.debug("REST request to delete Restaurateur : {}", id);
        restaurateurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
