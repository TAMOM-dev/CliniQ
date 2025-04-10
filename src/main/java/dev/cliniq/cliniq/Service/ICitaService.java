package dev.cliniq.cliniq.Service;

import java.time.LocalDate;
import java.util.List;

import dev.cliniq.cliniq.Model.Cita;

public interface ICitaService {
    public List<Cita> listarCitas();

    public Cita buscarCitaPorId(Long idCita);

    public Cita guardarCita(Cita cita);

    public Cita guardarCita(Long idPaciente, Long idMedico, Long idConsultorio, LocalDate fecha, String estado);

    public void eliminarCita(Cita cita);


}
