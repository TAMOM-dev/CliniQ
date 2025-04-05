package dev.cliniq.cliniq.Service;

import java.util.List;

import dev.cliniq.cliniq.Model.Medico;

public interface IMedicoService {
    public List<Medico> listarMedicos();

    public Medico buscarMedicoPorId(Long idMedico);

    public void guardarMedico(Medico medico);

    public void eliminarMedico(Medico medico);
}
