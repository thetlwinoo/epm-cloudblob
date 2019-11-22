package com.epmserver.cloudblob.service.mapper;

import com.epmserver.cloudblob.domain.*;
import com.epmserver.cloudblob.service.dto.BlobPhotosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BlobPhotos} and its DTO {@link BlobPhotosDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BlobPhotosMapper extends EntityMapper<BlobPhotosDTO, BlobPhotos> {



    default BlobPhotos fromId(String id) {
        if (id == null) {
            return null;
        }
        BlobPhotos blobPhotos = new BlobPhotos();
        blobPhotos.setId(id);
        return blobPhotos;
    }
}
