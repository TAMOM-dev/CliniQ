package dev.cliniq.cliniq.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cliniq.cliniq.Model.Cita;
import dev.cliniq.cliniq.Repository.citaRepository;

@Service
public class citaService implements ICitaService {
    @Autowired
    private citaRepository citaRepository;

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAll(); 
    }

    @Override
    public Cita buscarCitaPorId(Long idCita) {
        return citaRepository.findById(idCita).orElse(null);
    }

    @Override
    public void guardarCita(Cita cita) {
        citaRepository.save(cita);
    }

    @Override
    public void eliminarCita(Cita cita) {
        citaRepository.delete(cita);
    }
    
}
