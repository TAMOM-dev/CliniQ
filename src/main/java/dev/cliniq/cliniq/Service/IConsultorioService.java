package dev.cliniq.cliniq.Service;

import java.util.List;

import dev.cliniq.cliniq.Model.Consultorio;

public interface IConsultorioService {
    public List<Consultorio> listarConsultorios();

    public boolean existeConsultorio(Long idConsultorio);

    public Consultorio buscarConsultorioPorId(Long idConsultorio);

    public void guardarConsultorio(Consultorio consultorio);

    public void eliminarConsutlorio(Consultorio consultorio);
}
