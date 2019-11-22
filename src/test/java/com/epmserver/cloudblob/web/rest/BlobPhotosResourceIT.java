package com.epmserver.cloudblob.web.rest;

import com.epmserver.cloudblob.EpmcloudblobApp;
import com.epmserver.cloudblob.domain.BlobPhotos;
import com.epmserver.cloudblob.repository.BlobPhotosRepository;
import com.epmserver.cloudblob.service.BlobPhotosService;
import com.epmserver.cloudblob.service.dto.BlobPhotosDTO;
import com.epmserver.cloudblob.service.mapper.BlobPhotosMapper;
import com.epmserver.cloudblob.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.epmserver.cloudblob.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BlobPhotosResource} REST controller.
 */
@SpringBootTest(classes = EpmcloudblobApp.class)
public class BlobPhotosResourceIT {

    private static final byte[] DEFAULT_THUMBNAIL_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_THUMBNAIL_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ORIGINAL_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ORIGINAL_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BANNER_TALL_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BANNER_TALL_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BANNER_WIDE_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BANNER_WIDE_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CIRCLE_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CIRCLE_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CIRCLE_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CIRCLE_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SHARPENED_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SHARPENED_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SHARPENED_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SHARPENED_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SQUARE_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SQUARE_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SQUARE_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SQUARE_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_WATERMARK_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_WATERMARK_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_WATERMARK_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_WATERMARK_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BlobPhotosRepository blobPhotosRepository;

    @Autowired
    private BlobPhotosMapper blobPhotosMapper;

    @Autowired
    private BlobPhotosService blobPhotosService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBlobPhotosMockMvc;

