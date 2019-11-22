package com.epmserver.cloudblob.service.mapper;

import com.epmserver.cloudblob.domain.*;
import com.epmserver.cloudblob.service.dto.BlobMixedDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BlobMixedDocument} and its DTO {@link BlobMixedDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BlobMixedDocumentMapper extends EntityMapper<BlobMixedDocumentDTO, BlobMixedDocument> {



    default BlobMixedDocument fromId(String id) {
        if (id == null) {
            return null;
        }
        BlobMixedDocument blobMixedDocument = new BlobMixedDocument();
        blobMixedDocument.setId(id);
        return blobMixedDocument;
    }
}
