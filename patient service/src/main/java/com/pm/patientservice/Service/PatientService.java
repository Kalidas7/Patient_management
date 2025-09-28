package com.pm.patientservice.Service;

import com.pm.patientservice.DTO.PatientRequestDTO;
import com.pm.patientservice.DTO.PatientResponseDTO;
import com.pm.patientservice.Mapper.PatientMapper;
import com.pm.patientservice.Repository.PatientRepository;
import com.pm.patientservice.exception.EmaillAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.model.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
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
        if(patientRepository.existsByemail(patientRequestDTO.getEmail()))
        {
            throw new EmaillAlreadyExistsException(("A patient with this email alsready exsists"+patientRequestDTO.getEmail()));
        }

        Patient newpatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));


        return PatientMapper.toDTO(newpatient);
    }
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO)
    {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID" + id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id))
        {
            throw new EmaillAlreadyExistsException(("A patient with this email alsready exsists"+patientRequestDTO.getEmail()));
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedpatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedpatient);
    }


    public void deletePatient(UUID id)
    {
        patientRepository.deleteById(id);
    }

}



