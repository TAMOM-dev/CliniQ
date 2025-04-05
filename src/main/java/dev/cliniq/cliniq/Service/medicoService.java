package dev.cliniq.cliniq.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import dev.cliniq.cliniq.Model.Medico;
import dev.cliniq.cliniq.Repository.medicoRepository;

public class medicoService implements IMedicoService {

    @Autowired
    private medicoRepository medicoRepository;

    @Override
    public List<Medico> listarMedicos() {
        return medicoRepository.findAll();
    }

    @Override
    public Medico buscarMedicoPorId(Long idMedico) {
        return medicoRepository.findById(idMedico).orElse(null);
    }

    @Override
    public void guardarMedico(Medico medico) {
        medicoRepository.save(medico);
    }

    @Override
    public void eliminarMedico(Medico medico) {
        medicoRepository.delete(medico);
    }
    
}
