package com.epmserver.cloudblob.repository;
import com.epmserver.cloudblob.domain.BlobPhotos;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the BlobPhotos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlobPhotosRepository extends MongoRepository<BlobPhotos, String> {

}
