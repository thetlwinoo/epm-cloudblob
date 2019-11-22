package com.epmserver.cloudblob.service.impl;

import com.epmserver.cloudblob.service.BlobDocumentService;
import com.epmserver.cloudblob.domain.BlobDocument;
import com.epmserver.cloudblob.repository.BlobDocumentRepository;
import com.epmserver.cloudblob.service.dto.BlobDocumentDTO;
import com.epmserver.cloudblob.service.mapper.BlobDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BlobDocument}.
 */
@Service
public class BlobDocumentServiceImpl implements BlobDocumentService {

    private final Logger log = LoggerFactory.getLogger(BlobDocumentServiceImpl.class);

    private final BlobDocumentRepository blobDocumentRepository;

    private final BlobDocumentMapper blobDocumentMapper;

    public BlobDocumentServiceImpl(BlobDocumentRepository blobDocumentRepository, BlobDocumentMapper blobDocumentMapper) {
        this.blobDocumentRepository = blobDocumentRepository;
        this.blobDocumentMapper = blobDocumentMapper;
    }

    /**
     * Save a blobDocument.
     *
     * @param blobDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BlobDocumentDTO save(BlobDocumentDTO blobDocumentDTO) {
        log.debug("Request to save BlobDocument : {}", blobDocumentDTO);
        BlobDocument blobDocument = blobDocumentMapper.toEntity(blobDocumentDTO);
        blobDocument = blobDocumentRepository.save(blobDocument);
        return blobDocumentMapper.toDto(blobDocument);
    }

    /**
     * Get all the blobDocuments.
     *
     * @return the list of entities.
     */
    @Override
    public List<BlobDocumentDTO> findAll() {
        log.debug("Request to get all BlobDocuments");
        return blobDocumentRepository.findAll().stream()
            .map(blobDocumentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one blobDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<BlobDocumentDTO> findOne(String id) {
        log.debug("Request to get BlobDocument : {}", id);
        return blobDocumentRepository.findById(id)
            .map(blobDocumentMapper::toDto);
    }

    /**
     * Delete the blobDocument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete BlobDocument : {}", id);
        blobDocumentRepository.deleteById(id);
    }
}
