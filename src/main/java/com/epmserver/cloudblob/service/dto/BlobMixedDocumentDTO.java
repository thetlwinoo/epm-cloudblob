package com.epmserver.cloudblob.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmserver.cloudblob.domain.BlobMixedDocument} entity.
 */
public class BlobMixedDocumentDTO implements Serializable {

    private String id;

    private String document;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlobMixedDocumentDTO blobMixedDocumentDTO = (BlobMixedDocumentDTO) o;
        if (blobMixedDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blobMixedDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BlobMixedDocumentDTO{" +
            "id=" + getId() +
            ", document='" + getDocument() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
