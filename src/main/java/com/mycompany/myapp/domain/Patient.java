package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "fisrt_name")
    private String fisrtName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "d_ob")
    private String dOB;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "email_veried")
    private Boolean emailVeried;

    @Column(name = "mobile_verified")
    private Boolean mobileVerified;

    @Column(name = "address")
    private Integer address;

    @OneToOne
    @JoinColumn(unique = true)
    private Address addressId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public Patient patientId(Integer patientId) {
        this.patientId = patientId;
        return this;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getFisrtName() {
        return fisrtName;
    }

    public Patient fisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
        return this;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    public String getLastName() {
        return lastName;
    }

    public Patient lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public Patient fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getdOB() {
        return dOB;
    }

    public Patient dOB(String dOB) {
        this.dOB = dOB;
        return this;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Patient mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public Patient emailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Boolean isEmailVeried() {
        return emailVeried;
    }

    public Patient emailVeried(Boolean emailVeried) {
        this.emailVeried = emailVeried;
        return this;
    }

    public void setEmailVeried(Boolean emailVeried) {
        this.emailVeried = emailVeried;
    }

    public Boolean isMobileVerified() {
        return mobileVerified;
    }

    public Patient mobileVerified(Boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
        return this;
    }

    public void setMobileVerified(Boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public Integer getAddress() {
        return address;
    }

    public Patient address(Integer address) {
        this.address = address;
        return this;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Address getAddressId() {
        return addressId;
    }

    public Patient addressId(Address address) {
        this.addressId = address;
        return this;
    }

    public void setAddressId(Address address) {
        this.addressId = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", patientId=" + getPatientId() +
            ", fisrtName='" + getFisrtName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", dOB='" + getdOB() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", emailVeried='" + isEmailVeried() + "'" +
            ", mobileVerified='" + isMobileVerified() + "'" +
            ", address=" + getAddress() +
            "}";
    }
}
