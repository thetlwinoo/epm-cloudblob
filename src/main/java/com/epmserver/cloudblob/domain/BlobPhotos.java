package com.epmserver.cloudblob.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BlobPhotos.
 */
@Document(collection = "blob_photos")
public class BlobPhotos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("thumbnail_photo_blob")
    private byte[] thumbnailPhotoBlob;

    @Field("thumbnail_photo_blob_content_type")
    private String thumbnailPhotoBlobContentType;

    @Field("original_photo_blob")
    private byte[] originalPhotoBlob;

    @Field("original_photo_blob_content_type")
    private String originalPhotoBlobContentType;

    @Field("banner_tall_photo_blob")
    private byte[] bannerTallPhotoBlob;

    @Field("banner_tall_photo_blob_content_type")
    private String bannerTallPhotoBlobContentType;

    @Field("banner_wide_photo_blob")
    private byte[] bannerWidePhotoBlob;

    @Field("banner_wide_photo_blob_content_type")
    private String bannerWidePhotoBlobContentType;

    @Field("circle_photo_blob")
    private byte[] circlePhotoBlob;

    @Field("circle_photo_blob_content_type")
    private String circlePhotoBlobContentType;

    @Field("sharpened_photo_blob")
    private byte[] sharpenedPhotoBlob;

    @Field("sharpened_photo_blob_content_type")
    private String sharpenedPhotoBlobContentType;

    @Field("square_photo_blob")
    private byte[] squarePhotoBlob;

    @Field("square_photo_blob_content_type")
    private String squarePhotoBlobContentType;

    @Field("watermark_photo_blob")
    private byte[] watermarkPhotoBlob;

    @Field("watermark_photo_blob_content_type")
    private String watermarkPhotoBlobContentType;

    @Field("last_edited_by")
    private String lastEditedBy;

    @Field("last_edited_when")
    private Instant lastEditedWhen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getThumbnailPhotoBlob() {
        return thumbnailPhotoBlob;
    }

    public BlobPhotos thumbnailPhotoBlob(byte[] thumbnailPhotoBlob) {
        this.thumbnailPhotoBlob = thumbnailPhotoBlob;
        return this;
    }

    public void setThumbnailPhotoBlob(byte[] thumbnailPhotoBlob) {
        this.thumbnailPhotoBlob = thumbnailPhotoBlob;
    }

    public String getThumbnailPhotoBlobContentType() {
        return thumbnailPhotoBlobContentType;
    }

    public BlobPhotos thumbnailPhotoBlobContentType(String thumbnailPhotoBlobContentType) {
        this.thumbnailPhotoBlobContentType = thumbnailPhotoBlobContentType;
        return this;
    }

    public void setThumbnailPhotoBlobContentType(String thumbnailPhotoBlobContentType) {
        this.thumbnailPhotoBlobContentType = thumbnailPhotoBlobContentType;
    }

    public byte[] getOriginalPhotoBlob() {
        return originalPhotoBlob;
    }

    public BlobPhotos originalPhotoBlob(byte[] originalPhotoBlob) {
        this.originalPhotoBlob = originalPhotoBlob;
        return this;
    }

    public void setOriginalPhotoBlob(byte[] originalPhotoBlob) {
        this.originalPhotoBlob = originalPhotoBlob;
    }

    public String getOriginalPhotoBlobContentType() {
        return originalPhotoBlobContentType;
    }

    public BlobPhotos originalPhotoBlobContentType(String originalPhotoBlobContentType) {
        this.originalPhotoBlobContentType = originalPhotoBlobContentType;
        return this;
    }

    public void setOriginalPhotoBlobContentType(String originalPhotoBlobContentType) {
        this.originalPhotoBlobContentType = originalPhotoBlobContentType;
    }

    public byte[] getBannerTallPhotoBlob() {
        return bannerTallPhotoBlob;
    }

    public BlobPhotos bannerTallPhotoBlob(byte[] bannerTallPhotoBlob) {
        this.bannerTallPhotoBlob = bannerTallPhotoBlob;
        return this;
    }

    public void setBannerTallPhotoBlob(byte[] bannerTallPhotoBlob) {
        this.bannerTallPhotoBlob = bannerTallPhotoBlob;
    }

    public String getBannerTallPhotoBlobContentType() {
        return bannerTallPhotoBlobContentType;
    }

    public BlobPhotos bannerTallPhotoBlobContentType(String bannerTallPhotoBlobContentType) {
        this.bannerTallPhotoBlobContentType = bannerTallPhotoBlobContentType;
        return this;
    }

    public void setBannerTallPhotoBlobContentType(String bannerTallPhotoBlobContentType) {
        this.bannerTallPhotoBlobContentType = bannerTallPhotoBlobContentType;
    }

    public byte[] getBannerWidePhotoBlob() {
        return bannerWidePhotoBlob;
    }

    public BlobPhotos bannerWidePhotoBlob(byte[] bannerWidePhotoBlob) {
        this.bannerWidePhotoBlob = bannerWidePhotoBlob;
        return this;
    }

    public void setBannerWidePhotoBlob(byte[] bannerWidePhotoBlob) {
        this.bannerWidePhotoBlob = bannerWidePhotoBlob;
    }

    public String getBannerWidePhotoBlobContentType() {
        return bannerWidePhotoBlobContentType;
    }

    public BlobPhotos bannerWidePhotoBlobContentType(String bannerWidePhotoBlobContentType) {
        this.bannerWidePhotoBlobContentType = bannerWidePhotoBlobContentType;
        return this;
    }

    public void setBannerWidePhotoBlobContentType(String bannerWidePhotoBlobContentType) {
        this.bannerWidePhotoBlobContentType = bannerWidePhotoBlobContentType;
    }

    public byte[] getCirclePhotoBlob() {
        return circlePhotoBlob;
    }

    public BlobPhotos circlePhotoBlob(byte[] circlePhotoBlob) {
        this.circlePhotoBlob = circlePhotoBlob;
        return this;
    }

    public void setCirclePhotoBlob(byte[] circlePhotoBlob) {
        this.circlePhotoBlob = circlePhotoBlob;
    }

    public String getCirclePhotoBlobContentType() {
        return circlePhotoBlobContentType;
    }

    public BlobPhotos circlePhotoBlobContentType(String circlePhotoBlobContentType) {
        this.circlePhotoBlobContentType = circlePhotoBlobContentType;
        return this;
    }

    public void setCirclePhotoBlobContentType(String circlePhotoBlobContentType) {
        this.circlePhotoBlobContentType = circlePhotoBlobContentType;
    }

    public byte[] getSharpenedPhotoBlob() {
        return sharpenedPhotoBlob;
    }

    public BlobPhotos sharpenedPhotoBlob(byte[] sharpenedPhotoBlob) {
        this.sharpenedPhotoBlob = sharpenedPhotoBlob;
        return this;
    }

    public void setSharpenedPhotoBlob(byte[] sharpenedPhotoBlob) {
        this.sharpenedPhotoBlob = sharpenedPhotoBlob;
    }

    public String getSharpenedPhotoBlobContentType() {
        return sharpenedPhotoBlobContentType;
    }

    public BlobPhotos sharpenedPhotoBlobContentType(String sharpenedPhotoBlobContentType) {
        this.sharpenedPhotoBlobContentType = sharpenedPhotoBlobContentType;
        return this;
    }

    public void setSharpenedPhotoBlobContentType(String sharpenedPhotoBlobContentType) {
        this.sharpenedPhotoBlobContentType = sharpenedPhotoBlobContentType;
    }

    public byte[] getSquarePhotoBlob() {
        return squarePhotoBlob;
    }

    public BlobPhotos squarePhotoBlob(byte[] squarePhotoBlob) {
        this.squarePhotoBlob = squarePhotoBlob;
        return this;
    }

    public void setSquarePhotoBlob(byte[] squarePhotoBlob) {
        this.squarePhotoBlob = squarePhotoBlob;
    }

    public String getSquarePhotoBlobContentType() {
        return squarePhotoBlobContentType;
    }

    public BlobPhotos squarePhotoBlobContentType(String squarePhotoBlobContentType) {
        this.squarePhotoBlobContentType = squarePhotoBlobContentType;
        return this;
    }

    public void setSquarePhotoBlobContentType(String squarePhotoBlobContentType) {
        this.squarePhotoBlobContentType = squarePhotoBlobContentType;
    }

    public byte[] getWatermarkPhotoBlob() {
        return watermarkPhotoBlob;
    }

    public BlobPhotos watermarkPhotoBlob(byte[] watermarkPhotoBlob) {
        this.watermarkPhotoBlob = watermarkPhotoBlob;
        return this;
    }

    public void setWatermarkPhotoBlob(byte[] watermarkPhotoBlob) {
        this.watermarkPhotoBlob = watermarkPhotoBlob;
    }

    public String getWatermarkPhotoBlobContentType() {
        return watermarkPhotoBlobContentType;
    }

    public BlobPhotos watermarkPhotoBlobContentType(String watermarkPhotoBlobContentType) {
        this.watermarkPhotoBlobContentType = watermarkPhotoBlobContentType;
        return this;
    }

    public void setWatermarkPhotoBlobContentType(String watermarkPhotoBlobContentType) {
        this.watermarkPhotoBlobContentType = watermarkPhotoBlobContentType;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public BlobPhotos lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public BlobPhotos lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlobPhotos)) {
            return false;
        }
        return id != null && id.equals(((BlobPhotos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BlobPhotos{" +
            "id=" + getId() +
            ", thumbnailPhotoBlob='" + getThumbnailPhotoBlob() + "'" +
            ", thumbnailPhotoBlobContentType='" + getThumbnailPhotoBlobContentType() + "'" +
            ", originalPhotoBlob='" + getOriginalPhotoBlob() + "'" +
            ", originalPhotoBlobContentType='" + getOriginalPhotoBlobContentType() + "'" +
            ", bannerTallPhotoBlob='" + getBannerTallPhotoBlob() + "'" +
            ", bannerTallPhotoBlobContentType='" + getBannerTallPhotoBlobContentType() + "'" +
            ", bannerWidePhotoBlob='" + getBannerWidePhotoBlob() + "'" +
            ", bannerWidePhotoBlobContentType='" + getBannerWidePhotoBlobContentType() + "'" +
            ", circlePhotoBlob='" + getCirclePhotoBlob() + "'" +
            ", circlePhotoBlobContentType='" + getCirclePhotoBlobContentType() + "'" +
            ", sharpenedPhotoBlob='" + getSharpenedPhotoBlob() + "'" +
            ", sharpenedPhotoBlobContentType='" + getSharpenedPhotoBlobContentType() + "'" +
            ", squarePhotoBlob='" + getSquarePhotoBlob() + "'" +
            ", squarePhotoBlobContentType='" + getSquarePhotoBlobContentType() + "'" +
            ", watermarkPhotoBlob='" + getWatermarkPhotoBlob() + "'" +
            ", watermarkPhotoBlobContentType='" + getWatermarkPhotoBlobContentType() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
