package com.epmserver.cloudblob.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmserver.cloudblob.domain.BlobPhotos} entity.
 */
public class BlobPhotosDTO implements Serializable {

    private String id;

    private byte[] thumbnailPhotoBlob;

    private String thumbnailPhotoBlobContentType;
    private byte[] originalPhotoBlob;

    private String originalPhotoBlobContentType;
    private byte[] bannerTallPhotoBlob;

    private String bannerTallPhotoBlobContentType;
    private byte[] bannerWidePhotoBlob;

    private String bannerWidePhotoBlobContentType;
    private byte[] circlePhotoBlob;

    private String circlePhotoBlobContentType;
    private byte[] sharpenedPhotoBlob;

    private String sharpenedPhotoBlobContentType;
    private byte[] squarePhotoBlob;

    private String squarePhotoBlobContentType;
    private byte[] watermarkPhotoBlob;

    private String watermarkPhotoBlobContentType;
    private String lastEditedBy;

    private Instant lastEditedWhen;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getThumbnailPhotoBlob() {
        return thumbnailPhotoBlob;
    }

    public void setThumbnailPhotoBlob(byte[] thumbnailPhotoBlob) {
        this.thumbnailPhotoBlob = thumbnailPhotoBlob;
    }

    public String getThumbnailPhotoBlobContentType() {
        return thumbnailPhotoBlobContentType;
    }

    public void setThumbnailPhotoBlobContentType(String thumbnailPhotoBlobContentType) {
        this.thumbnailPhotoBlobContentType = thumbnailPhotoBlobContentType;
    }

    public byte[] getOriginalPhotoBlob() {
        return originalPhotoBlob;
    }

    public void setOriginalPhotoBlob(byte[] originalPhotoBlob) {
        this.originalPhotoBlob = originalPhotoBlob;
    }

    public String getOriginalPhotoBlobContentType() {
        return originalPhotoBlobContentType;
    }

    public void setOriginalPhotoBlobContentType(String originalPhotoBlobContentType) {
        this.originalPhotoBlobContentType = originalPhotoBlobContentType;
    }

    public byte[] getBannerTallPhotoBlob() {
        return bannerTallPhotoBlob;
    }

    public void setBannerTallPhotoBlob(byte[] bannerTallPhotoBlob) {
        this.bannerTallPhotoBlob = bannerTallPhotoBlob;
    }

    public String getBannerTallPhotoBlobContentType() {
        return bannerTallPhotoBlobContentType;
    }

    public void setBannerTallPhotoBlobContentType(String bannerTallPhotoBlobContentType) {
        this.bannerTallPhotoBlobContentType = bannerTallPhotoBlobContentType;
    }

    public byte[] getBannerWidePhotoBlob() {
        return bannerWidePhotoBlob;
    }

    public void setBannerWidePhotoBlob(byte[] bannerWidePhotoBlob) {
        this.bannerWidePhotoBlob = bannerWidePhotoBlob;
    }

    public String getBannerWidePhotoBlobContentType() {
        return bannerWidePhotoBlobContentType;
    }

    public void setBannerWidePhotoBlobContentType(String bannerWidePhotoBlobContentType) {
        this.bannerWidePhotoBlobContentType = bannerWidePhotoBlobContentType;
    }

    public byte[] getCirclePhotoBlob() {
        return circlePhotoBlob;
    }

    public void setCirclePhotoBlob(byte[] circlePhotoBlob) {
        this.circlePhotoBlob = circlePhotoBlob;
    }

    public String getCirclePhotoBlobContentType() {
        return circlePhotoBlobContentType;
    }

    public void setCirclePhotoBlobContentType(String circlePhotoBlobContentType) {
        this.circlePhotoBlobContentType = circlePhotoBlobContentType;
    }

    public byte[] getSharpenedPhotoBlob() {
        return sharpenedPhotoBlob;
    }

    public void setSharpenedPhotoBlob(byte[] sharpenedPhotoBlob) {
        this.sharpenedPhotoBlob = sharpenedPhotoBlob;
    }

    public String getSharpenedPhotoBlobContentType() {
        return sharpenedPhotoBlobContentType;
    }

    public void setSharpenedPhotoBlobContentType(String sharpenedPhotoBlobContentType) {
        this.sharpenedPhotoBlobContentType = sharpenedPhotoBlobContentType;
    }

    public byte[] getSquarePhotoBlob() {
        return squarePhotoBlob;
    }

    public void setSquarePhotoBlob(byte[] squarePhotoBlob) {
        this.squarePhotoBlob = squarePhotoBlob;
    }

    public String getSquarePhotoBlobContentType() {
        return squarePhotoBlobContentType;
    }

    public void setSquarePhotoBlobContentType(String squarePhotoBlobContentType) {
        this.squarePhotoBlobContentType = squarePhotoBlobContentType;
    }

    public byte[] getWatermarkPhotoBlob() {
        return watermarkPhotoBlob;
    }

    public void setWatermarkPhotoBlob(byte[] watermarkPhotoBlob) {
        this.watermarkPhotoBlob = watermarkPhotoBlob;
    }

    public String getWatermarkPhotoBlobContentType() {
        return watermarkPhotoBlobContentType;
    }

    public void setWatermarkPhotoBlobContentType(String watermarkPhotoBlobContentType) {
        this.watermarkPhotoBlobContentType = watermarkPhotoBlobContentType;
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

        BlobPhotosDTO blobPhotosDTO = (BlobPhotosDTO) o;
        if (blobPhotosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blobPhotosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BlobPhotosDTO{" +
            "id=" + getId() +
            ", thumbnailPhotoBlob='" + getThumbnailPhotoBlob() + "'" +
            ", originalPhotoBlob='" + getOriginalPhotoBlob() + "'" +
            ", bannerTallPhotoBlob='" + getBannerTallPhotoBlob() + "'" +
            ", bannerWidePhotoBlob='" + getBannerWidePhotoBlob() + "'" +
            ", circlePhotoBlob='" + getCirclePhotoBlob() + "'" +
            ", sharpenedPhotoBlob='" + getSharpenedPhotoBlob() + "'" +
            ", squarePhotoBlob='" + getSquarePhotoBlob() + "'" +
            ", watermarkPhotoBlob='" + getWatermarkPhotoBlob() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
