package com.epmserver.cloudblob.web.rest;

import com.epmserver.cloudblob.service.BlobMixedDocumentService;
import com.epmserver.cloudblob.web.rest.errors.BadRequestAlertException;
import com.epmserver.cloudblob.service.dto.BlobMixedDocumentDTO;

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
 * REST controller for managing {@link com.epmserver.cloudblob.domain.BlobMixedDocument}.
 */
@RestController
@RequestMapping("/api")
public class BlobMixedDocumentResource {

    private final Logger log = LoggerFactory.getLogger(BlobMixedDocumentResource.class);

    private static final String ENTITY_NAME = "epmcloudblobBlobMixedDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlobMixedDocumentService blobMixedDocumentService;

    public BlobMixedDocumentResource(BlobMixedDocumentService blobMixedDocumentService) {
        this.blobMixedDocumentService = blobMixedDocumentService;
    }

    /**
     * {@code POST  /blob-mixed-documents} : Create a new blobMixedDocument.
     *
     * @param blobMixedDocumentDTO the blobMixedDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blobMixedDocumentDTO, or with status {@code 400 (Bad Request)} if the blobMixedDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blob-mixed-documents")
    public ResponseEntity<BlobMixedDocumentDTO> createBlobMixedDocument(@RequestBody BlobMixedDocumentDTO blobMixedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save BlobMixedDocument : {}", blobMixedDocumentDTO);
        if (blobMixedDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new blobMixedDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlobMixedDocumentDTO result = blobMixedDocumentService.save(blobMixedDocumentDTO);
        return ResponseEntity.created(new URI("/api/blob-mixed-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blob-mixed-documents} : Updates an existing blobMixedDocument.
     *
     * @param blobMixedDocumentDTO the blobMixedDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blobMixedDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the blobMixedDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blobMixedDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blob-mixed-documents")
    public ResponseEntity<BlobMixedDocumentDTO> updateBlobMixedDocument(@RequestBody BlobMixedDocumentDTO blobMixedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update BlobMixedDocument : {}", blobMixedDocumentDTO);
        if (blobMixedDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlobMixedDocumentDTO result = blobMixedDocumentService.save(blobMixedDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blobMixedDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blob-mixed-documents} : get all the blobMixedDocuments.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blobMixedDocuments in body.
     */
    @GetMapping("/blob-mixed-documents")
    public List<BlobMixedDocumentDTO> getAllBlobMixedDocuments() {
        log.debug("REST request to get all BlobMixedDocuments");
        return blobMixedDocumentService.findAll();
    }

    /**
     * {@code GET  /blob-mixed-documents/:id} : get the "id" blobMixedDocument.
     *
     * @param id the id of the blobMixedDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blobMixedDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blob-mixed-documents/{id}")
    public ResponseEntity<BlobMixedDocumentDTO> getBlobMixedDocument(@PathVariable String id) {
        log.debug("REST request to get BlobMixedDocument : {}", id);
        Optional<BlobMixedDocumentDTO> blobMixedDocumentDTO = blobMixedDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blobMixedDocumentDTO);
    }

    /**
     * {@code DELETE  /blob-mixed-documents/:id} : delete the "id" blobMixedDocument.
     *
     * @param id the id of the blobMixedDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blob-mixed-documents/{id}")
    public ResponseEntity<Void> deleteBlobMixedDocument(@PathVariable String id) {
        log.debug("REST request to delete BlobMixedDocument : {}", id);
        blobMixedDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
