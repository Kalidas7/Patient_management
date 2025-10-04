package com.pm.patientservice.Mapper;

import java.time.LocalDate;

import com.pm.patientservice.DTO.PatientRequestDTO;
import com.pm.patientservice.DTO.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

public class PatientMapper {

    public static PatientResponseDTO toDTO(Patient patient){
        if (patient == null) {
            return null;
        }
        
        PatientResponseDTO patientDTO = new PatientResponseDTO();
        if (patient.getId() != null) {
            patientDTO.setId(patient.getId().toString());
        }
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        if (patient.getDateOfBirth() != null) {
            patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        }
        if (patient.getRegisterDate() != null) {
            patientDTO.setRegisteredDate(patient.getRegisterDate().toString());
        }
        return patientDTO;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO){
        if (patientRequestDTO == null) {
            return null;
        }
        
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        
        if (patientRequestDTO.getDateOfBirth() != null && !patientRequestDTO.getDateOfBirth().trim().isEmpty()) {
            try {
                patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid date format for dateOfBirth: " + patientRequestDTO.getDateOfBirth());
            }
        }
        
        // Set register date to current date for new patients
        patient.setRegisterDate(LocalDate.now());

        return patient;
    }
}
