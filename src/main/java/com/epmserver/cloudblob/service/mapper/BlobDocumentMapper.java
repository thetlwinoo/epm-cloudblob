package com.epmserver.cloudblob.service.mapper;

import com.epmserver.cloudblob.domain.*;
import com.epmserver.cloudblob.service.dto.BlobDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BlobDocument} and its DTO {@link BlobDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BlobDocumentMapper extends EntityMapper<BlobDocumentDTO, BlobDocument> {



    default BlobDocument fromId(String id) {
        if (id == null) {
            return null;
        }
        BlobDocument blobDocument = new BlobDocument();
        blobDocument.setId(id);
        return blobDocument;
    }
}
