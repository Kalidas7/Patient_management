package com.pm.patientservice.Service;

import com.pm.patientservice.DTO.PatientResponseDTO;
import com.pm.patientservice.Mapper.PatientMapper;
import com.pm.patientservice.Repository.PatientRepository;
import com.pm.patientservice.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

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


}

