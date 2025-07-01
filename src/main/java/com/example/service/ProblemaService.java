package com.example.service;

import com.example.model.Problema;
import com.example.repository.ProblemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemaService {

    private final ProblemaRepository problemaRepository;

    public ProblemaService(ProblemaRepository problemaRepository) {
        this.problemaRepository = problemaRepository;
    }

    public List<Problema> getProblemeByUtilizator(int utilizatorID) {
        return problemaRepository.findByUtilizatorID(utilizatorID);
    }

    public void addProblema(Problema problema) {
        problemaRepository.save(problema);
    }

    public void updateProblema(int problemaID, Problema problema) {
        problemaRepository.update(problemaID, problema);
    }

    public void deleteProblema(int problemaID) {
        problemaRepository.delete(problemaID);
    }
    public Problema getProblemaById(int id) {
        return problemaRepository.getProblemaById(id);
    }
}
