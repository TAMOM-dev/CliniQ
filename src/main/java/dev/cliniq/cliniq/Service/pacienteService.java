package dev.cliniq.cliniq.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import dev.cliniq.cliniq.Model.Paciente;
import dev.cliniq.cliniq.Repository.pacienteRepository;

public class pacienteService implements IPacienteService {

    //instancia de la clase de repositorio
    @Autowired
    private pacienteRepository pacienteRepository;

    //Devuelve la lista de todos los pacientes
    @Override
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    //Devuelve el paciente con el id pasado como parámetro, si no exite devuelve null
    @Override
    public Paciente buscarPacientePorId(Long idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente).orElse(null);
        return paciente;
    }


    /** 
     * Si el valor es igual a null, se creará un nuevo registro
     * Si el valor no es igual a null, se actualizará el registro
     */
    @Override
    public void guardarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    @Override
    public void elininarPaciente(Paciente paciente) {
        pacienteRepository.delete(paciente);
    }

}