    private BlobPhotos blobPhotos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlobPhotosResource blobPhotosResource = new BlobPhotosResource(blobPhotosService);
        this.restBlobPhotosMockMvc = MockMvcBuilders.standaloneSetup(blobPhotosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlobPhotos createEntity() {
        BlobPhotos blobPhotos = new BlobPhotos()
            .thumbnailPhotoBlob(DEFAULT_THUMBNAIL_PHOTO_BLOB)
            .thumbnailPhotoBlobContentType(DEFAULT_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE)
            .originalPhotoBlob(DEFAULT_ORIGINAL_PHOTO_BLOB)
            .originalPhotoBlobContentType(DEFAULT_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE)
            .bannerTallPhotoBlob(DEFAULT_BANNER_TALL_PHOTO_BLOB)
            .bannerTallPhotoBlobContentType(DEFAULT_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE)
            .bannerWidePhotoBlob(DEFAULT_BANNER_WIDE_PHOTO_BLOB)
            .bannerWidePhotoBlobContentType(DEFAULT_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE)
            .circlePhotoBlob(DEFAULT_CIRCLE_PHOTO_BLOB)
            .circlePhotoBlobContentType(DEFAULT_CIRCLE_PHOTO_BLOB_CONTENT_TYPE)
            .sharpenedPhotoBlob(DEFAULT_SHARPENED_PHOTO_BLOB)
            .sharpenedPhotoBlobContentType(DEFAULT_SHARPENED_PHOTO_BLOB_CONTENT_TYPE)
            .squarePhotoBlob(DEFAULT_SQUARE_PHOTO_BLOB)
            .squarePhotoBlobContentType(DEFAULT_SQUARE_PHOTO_BLOB_CONTENT_TYPE)
            .watermarkPhotoBlob(DEFAULT_WATERMARK_PHOTO_BLOB)
            .watermarkPhotoBlobContentType(DEFAULT_WATERMARK_PHOTO_BLOB_CONTENT_TYPE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return blobPhotos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlobPhotos createUpdatedEntity() {
        BlobPhotos blobPhotos = new BlobPhotos()
            .thumbnailPhotoBlob(UPDATED_THUMBNAIL_PHOTO_BLOB)
            .thumbnailPhotoBlobContentType(UPDATED_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE)
            .originalPhotoBlob(UPDATED_ORIGINAL_PHOTO_BLOB)
            .originalPhotoBlobContentType(UPDATED_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE)
            .bannerTallPhotoBlob(UPDATED_BANNER_TALL_PHOTO_BLOB)
            .bannerTallPhotoBlobContentType(UPDATED_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE)
            .bannerWidePhotoBlob(UPDATED_BANNER_WIDE_PHOTO_BLOB)
            .bannerWidePhotoBlobContentType(UPDATED_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE)
            .circlePhotoBlob(UPDATED_CIRCLE_PHOTO_BLOB)
            .circlePhotoBlobContentType(UPDATED_CIRCLE_PHOTO_BLOB_CONTENT_TYPE)
            .sharpenedPhotoBlob(UPDATED_SHARPENED_PHOTO_BLOB)
            .sharpenedPhotoBlobContentType(UPDATED_SHARPENED_PHOTO_BLOB_CONTENT_TYPE)
            .squarePhotoBlob(UPDATED_SQUARE_PHOTO_BLOB)
            .squarePhotoBlobContentType(UPDATED_SQUARE_PHOTO_BLOB_CONTENT_TYPE)
            .watermarkPhotoBlob(UPDATED_WATERMARK_PHOTO_BLOB)
            .watermarkPhotoBlobContentType(UPDATED_WATERMARK_PHOTO_BLOB_CONTENT_TYPE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return blobPhotos;
    }

    @BeforeEach
    public void initTest() {
        blobPhotosRepository.deleteAll();
        blobPhotos = createEntity();
    }

    @Test
    public void createBlobPhotos() throws Exception {
        int databaseSizeBeforeCreate = blobPhotosRepository.findAll().size();

        // Create the BlobPhotos
        BlobPhotosDTO blobPhotosDTO = blobPhotosMapper.toDto(blobPhotos);
        restBlobPhotosMockMvc.perform(post("/api/blob-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobPhotosDTO)))
            .andExpect(status().isCreated());

        // Validate the BlobPhotos in the database
        List<BlobPhotos> blobPhotosList = blobPhotosRepository.findAll();
        assertThat(blobPhotosList).hasSize(databaseSizeBeforeCreate + 1);
        BlobPhotos testBlobPhotos = blobPhotosList.get(blobPhotosList.size() - 1);
        assertThat(testBlobPhotos.getThumbnailPhotoBlob()).isEqualTo(DEFAULT_THUMBNAIL_PHOTO_BLOB);
        assertThat(testBlobPhotos.getThumbnailPhotoBlobContentType()).isEqualTo(DEFAULT_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getOriginalPhotoBlob()).isEqualTo(DEFAULT_ORIGINAL_PHOTO_BLOB);
        assertThat(testBlobPhotos.getOriginalPhotoBlobContentType()).isEqualTo(DEFAULT_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getBannerTallPhotoBlob()).isEqualTo(DEFAULT_BANNER_TALL_PHOTO_BLOB);
        assertThat(testBlobPhotos.getBannerTallPhotoBlobContentType()).isEqualTo(DEFAULT_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getBannerWidePhotoBlob()).isEqualTo(DEFAULT_BANNER_WIDE_PHOTO_BLOB);
        assertThat(testBlobPhotos.getBannerWidePhotoBlobContentType()).isEqualTo(DEFAULT_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getCirclePhotoBlob()).isEqualTo(DEFAULT_CIRCLE_PHOTO_BLOB);
        assertThat(testBlobPhotos.getCirclePhotoBlobContentType()).isEqualTo(DEFAULT_CIRCLE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getSharpenedPhotoBlob()).isEqualTo(DEFAULT_SHARPENED_PHOTO_BLOB);
        assertThat(testBlobPhotos.getSharpenedPhotoBlobContentType()).isEqualTo(DEFAULT_SHARPENED_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getSquarePhotoBlob()).isEqualTo(DEFAULT_SQUARE_PHOTO_BLOB);
        assertThat(testBlobPhotos.getSquarePhotoBlobContentType()).isEqualTo(DEFAULT_SQUARE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getWatermarkPhotoBlob()).isEqualTo(DEFAULT_WATERMARK_PHOTO_BLOB);
        assertThat(testBlobPhotos.getWatermarkPhotoBlobContentType()).isEqualTo(DEFAULT_WATERMARK_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testBlobPhotos.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    public void createBlobPhotosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blobPhotosRepository.findAll().size();

        // Create the BlobPhotos with an existing ID
        blobPhotos.setId("existing_id");
        BlobPhotosDTO blobPhotosDTO = blobPhotosMapper.toDto(blobPhotos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlobPhotosMockMvc.perform(post("/api/blob-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobPhotosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlobPhotos in the database
        List<BlobPhotos> blobPhotosList = blobPhotosRepository.findAll();
        assertThat(blobPhotosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllBlobPhotos() throws Exception {
        // Initialize the database
        blobPhotosRepository.save(blobPhotos);

        // Get all the blobPhotosList
        restBlobPhotosMockMvc.perform(get("/api/blob-photos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blobPhotos.getId())))
            .andExpect(jsonPath("$.[*].thumbnailPhotoBlobContentType").value(hasItem(DEFAULT_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].thumbnailPhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_THUMBNAIL_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].originalPhotoBlobContentType").value(hasItem(DEFAULT_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].originalPhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_ORIGINAL_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].bannerTallPhotoBlobContentType").value(hasItem(DEFAULT_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bannerTallPhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER_TALL_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].bannerWidePhotoBlobContentType").value(hasItem(DEFAULT_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bannerWidePhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER_WIDE_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].circlePhotoBlobContentType").value(hasItem(DEFAULT_CIRCLE_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].circlePhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_CIRCLE_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].sharpenedPhotoBlobContentType").value(hasItem(DEFAULT_SHARPENED_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sharpenedPhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_SHARPENED_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].squarePhotoBlobContentType").value(hasItem(DEFAULT_SQUARE_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].squarePhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_SQUARE_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].watermarkPhotoBlobContentType").value(hasItem(DEFAULT_WATERMARK_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].watermarkPhotoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_WATERMARK_PHOTO_BLOB))))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    public void getBlobPhotos() throws Exception {
        // Initialize the database
        blobPhotosRepository.save(blobPhotos);

        // Get the blobPhotos
        restBlobPhotosMockMvc.perform(get("/api/blob-photos/{id}", blobPhotos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blobPhotos.getId()))
            .andExpect(jsonPath("$.thumbnailPhotoBlobContentType").value(DEFAULT_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.thumbnailPhotoBlob").value(Base64Utils.encodeToString(DEFAULT_THUMBNAIL_PHOTO_BLOB)))
            .andExpect(jsonPath("$.originalPhotoBlobContentType").value(DEFAULT_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.originalPhotoBlob").value(Base64Utils.encodeToString(DEFAULT_ORIGINAL_PHOTO_BLOB)))
            .andExpect(jsonPath("$.bannerTallPhotoBlobContentType").value(DEFAULT_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.bannerTallPhotoBlob").value(Base64Utils.encodeToString(DEFAULT_BANNER_TALL_PHOTO_BLOB)))
            .andExpect(jsonPath("$.bannerWidePhotoBlobContentType").value(DEFAULT_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.bannerWidePhotoBlob").value(Base64Utils.encodeToString(DEFAULT_BANNER_WIDE_PHOTO_BLOB)))
            .andExpect(jsonPath("$.circlePhotoBlobContentType").value(DEFAULT_CIRCLE_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.circlePhotoBlob").value(Base64Utils.encodeToString(DEFAULT_CIRCLE_PHOTO_BLOB)))
            .andExpect(jsonPath("$.sharpenedPhotoBlobContentType").value(DEFAULT_SHARPENED_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.sharpenedPhotoBlob").value(Base64Utils.encodeToString(DEFAULT_SHARPENED_PHOTO_BLOB)))
            .andExpect(jsonPath("$.squarePhotoBlobContentType").value(DEFAULT_SQUARE_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.squarePhotoBlob").value(Base64Utils.encodeToString(DEFAULT_SQUARE_PHOTO_BLOB)))
            .andExpect(jsonPath("$.watermarkPhotoBlobContentType").value(DEFAULT_WATERMARK_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.watermarkPhotoBlob").value(Base64Utils.encodeToString(DEFAULT_WATERMARK_PHOTO_BLOB)))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }

    @Test
    public void getNonExistingBlobPhotos() throws Exception {
        // Get the blobPhotos
        restBlobPhotosMockMvc.perform(get("/api/blob-photos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBlobPhotos() throws Exception {
        // Initialize the database
        blobPhotosRepository.save(blobPhotos);

        int databaseSizeBeforeUpdate = blobPhotosRepository.findAll().size();

        // Update the blobPhotos
        BlobPhotos updatedBlobPhotos = blobPhotosRepository.findById(blobPhotos.getId()).get();
        updatedBlobPhotos
            .thumbnailPhotoBlob(UPDATED_THUMBNAIL_PHOTO_BLOB)
            .thumbnailPhotoBlobContentType(UPDATED_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE)
            .originalPhotoBlob(UPDATED_ORIGINAL_PHOTO_BLOB)
            .originalPhotoBlobContentType(UPDATED_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE)
            .bannerTallPhotoBlob(UPDATED_BANNER_TALL_PHOTO_BLOB)
            .bannerTallPhotoBlobContentType(UPDATED_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE)
            .bannerWidePhotoBlob(UPDATED_BANNER_WIDE_PHOTO_BLOB)
            .bannerWidePhotoBlobContentType(UPDATED_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE)
            .circlePhotoBlob(UPDATED_CIRCLE_PHOTO_BLOB)
            .circlePhotoBlobContentType(UPDATED_CIRCLE_PHOTO_BLOB_CONTENT_TYPE)
            .sharpenedPhotoBlob(UPDATED_SHARPENED_PHOTO_BLOB)
            .sharpenedPhotoBlobContentType(UPDATED_SHARPENED_PHOTO_BLOB_CONTENT_TYPE)
            .squarePhotoBlob(UPDATED_SQUARE_PHOTO_BLOB)
            .squarePhotoBlobContentType(UPDATED_SQUARE_PHOTO_BLOB_CONTENT_TYPE)
            .watermarkPhotoBlob(UPDATED_WATERMARK_PHOTO_BLOB)
            .watermarkPhotoBlobContentType(UPDATED_WATERMARK_PHOTO_BLOB_CONTENT_TYPE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        BlobPhotosDTO blobPhotosDTO = blobPhotosMapper.toDto(updatedBlobPhotos);

        restBlobPhotosMockMvc.perform(put("/api/blob-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobPhotosDTO)))
            .andExpect(status().isOk());

        // Validate the BlobPhotos in the database
        List<BlobPhotos> blobPhotosList = blobPhotosRepository.findAll();
        assertThat(blobPhotosList).hasSize(databaseSizeBeforeUpdate);
        BlobPhotos testBlobPhotos = blobPhotosList.get(blobPhotosList.size() - 1);
        assertThat(testBlobPhotos.getThumbnailPhotoBlob()).isEqualTo(UPDATED_THUMBNAIL_PHOTO_BLOB);
        assertThat(testBlobPhotos.getThumbnailPhotoBlobContentType()).isEqualTo(UPDATED_THUMBNAIL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getOriginalPhotoBlob()).isEqualTo(UPDATED_ORIGINAL_PHOTO_BLOB);
        assertThat(testBlobPhotos.getOriginalPhotoBlobContentType()).isEqualTo(UPDATED_ORIGINAL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getBannerTallPhotoBlob()).isEqualTo(UPDATED_BANNER_TALL_PHOTO_BLOB);
        assertThat(testBlobPhotos.getBannerTallPhotoBlobContentType()).isEqualTo(UPDATED_BANNER_TALL_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getBannerWidePhotoBlob()).isEqualTo(UPDATED_BANNER_WIDE_PHOTO_BLOB);
        assertThat(testBlobPhotos.getBannerWidePhotoBlobContentType()).isEqualTo(UPDATED_BANNER_WIDE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getCirclePhotoBlob()).isEqualTo(UPDATED_CIRCLE_PHOTO_BLOB);
        assertThat(testBlobPhotos.getCirclePhotoBlobContentType()).isEqualTo(UPDATED_CIRCLE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getSharpenedPhotoBlob()).isEqualTo(UPDATED_SHARPENED_PHOTO_BLOB);
        assertThat(testBlobPhotos.getSharpenedPhotoBlobContentType()).isEqualTo(UPDATED_SHARPENED_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getSquarePhotoBlob()).isEqualTo(UPDATED_SQUARE_PHOTO_BLOB);
        assertThat(testBlobPhotos.getSquarePhotoBlobContentType()).isEqualTo(UPDATED_SQUARE_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getWatermarkPhotoBlob()).isEqualTo(UPDATED_WATERMARK_PHOTO_BLOB);
        assertThat(testBlobPhotos.getWatermarkPhotoBlobContentType()).isEqualTo(UPDATED_WATERMARK_PHOTO_BLOB_CONTENT_TYPE);
        assertThat(testBlobPhotos.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testBlobPhotos.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    public void updateNonExistingBlobPhotos() throws Exception {
        int databaseSizeBeforeUpdate = blobPhotosRepository.findAll().size();

        // Create the BlobPhotos
        BlobPhotosDTO blobPhotosDTO = blobPhotosMapper.toDto(blobPhotos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlobPhotosMockMvc.perform(put("/api/blob-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobPhotosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlobPhotos in the database
        List<BlobPhotos> blobPhotosList = blobPhotosRepository.findAll();
        assertThat(blobPhotosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBlobPhotos() throws Exception {
        // Initialize the database
        blobPhotosRepository.save(blobPhotos);

        int databaseSizeBeforeDelete = blobPhotosRepository.findAll().size();

        // Delete the blobPhotos
        restBlobPhotosMockMvc.perform(delete("/api/blob-photos/{id}", blobPhotos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BlobPhotos> blobPhotosList = blobPhotosRepository.findAll();
        assertThat(blobPhotosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlobPhotos.class);
        BlobPhotos blobPhotos1 = new BlobPhotos();
        blobPhotos1.setId("id1");
        BlobPhotos blobPhotos2 = new BlobPhotos();
        blobPhotos2.setId(blobPhotos1.getId());
        assertThat(blobPhotos1).isEqualTo(blobPhotos2);
        blobPhotos2.setId("id2");
        assertThat(blobPhotos1).isNotEqualTo(blobPhotos2);
        blobPhotos1.setId(null);
        assertThat(blobPhotos1).isNotEqualTo(blobPhotos2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlobPhotosDTO.class);
        BlobPhotosDTO blobPhotosDTO1 = new BlobPhotosDTO();
        blobPhotosDTO1.setId("id1");
        BlobPhotosDTO blobPhotosDTO2 = new BlobPhotosDTO();
        assertThat(blobPhotosDTO1).isNotEqualTo(blobPhotosDTO2);
        blobPhotosDTO2.setId(blobPhotosDTO1.getId());
        assertThat(blobPhotosDTO1).isEqualTo(blobPhotosDTO2);
        blobPhotosDTO2.setId("id2");
        assertThat(blobPhotosDTO1).isNotEqualTo(blobPhotosDTO2);
        blobPhotosDTO1.setId(null);
        assertThat(blobPhotosDTO1).isNotEqualTo(blobPhotosDTO2);
    }
}
