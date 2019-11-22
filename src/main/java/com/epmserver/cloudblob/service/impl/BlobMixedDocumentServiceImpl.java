package com.epmserver.cloudblob.service.impl;

import com.epmserver.cloudblob.service.BlobMixedDocumentService;
import com.epmserver.cloudblob.domain.BlobMixedDocument;
import com.epmserver.cloudblob.repository.BlobMixedDocumentRepository;
import com.epmserver.cloudblob.service.dto.BlobMixedDocumentDTO;
import com.epmserver.cloudblob.service.mapper.BlobMixedDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BlobMixedDocument}.
 */
@Service
public class BlobMixedDocumentServiceImpl implements BlobMixedDocumentService {

    private final Logger log = LoggerFactory.getLogger(BlobMixedDocumentServiceImpl.class);

    private final BlobMixedDocumentRepository blobMixedDocumentRepository;

    private final BlobMixedDocumentMapper blobMixedDocumentMapper;

    public BlobMixedDocumentServiceImpl(BlobMixedDocumentRepository blobMixedDocumentRepository, BlobMixedDocumentMapper blobMixedDocumentMapper) {
        this.blobMixedDocumentRepository = blobMixedDocumentRepository;
        this.blobMixedDocumentMapper = blobMixedDocumentMapper;
    }

    /**
     * Save a blobMixedDocument.
     *
     * @param blobMixedDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BlobMixedDocumentDTO save(BlobMixedDocumentDTO blobMixedDocumentDTO) {
        log.debug("Request to save BlobMixedDocument : {}", blobMixedDocumentDTO);
        BlobMixedDocument blobMixedDocument = blobMixedDocumentMapper.toEntity(blobMixedDocumentDTO);
        blobMixedDocument = blobMixedDocumentRepository.save(blobMixedDocument);
        return blobMixedDocumentMapper.toDto(blobMixedDocument);
    }

    /**
     * Get all the blobMixedDocuments.
     *
     * @return the list of entities.
     */
    @Override
    public List<BlobMixedDocumentDTO> findAll() {
        log.debug("Request to get all BlobMixedDocuments");
        return blobMixedDocumentRepository.findAll().stream()
            .map(blobMixedDocumentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one blobMixedDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<BlobMixedDocumentDTO> findOne(String id) {
        log.debug("Request to get BlobMixedDocument : {}", id);
        return blobMixedDocumentRepository.findById(id)
            .map(blobMixedDocumentMapper::toDto);
    }

    /**
     * Delete the blobMixedDocument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete BlobMixedDocument : {}", id);
        blobMixedDocumentRepository.deleteById(id);
    }
}
