package dev.cliniq.cliniq.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cliniq.cliniq.Model.Cita;
import dev.cliniq.cliniq.Model.Consultorio;
import dev.cliniq.cliniq.Model.Medico;
import dev.cliniq.cliniq.Model.Paciente;
import dev.cliniq.cliniq.Repository.citaRepository;
import dev.cliniq.cliniq.Repository.medicoRepository;
import dev.cliniq.cliniq.Repository.pacienteRepository;
import dev.cliniq.cliniq.Repository.consultorioRepository;



@Service
public class citaService implements ICitaService {
    @Autowired
    private citaRepository citaRepository;

    @Autowired
    private medicoRepository medicoRepository;

    @Autowired
    private pacienteRepository pacienteRespository;

    @Autowired
    private consultorioRepository consultorioRepository;

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAll(); 
    }

    @Override
    public Cita buscarCitaPorId(Long idCita) {
        return citaRepository.findById(idCita).orElse(null);
    }

    @Override
    public Cita guardarCita(Long idPaciente, Long idMedico, Long idConsultorio,LocalDate fecha, String estado) {
        //Validar que el paciente y el medico existan
        Paciente paciente = pacienteRespository.findById(idPaciente)
            .orElseThrow(() -> new IllegalArgumentException("El paciente no existe"));

        Medico medico = medicoRepository.findById(idMedico)
            .orElseThrow(() -> new IllegalArgumentException("El medico no existe"));

        Consultorio consultorio = consultorioRepository.findById(idConsultorio)
            .orElseThrow(() -> new IllegalArgumentException("El consultorio no existe"));

        //Crear la cita
        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setConsultorio(consultorio);
        cita.setFecha(fecha);
        cita.setEstado(estado);

        return citaRepository.save(cita);
    }

    @Override
    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void eliminarCita(Cita cita) {
        citaRepository.delete(cita);
    }
    
}
