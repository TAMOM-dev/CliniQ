package dev.cliniq.cliniq.Service;

import java.util.List;

import dev.cliniq.cliniq.Model.Paciente;

public interface IPacienteService {
    public List<Paciente> listarPacientes();

    public Paciente buscarPacientePorId(Long idPaciente);

    //Se agrega si no exite, se actualiza si existe (JPA)
    public void guardarPaciente(Paciente paciente);

    public void elininarPaciente(Paciente paciente);
}
