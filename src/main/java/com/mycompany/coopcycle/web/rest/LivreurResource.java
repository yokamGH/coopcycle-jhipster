package com.mycompany.coopcycle.web.rest;

import com.mycompany.coopcycle.domain.Livreur;
import com.mycompany.coopcycle.repository.LivreurRepository;
import com.mycompany.coopcycle.service.LivreurService;
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
 * REST controller for managing {@link com.mycompany.coopcycle.domain.Livreur}.
 */
@RestController
@RequestMapping("/api")
public class LivreurResource {

    private final Logger log = LoggerFactory.getLogger(LivreurResource.class);

    private static final String ENTITY_NAME = "livreur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LivreurService livreurService;

    private final LivreurRepository livreurRepository;

    public LivreurResource(LivreurService livreurService, LivreurRepository livreurRepository) {
        this.livreurService = livreurService;
        this.livreurRepository = livreurRepository;
    }

    /**
     * {@code POST  /livreurs} : Create a new livreur.
     *
     * @param livreur the livreur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livreur, or with status {@code 400 (Bad Request)} if the livreur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/livreurs")
    public ResponseEntity<Livreur> createLivreur(@Valid @RequestBody Livreur livreur) throws URISyntaxException {
        log.debug("REST request to save Livreur : {}", livreur);
        if (livreur.getId() != null) {
            throw new BadRequestAlertException("A new livreur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Livreur result = livreurService.save(livreur);
        return ResponseEntity
            .created(new URI("/api/livreurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /livreurs/:id} : Updates an existing livreur.
     *
     * @param id the id of the livreur to save.
     * @param livreur the livreur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livreur,
     * or with status {@code 400 (Bad Request)} if the livreur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livreur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/livreurs/{id}")
    public ResponseEntity<Livreur> updateLivreur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Livreur livreur
    ) throws URISyntaxException {
        log.debug("REST request to update Livreur : {}, {}", id, livreur);
        if (livreur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, livreur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!livreurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Livreur result = livreurService.update(livreur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livreur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /livreurs/:id} : Partial updates given fields of an existing livreur, field will ignore if it is null
     *
     * @param id the id of the livreur to save.
     * @param livreur the livreur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livreur,
     * or with status {@code 400 (Bad Request)} if the livreur is not valid,
     * or with status {@code 404 (Not Found)} if the livreur is not found,
     * or with status {@code 500 (Internal Server Error)} if the livreur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/livreurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Livreur> partialUpdateLivreur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Livreur livreur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Livreur partially : {}, {}", id, livreur);
        if (livreur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, livreur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!livreurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Livreur> result = livreurService.partialUpdate(livreur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livreur.getId().toString())
        );
    }

    /**
     * {@code GET  /livreurs} : get all the livreurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livreurs in body.
     */
    @GetMapping("/livreurs")
    public ResponseEntity<List<Livreur>> getAllLivreurs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Livreurs");
        Page<Livreur> page = livreurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /livreurs/:id} : get the "id" livreur.
     *
     * @param id the id of the livreur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livreur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/livreurs/{id}")
    public ResponseEntity<Livreur> getLivreur(@PathVariable Long id) {
        log.debug("REST request to get Livreur : {}", id);
        Optional<Livreur> livreur = livreurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livreur);
    }

    /**
     * {@code DELETE  /livreurs/:id} : delete the "id" livreur.
     *
     * @param id the id of the livreur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/livreurs/{id}")
    public ResponseEntity<Void> deleteLivreur(@PathVariable Long id) {
        log.debug("REST request to delete Livreur : {}", id);
        livreurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
