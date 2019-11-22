package com.epmserver.cloudblob.service;

import com.epmserver.cloudblob.service.dto.BlobDocumentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.cloudblob.domain.BlobDocument}.
 */
public interface BlobDocumentService {

    /**
     * Save a blobDocument.
     *
     * @param blobDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    BlobDocumentDTO save(BlobDocumentDTO blobDocumentDTO);

    /**
     * Get all the blobDocuments.
     *
     * @return the list of entities.
     */
    List<BlobDocumentDTO> findAll();


    /**
     * Get the "id" blobDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlobDocumentDTO> findOne(String id);

    /**
     * Delete the "id" blobDocument.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
