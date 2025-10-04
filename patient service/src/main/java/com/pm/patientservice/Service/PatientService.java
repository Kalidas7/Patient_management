package com.pm.patientservice.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pm.patientservice.DTO.PatientRequestDTO;
import com.pm.patientservice.DTO.PatientResponseDTO;
import com.pm.patientservice.Mapper.PatientMapper;
import com.pm.patientservice.Repository.PatientRepository;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.model.Patient;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        if (patientRepository == null) {
            throw new IllegalArgumentException("PatientRepository cannot be null");
        }
        this.patientRepository = patientRepository;
    }



    public List<PatientResponseDTO> getPatients()
    {

        List<Patient> patients = patientRepository.findAll();

        List<PatientResponseDTO> patientResponseDTOS = patients.stream().map(PatientMapper::toDTO).toList();

        return patientResponseDTOS;


    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO)
    {
        if (patientRequestDTO == null) {
            throw new IllegalArgumentException("PatientRequestDTO cannot be null");
        }
        if (patientRequestDTO.getEmail() == null || patientRequestDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail()))
        {
            throw new EmailAlreadyExistsException("A patient with this email already exists: " + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO)
    {
        if (id == null) {
            throw new IllegalArgumentException("Patient ID cannot be null");
        }
        if (patientRequestDTO == null) {
            throw new IllegalArgumentException("PatientRequestDTO cannot be null");
        }
        if (patientRequestDTO.getEmail() == null || patientRequestDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID: " + id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id))
        {
            throw new EmailAlreadyExistsException("A patient with this email already exists: " + patientRequestDTO.getEmail());
        }

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

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }


    public void deletePatient(UUID id)
    {
        if (id == null) {
            throw new IllegalArgumentException("Patient ID cannot be null");
        }
        
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with ID: " + id);
        }
        patientRepository.deleteById(id);
    }

}



