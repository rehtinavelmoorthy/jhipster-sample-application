package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Patient;
import com.mycompany.myapp.repository.PatientRepository;
import com.mycompany.myapp.service.PatientService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PatientResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class PatientResourceIT {

    private static final Integer DEFAULT_PATIENT_ID = 1;
    private static final Integer UPDATED_PATIENT_ID = 2;

    private static final String DEFAULT_FISRT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FISRT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_D_OB = "AAAAAAAAAA";
    private static final String UPDATED_D_OB = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EMAIL_VERIED = false;
    private static final Boolean UPDATED_EMAIL_VERIED = true;

    private static final Boolean DEFAULT_MOBILE_VERIFIED = false;
    private static final Boolean UPDATED_MOBILE_VERIFIED = true;

    private static final Integer DEFAULT_ADDRESS = 1;
    private static final Integer UPDATED_ADDRESS = 2;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPatientMockMvc;

    private Patient patient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientResource patientResource = new PatientResource(patientService);
        this.restPatientMockMvc = MockMvcBuilders.standaloneSetup(patientResource)
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
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .patientId(DEFAULT_PATIENT_ID)
            .fisrtName(DEFAULT_FISRT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .fullName(DEFAULT_FULL_NAME)
            .dOB(DEFAULT_D_OB)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .emailId(DEFAULT_EMAIL_ID)
            .emailVeried(DEFAULT_EMAIL_VERIED)
            .mobileVerified(DEFAULT_MOBILE_VERIFIED)
            .address(DEFAULT_ADDRESS);
        return patient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createUpdatedEntity(EntityManager em) {
        Patient patient = new Patient()
            .patientId(UPDATED_PATIENT_ID)
            .fisrtName(UPDATED_FISRT_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fullName(UPDATED_FULL_NAME)
            .dOB(UPDATED_D_OB)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .emailVeried(UPDATED_EMAIL_VERIED)
            .mobileVerified(UPDATED_MOBILE_VERIFIED)
            .address(UPDATED_ADDRESS);
        return patient;
    }

    @BeforeEach
    public void initTest() {
        patient = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getPatientId()).isEqualTo(DEFAULT_PATIENT_ID);
        assertThat(testPatient.getFisrtName()).isEqualTo(DEFAULT_FISRT_NAME);
        assertThat(testPatient.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPatient.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testPatient.getdOB()).isEqualTo(DEFAULT_D_OB);
        assertThat(testPatient.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testPatient.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testPatient.isEmailVeried()).isEqualTo(DEFAULT_EMAIL_VERIED);
        assertThat(testPatient.isMobileVerified()).isEqualTo(DEFAULT_MOBILE_VERIFIED);
        assertThat(testPatient.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient with an existing ID
        patient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc.perform(get("/api/patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientId").value(hasItem(DEFAULT_PATIENT_ID)))
            .andExpect(jsonPath("$.[*].fisrtName").value(hasItem(DEFAULT_FISRT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].dOB").value(hasItem(DEFAULT_D_OB)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].emailVeried").value(hasItem(DEFAULT_EMAIL_VERIED.booleanValue())))
            .andExpect(jsonPath("$.[*].mobileVerified").value(hasItem(DEFAULT_MOBILE_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.patientId").value(DEFAULT_PATIENT_ID))
            .andExpect(jsonPath("$.fisrtName").value(DEFAULT_FISRT_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.dOB").value(DEFAULT_D_OB))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID))
            .andExpect(jsonPath("$.emailVeried").value(DEFAULT_EMAIL_VERIED.booleanValue()))
            .andExpect(jsonPath("$.mobileVerified").value(DEFAULT_MOBILE_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatient() throws Exception {
        // Initialize the database
        patientService.save(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findById(patient.getId()).get();
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .patientId(UPDATED_PATIENT_ID)
            .fisrtName(UPDATED_FISRT_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fullName(UPDATED_FULL_NAME)
            .dOB(UPDATED_D_OB)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .emailVeried(UPDATED_EMAIL_VERIED)
            .mobileVerified(UPDATED_MOBILE_VERIFIED)
            .address(UPDATED_ADDRESS);

        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatient)))
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getPatientId()).isEqualTo(UPDATED_PATIENT_ID);
        assertThat(testPatient.getFisrtName()).isEqualTo(UPDATED_FISRT_NAME);
        assertThat(testPatient.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPatient.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testPatient.getdOB()).isEqualTo(UPDATED_D_OB);
        assertThat(testPatient.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testPatient.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testPatient.isEmailVeried()).isEqualTo(UPDATED_EMAIL_VERIED);
        assertThat(testPatient.isMobileVerified()).isEqualTo(UPDATED_MOBILE_VERIFIED);
        assertThat(testPatient.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Create the Patient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatient() throws Exception {
        // Initialize the database
        patientService.save(patient);

        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Delete the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}", patient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
