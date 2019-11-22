package com.epmserver.cloudblob.service;

import com.epmserver.cloudblob.service.dto.BlobMixedDocumentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.cloudblob.domain.BlobMixedDocument}.
 */
public interface BlobMixedDocumentService {

    /**
     * Save a blobMixedDocument.
     *
     * @param blobMixedDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    BlobMixedDocumentDTO save(BlobMixedDocumentDTO blobMixedDocumentDTO);

    /**
     * Get all the blobMixedDocuments.
     *
     * @return the list of entities.
     */
    List<BlobMixedDocumentDTO> findAll();


    /**
     * Get the "id" blobMixedDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlobMixedDocumentDTO> findOne(String id);

    /**
     * Delete the "id" blobMixedDocument.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
