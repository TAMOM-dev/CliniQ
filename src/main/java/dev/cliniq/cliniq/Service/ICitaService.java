package dev.cliniq.cliniq.Service;

import java.util.List;

import dev.cliniq.cliniq.Model.Cita;

public interface ICitaService {
    public List<Cita> listarCitas();

    public Cita buscarCitaPorId(Long idCita);

    public void guardarCita(Cita cita);

    public void eliminarCita(Cita cita);
}
