package com.epmserver.cloudblob.web.rest;

import com.epmserver.cloudblob.service.BlobDocumentService;
import com.epmserver.cloudblob.web.rest.errors.BadRequestAlertException;
import com.epmserver.cloudblob.service.dto.BlobDocumentDTO;

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
 * REST controller for managing {@link com.epmserver.cloudblob.domain.BlobDocument}.
 */
@RestController
@RequestMapping("/api")
public class BlobDocumentResource {

    private final Logger log = LoggerFactory.getLogger(BlobDocumentResource.class);

    private static final String ENTITY_NAME = "epmcloudblobBlobDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlobDocumentService blobDocumentService;

    public BlobDocumentResource(BlobDocumentService blobDocumentService) {
        this.blobDocumentService = blobDocumentService;
    }

    /**
     * {@code POST  /blob-documents} : Create a new blobDocument.
     *
     * @param blobDocumentDTO the blobDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blobDocumentDTO, or with status {@code 400 (Bad Request)} if the blobDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blob-documents")
    public ResponseEntity<BlobDocumentDTO> createBlobDocument(@RequestBody BlobDocumentDTO blobDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save BlobDocument : {}", blobDocumentDTO);
        if (blobDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new blobDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlobDocumentDTO result = blobDocumentService.save(blobDocumentDTO);
        return ResponseEntity.created(new URI("/api/blob-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blob-documents} : Updates an existing blobDocument.
     *
     * @param blobDocumentDTO the blobDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blobDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the blobDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blobDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blob-documents")
    public ResponseEntity<BlobDocumentDTO> updateBlobDocument(@RequestBody BlobDocumentDTO blobDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update BlobDocument : {}", blobDocumentDTO);
        if (blobDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlobDocumentDTO result = blobDocumentService.save(blobDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blobDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blob-documents} : get all the blobDocuments.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blobDocuments in body.
     */
    @GetMapping("/blob-documents")
    public List<BlobDocumentDTO> getAllBlobDocuments() {
        log.debug("REST request to get all BlobDocuments");
        return blobDocumentService.findAll();
    }

    /**
     * {@code GET  /blob-documents/:id} : get the "id" blobDocument.
     *
     * @param id the id of the blobDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blobDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blob-documents/{id}")
    public ResponseEntity<BlobDocumentDTO> getBlobDocument(@PathVariable String id) {
        log.debug("REST request to get BlobDocument : {}", id);
        Optional<BlobDocumentDTO> blobDocumentDTO = blobDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blobDocumentDTO);
    }

    /**
     * {@code DELETE  /blob-documents/:id} : delete the "id" blobDocument.
     *
     * @param id the id of the blobDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blob-documents/{id}")
    public ResponseEntity<Void> deleteBlobDocument(@PathVariable String id) {
        log.debug("REST request to delete BlobDocument : {}", id);
        blobDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
