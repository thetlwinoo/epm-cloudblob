package com.epmserver.cloudblob.web.rest;

import com.epmserver.cloudblob.EpmcloudblobApp;
import com.epmserver.cloudblob.domain.BlobMixedDocument;
import com.epmserver.cloudblob.repository.BlobMixedDocumentRepository;
import com.epmserver.cloudblob.service.BlobMixedDocumentService;
import com.epmserver.cloudblob.service.dto.BlobMixedDocumentDTO;
import com.epmserver.cloudblob.service.mapper.BlobMixedDocumentMapper;
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
 * Integration tests for the {@link BlobMixedDocumentResource} REST controller.
 */
@SpringBootTest(classes = EpmcloudblobApp.class)
public class BlobMixedDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BlobMixedDocumentRepository blobMixedDocumentRepository;

    @Autowired
    private BlobMixedDocumentMapper blobMixedDocumentMapper;

    @Autowired
    private BlobMixedDocumentService blobMixedDocumentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBlobMixedDocumentMockMvc;

    private BlobMixedDocument blobMixedDocument;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlobMixedDocumentResource blobMixedDocumentResource = new BlobMixedDocumentResource(blobMixedDocumentService);
        this.restBlobMixedDocumentMockMvc = MockMvcBuilders.standaloneSetup(blobMixedDocumentResource)
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
    public static BlobMixedDocument createEntity() {
        BlobMixedDocument blobMixedDocument = new BlobMixedDocument()
            .document(DEFAULT_DOCUMENT)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return blobMixedDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlobMixedDocument createUpdatedEntity() {
        BlobMixedDocument blobMixedDocument = new BlobMixedDocument()
            .document(UPDATED_DOCUMENT)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return blobMixedDocument;
    }

    @BeforeEach
    public void initTest() {
        blobMixedDocumentRepository.deleteAll();
        blobMixedDocument = createEntity();
    }

    @Test
    public void createBlobMixedDocument() throws Exception {
        int databaseSizeBeforeCreate = blobMixedDocumentRepository.findAll().size();

        // Create the BlobMixedDocument
        BlobMixedDocumentDTO blobMixedDocumentDTO = blobMixedDocumentMapper.toDto(blobMixedDocument);
        restBlobMixedDocumentMockMvc.perform(post("/api/blob-mixed-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobMixedDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the BlobMixedDocument in the database
        List<BlobMixedDocument> blobMixedDocumentList = blobMixedDocumentRepository.findAll();
        assertThat(blobMixedDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        BlobMixedDocument testBlobMixedDocument = blobMixedDocumentList.get(blobMixedDocumentList.size() - 1);
        assertThat(testBlobMixedDocument.getDocument()).isEqualTo(DEFAULT_DOCUMENT);
        assertThat(testBlobMixedDocument.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testBlobMixedDocument.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    public void createBlobMixedDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blobMixedDocumentRepository.findAll().size();

        // Create the BlobMixedDocument with an existing ID
        blobMixedDocument.setId("existing_id");
        BlobMixedDocumentDTO blobMixedDocumentDTO = blobMixedDocumentMapper.toDto(blobMixedDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlobMixedDocumentMockMvc.perform(post("/api/blob-mixed-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobMixedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlobMixedDocument in the database
        List<BlobMixedDocument> blobMixedDocumentList = blobMixedDocumentRepository.findAll();
        assertThat(blobMixedDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllBlobMixedDocuments() throws Exception {
        // Initialize the database
        blobMixedDocumentRepository.save(blobMixedDocument);

        // Get all the blobMixedDocumentList
        restBlobMixedDocumentMockMvc.perform(get("/api/blob-mixed-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blobMixedDocument.getId())))
            .andExpect(jsonPath("$.[*].document").value(hasItem(DEFAULT_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    public void getBlobMixedDocument() throws Exception {
        // Initialize the database
        blobMixedDocumentRepository.save(blobMixedDocument);

        // Get the blobMixedDocument
        restBlobMixedDocumentMockMvc.perform(get("/api/blob-mixed-documents/{id}", blobMixedDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blobMixedDocument.getId()))
            .andExpect(jsonPath("$.document").value(DEFAULT_DOCUMENT.toString()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }

    @Test
    public void getNonExistingBlobMixedDocument() throws Exception {
        // Get the blobMixedDocument
        restBlobMixedDocumentMockMvc.perform(get("/api/blob-mixed-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBlobMixedDocument() throws Exception {
        // Initialize the database
        blobMixedDocumentRepository.save(blobMixedDocument);

        int databaseSizeBeforeUpdate = blobMixedDocumentRepository.findAll().size();

        // Update the blobMixedDocument
        BlobMixedDocument updatedBlobMixedDocument = blobMixedDocumentRepository.findById(blobMixedDocument.getId()).get();
        updatedBlobMixedDocument
            .document(UPDATED_DOCUMENT)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        BlobMixedDocumentDTO blobMixedDocumentDTO = blobMixedDocumentMapper.toDto(updatedBlobMixedDocument);

        restBlobMixedDocumentMockMvc.perform(put("/api/blob-mixed-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobMixedDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the BlobMixedDocument in the database
        List<BlobMixedDocument> blobMixedDocumentList = blobMixedDocumentRepository.findAll();
        assertThat(blobMixedDocumentList).hasSize(databaseSizeBeforeUpdate);
        BlobMixedDocument testBlobMixedDocument = blobMixedDocumentList.get(blobMixedDocumentList.size() - 1);
        assertThat(testBlobMixedDocument.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testBlobMixedDocument.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testBlobMixedDocument.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    public void updateNonExistingBlobMixedDocument() throws Exception {
        int databaseSizeBeforeUpdate = blobMixedDocumentRepository.findAll().size();

        // Create the BlobMixedDocument
        BlobMixedDocumentDTO blobMixedDocumentDTO = blobMixedDocumentMapper.toDto(blobMixedDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlobMixedDocumentMockMvc.perform(put("/api/blob-mixed-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blobMixedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlobMixedDocument in the database
        List<BlobMixedDocument> blobMixedDocumentList = blobMixedDocumentRepository.findAll();
        assertThat(blobMixedDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBlobMixedDocument() throws Exception {
        // Initialize the database
        blobMixedDocumentRepository.save(blobMixedDocument);

        int databaseSizeBeforeDelete = blobMixedDocumentRepository.findAll().size();

        // Delete the blobMixedDocument
        restBlobMixedDocumentMockMvc.perform(delete("/api/blob-mixed-documents/{id}", blobMixedDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BlobMixedDocument> blobMixedDocumentList = blobMixedDocumentRepository.findAll();
        assertThat(blobMixedDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlobMixedDocument.class);
        BlobMixedDocument blobMixedDocument1 = new BlobMixedDocument();
        blobMixedDocument1.setId("id1");
        BlobMixedDocument blobMixedDocument2 = new BlobMixedDocument();
        blobMixedDocument2.setId(blobMixedDocument1.getId());
        assertThat(blobMixedDocument1).isEqualTo(blobMixedDocument2);
        blobMixedDocument2.setId("id2");
        assertThat(blobMixedDocument1).isNotEqualTo(blobMixedDocument2);
        blobMixedDocument1.setId(null);
        assertThat(blobMixedDocument1).isNotEqualTo(blobMixedDocument2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlobMixedDocumentDTO.class);
        BlobMixedDocumentDTO blobMixedDocumentDTO1 = new BlobMixedDocumentDTO();
        blobMixedDocumentDTO1.setId("id1");
        BlobMixedDocumentDTO blobMixedDocumentDTO2 = new BlobMixedDocumentDTO();
        assertThat(blobMixedDocumentDTO1).isNotEqualTo(blobMixedDocumentDTO2);
        blobMixedDocumentDTO2.setId(blobMixedDocumentDTO1.getId());
        assertThat(blobMixedDocumentDTO1).isEqualTo(blobMixedDocumentDTO2);
        blobMixedDocumentDTO2.setId("id2");
        assertThat(blobMixedDocumentDTO1).isNotEqualTo(blobMixedDocumentDTO2);
        blobMixedDocumentDTO1.setId(null);
        assertThat(blobMixedDocumentDTO1).isNotEqualTo(blobMixedDocumentDTO2);
    }
}
