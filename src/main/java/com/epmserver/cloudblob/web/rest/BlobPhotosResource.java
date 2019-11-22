package com.epmserver.cloudblob.web.rest;

import com.epmserver.cloudblob.service.BlobPhotosService;
import com.epmserver.cloudblob.web.rest.errors.BadRequestAlertException;
import com.epmserver.cloudblob.service.dto.BlobPhotosDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.epmserver.cloudblob.domain.BlobPhotos}.
 */
@RestController
@RequestMapping("/api")
public class BlobPhotosResource {

    private final Logger log = LoggerFactory.getLogger(BlobPhotosResource.class);

    private static final String ENTITY_NAME = "epmcloudblobBlobPhotos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlobPhotosService blobPhotosService;

    public BlobPhotosResource(BlobPhotosService blobPhotosService) {
        this.blobPhotosService = blobPhotosService;
    }

    /**
     * {@code POST  /blob-photos} : Create a new blobPhotos.
     *
     * @param blobPhotosDTO the blobPhotosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blobPhotosDTO, or with status {@code 400 (Bad Request)} if the blobPhotos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blob-photos")
    public ResponseEntity<BlobPhotosDTO> createBlobPhotos(@RequestBody BlobPhotosDTO blobPhotosDTO) throws URISyntaxException {
        log.debug("REST request to save BlobPhotos : {}", blobPhotosDTO);
        if (blobPhotosDTO.getId() != null) {
            throw new BadRequestAlertException("A new blobPhotos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlobPhotosDTO result = blobPhotosService.save(blobPhotosDTO);
        return ResponseEntity.created(new URI("/api/blob-photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blob-photos} : Updates an existing blobPhotos.
     *
     * @param blobPhotosDTO the blobPhotosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blobPhotosDTO,
     * or with status {@code 400 (Bad Request)} if the blobPhotosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blobPhotosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blob-photos")
    public ResponseEntity<BlobPhotosDTO> updateBlobPhotos(@RequestBody BlobPhotosDTO blobPhotosDTO) throws URISyntaxException {
        log.debug("REST request to update BlobPhotos : {}", blobPhotosDTO);
        if (blobPhotosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlobPhotosDTO result = blobPhotosService.save(blobPhotosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blobPhotosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blob-photos} : get all the blobPhotos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blobPhotos in body.
     */
    @GetMapping("/blob-photos")
    public List<BlobPhotosDTO> getAllBlobPhotos() {
        log.debug("REST request to get all BlobPhotos");
        return blobPhotosService.findAll();
    }

    /**
     * {@code GET  /blob-photos/:id} : get the "id" blobPhotos.
     *
     * @param id the id of the blobPhotosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blobPhotosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blob-photos/{id}")
    public ResponseEntity<BlobPhotosDTO> getBlobPhotos(@PathVariable String id) {
        log.debug("REST request to get BlobPhotos : {}", id);
        Optional<BlobPhotosDTO> blobPhotosDTO = blobPhotosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blobPhotosDTO);
    }

    /**
     * {@code DELETE  /blob-photos/:id} : delete the "id" blobPhotos.
     *
     * @param id the id of the blobPhotosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blob-photos/{id}")
    public ResponseEntity<Void> deleteBlobPhotos(@PathVariable String id) {
        log.debug("REST request to delete BlobPhotos : {}", id);
        blobPhotosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
