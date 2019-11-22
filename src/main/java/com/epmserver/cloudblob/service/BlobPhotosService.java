package com.epmserver.cloudblob.service;

import com.epmserver.cloudblob.service.dto.BlobPhotosDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.cloudblob.domain.BlobPhotos}.
 */
public interface BlobPhotosService {

    /**
     * Save a blobPhotos.
     *
     * @param blobPhotosDTO the entity to save.
     * @return the persisted entity.
     */
    BlobPhotosDTO save(BlobPhotosDTO blobPhotosDTO);

    /**
     * Get all the blobPhotos.
     *
     * @return the list of entities.
     */
    List<BlobPhotosDTO> findAll();


    /**
     * Get the "id" blobPhotos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlobPhotosDTO> findOne(String id);

    /**
     * Delete the "id" blobPhotos.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
