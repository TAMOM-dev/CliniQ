package dev.cliniq.cliniq.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cliniq.cliniq.Model.Consultorio;

import dev.cliniq.cliniq.Repository.consultorioRepository;


@Service
public class consultorioService implements IConsultorioService {
    @Autowired
    private consultorioRepository consultorioRepository;


    @Override
    public List<Consultorio> listarConsultorios() {
        return consultorioRepository.findAll();
    }

    @Override
    public Consultorio buscarConsultorioPorId(Long idConsultorio) {
        return consultorioRepository.findById(idConsultorio).orElse(null);
    }

    @Override
    public void guardarConsultorio(Consultorio consultorio) {
        consultorioRepository.save(consultorio);
    }

    @Override
    public void eliminarConsultorio(Consultorio consultorio) {
        consultorioRepository.delete(consultorio);
    }

    
}
