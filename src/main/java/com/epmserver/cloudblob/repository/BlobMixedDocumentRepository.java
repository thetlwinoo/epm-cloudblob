package com.epmserver.cloudblob.repository;
import com.epmserver.cloudblob.domain.BlobMixedDocument;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the BlobMixedDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlobMixedDocumentRepository extends MongoRepository<BlobMixedDocument, String> {

}
