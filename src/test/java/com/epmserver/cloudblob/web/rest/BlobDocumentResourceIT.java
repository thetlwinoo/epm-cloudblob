package com.epmserver.cloudblob.web.rest;

import com.epmserver.cloudblob.EpmcloudblobApp;
import com.epmserver.cloudblob.domain.BlobDocument;
import com.epmserver.cloudblob.repository.BlobDocumentRepository;
import com.epmserver.cloudblob.service.BlobDocumentService;
import com.epmserver.cloudblob.service.dto.BlobDocumentDTO;
import com.epmserver.cloudblob.service.mapper.BlobDocumentMapper;
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
 * Integration tests for the {@link BlobDocumentResource} REST controller.
 */
@SpringBootTest(classes = EpmcloudblobApp.class)
public class BlobDocumentResourceIT {

    private static final byte[] DEFAULT_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BlobDocumentRepository blobDocumentRepository;

    @Autowired
    private BlobDocumentMapper blobDocumentMapper;

    @Autowired
    private BlobDocumentService blobDocumentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBlobDocumentMockMvc;

    private BlobDocument blobDocument;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlobDocumentResource blobDocumentResource = new BlobDocumentResource(blobDocumentService);
        this.restBlobDocumentMockMvc = MockMvcBuilders.standaloneSetup(blobDocumentResource)
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
    public static BlobDocument createEntity() {
        BlobDocument blobDocument = new BlobDocument()
            .document(DEFAULT_DOCUMENT)
            .documentContentType(DEFAULT_DOCUMENT_CONTENT_TYPE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return blobDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlobDocument createUpdatedEntity() {
        BlobDocument blobDocument = new BlobDocument()
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return blobDocument;
    }

    @BeforeEach
    public void initTest() {
        blobDocumentRepository.deleteAll();
        blobDocument = createEntity();
    }

    @Test
    public void createBlobDocument() throws Exception {
        int databaseSizeBeforeCreate = blobDocumentRepository.findAll().size();

        // Create the BlobDocument
        BlobDocumentDTO blobDocumentDTO = blobDocumentMapper.toDto(blobDocument);
        restBlobDocumentMockMvc.perform(post("/api/blob-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the BlobDocument in the database
        List<BlobDocument> blobDocumentList = blobDocumentRepository.findAll();
        assertThat(blobDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        BlobDocument testBlobDocument = blobDocumentList.get(blobDocumentList.size() - 1);
        assertThat(testBlobDocument.getDocument()).isEqualTo(DEFAULT_DOCUMENT);
        assertThat(testBlobDocument.getDocumentContentType()).isEqualTo(DEFAULT_DOCUMENT_CONTENT_TYPE);
        assertThat(testBlobDocument.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testBlobDocument.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    public void createBlobDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blobDocumentRepository.findAll().size();

        // Create the BlobDocument with an existing ID
        blobDocument.setId("existing_id");
        BlobDocumentDTO blobDocumentDTO = blobDocumentMapper.toDto(blobDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlobDocumentMockMvc.perform(post("/api/blob-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlobDocument in the database
        List<BlobDocument> blobDocumentList = blobDocumentRepository.findAll();
        assertThat(blobDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllBlobDocuments() throws Exception {
        // Initialize the database
        blobDocumentRepository.save(blobDocument);

        // Get all the blobDocumentList
        restBlobDocumentMockMvc.perform(get("/api/blob-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blobDocument.getId())))
            .andExpect(jsonPath("$.[*].documentContentType").value(hasItem(DEFAULT_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT))))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    public void getBlobDocument() throws Exception {
        // Initialize the database
        blobDocumentRepository.save(blobDocument);

        // Get the blobDocument
        restBlobDocumentMockMvc.perform(get("/api/blob-documents/{id}", blobDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blobDocument.getId()))
            .andExpect(jsonPath("$.documentContentType").value(DEFAULT_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.document").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT)))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }

    @Test
    public void getNonExistingBlobDocument() throws Exception {
        // Get the blobDocument
        restBlobDocumentMockMvc.perform(get("/api/blob-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBlobDocument() throws Exception {
        // Initialize the database
        blobDocumentRepository.save(blobDocument);

        int databaseSizeBeforeUpdate = blobDocumentRepository.findAll().size();

        // Update the blobDocument
        BlobDocument updatedBlobDocument = blobDocumentRepository.findById(blobDocument.getId()).get();
        updatedBlobDocument
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        BlobDocumentDTO blobDocumentDTO = blobDocumentMapper.toDto(updatedBlobDocument);

        restBlobDocumentMockMvc.perform(put("/api/blob-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the BlobDocument in the database
        List<BlobDocument> blobDocumentList = blobDocumentRepository.findAll();
        assertThat(blobDocumentList).hasSize(databaseSizeBeforeUpdate);
        BlobDocument testBlobDocument = blobDocumentList.get(blobDocumentList.size() - 1);
        assertThat(testBlobDocument.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testBlobDocument.getDocumentContentType()).isEqualTo(UPDATED_DOCUMENT_CONTENT_TYPE);
        assertThat(testBlobDocument.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testBlobDocument.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    public void updateNonExistingBlobDocument() throws Exception {
        int databaseSizeBeforeUpdate = blobDocumentRepository.findAll().size();

        // Create the BlobDocument
        BlobDocumentDTO blobDocumentDTO = blobDocumentMapper.toDto(blobDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlobDocumentMockMvc.perform(put("/api/blob-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlobDocument in the database
        List<BlobDocument> blobDocumentList = blobDocumentRepository.findAll();
        assertThat(blobDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBlobDocument() throws Exception {
        // Initialize the database
        blobDocumentRepository.save(blobDocument);

        int databaseSizeBeforeDelete = blobDocumentRepository.findAll().size();

        // Delete the blobDocument
        restBlobDocumentMockMvc.perform(delete("/api/blob-documents/{id}", blobDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BlobDocument> blobDocumentList = blobDocumentRepository.findAll();
        assertThat(blobDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlobDocument.class);
        BlobDocument blobDocument1 = new BlobDocument();
        blobDocument1.setId("id1");
        BlobDocument blobDocument2 = new BlobDocument();
        blobDocument2.setId(blobDocument1.getId());
        assertThat(blobDocument1).isEqualTo(blobDocument2);
        blobDocument2.setId("id2");
        assertThat(blobDocument1).isNotEqualTo(blobDocument2);
        blobDocument1.setId(null);
        assertThat(blobDocument1).isNotEqualTo(blobDocument2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlobDocumentDTO.class);
        BlobDocumentDTO blobDocumentDTO1 = new BlobDocumentDTO();
        blobDocumentDTO1.setId("id1");
        BlobDocumentDTO blobDocumentDTO2 = new BlobDocumentDTO();
        assertThat(blobDocumentDTO1).isNotEqualTo(blobDocumentDTO2);
        blobDocumentDTO2.setId(blobDocumentDTO1.getId());
        assertThat(blobDocumentDTO1).isEqualTo(blobDocumentDTO2);
        blobDocumentDTO2.setId("id2");
        assertThat(blobDocumentDTO1).isNotEqualTo(blobDocumentDTO2);
        blobDocumentDTO1.setId(null);
        assertThat(blobDocumentDTO1).isNotEqualTo(blobDocumentDTO2);
    }
}
