package com.epmserver.cloudblob.repository;
import com.epmserver.cloudblob.domain.BlobDocument;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the BlobDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlobDocumentRepository extends MongoRepository<BlobDocument, String> {

}
