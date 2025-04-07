package dev.cliniq.cliniq.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cliniq.cliniq.Model.Medico;
import dev.cliniq.cliniq.Repository.medicoRepository;

@Service
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

    @Override
    public boolean existeMedico(Long idMedico) {
        return medicoRepository.existsById(idMedico);
    }
    
}
