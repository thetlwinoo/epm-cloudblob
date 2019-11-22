package com.epmserver.cloudblob.service.impl;

import com.epmserver.cloudblob.service.BlobPhotosService;
import com.epmserver.cloudblob.domain.BlobPhotos;
import com.epmserver.cloudblob.repository.BlobPhotosRepository;
import com.epmserver.cloudblob.service.dto.BlobPhotosDTO;
import com.epmserver.cloudblob.service.mapper.BlobPhotosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BlobPhotos}.
 */
@Service
public class BlobPhotosServiceImpl implements BlobPhotosService {

    private final Logger log = LoggerFactory.getLogger(BlobPhotosServiceImpl.class);

    private final BlobPhotosRepository blobPhotosRepository;

    private final BlobPhotosMapper blobPhotosMapper;

    public BlobPhotosServiceImpl(BlobPhotosRepository blobPhotosRepository, BlobPhotosMapper blobPhotosMapper) {
        this.blobPhotosRepository = blobPhotosRepository;
        this.blobPhotosMapper = blobPhotosMapper;
    }

    /**
     * Save a blobPhotos.
     *
     * @param blobPhotosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BlobPhotosDTO save(BlobPhotosDTO blobPhotosDTO) {
        log.debug("Request to save BlobPhotos : {}", blobPhotosDTO);
        BlobPhotos blobPhotos = blobPhotosMapper.toEntity(blobPhotosDTO);
        blobPhotos = blobPhotosRepository.save(blobPhotos);
        return blobPhotosMapper.toDto(blobPhotos);
    }

    /**
     * Get all the blobPhotos.
     *
     * @return the list of entities.
     */
    @Override
    public List<BlobPhotosDTO> findAll() {
        log.debug("Request to get all BlobPhotos");
        return blobPhotosRepository.findAll().stream()
            .map(blobPhotosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one blobPhotos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<BlobPhotosDTO> findOne(String id) {
        log.debug("Request to get BlobPhotos : {}", id);
        return blobPhotosRepository.findById(id)
            .map(blobPhotosMapper::toDto);
    }

    /**
     * Delete the blobPhotos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete BlobPhotos : {}", id);
        blobPhotosRepository.deleteById(id);
    }
}
