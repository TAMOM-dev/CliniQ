package dev.cliniq.cliniq.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cliniq.cliniq.Model.Factura;
import dev.cliniq.cliniq.Repository.FacturaRepository;

@Service
public class FacturaService implements IFacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura buscarFacturaPorId(Long idFactura) {
        return facturaRepository.findById(idFactura).orElse(null);
    }

    @Override
    public void guardarFactura(Factura factura) {
        facturaRepository.save(factura);
    }

    @Override
    public void eliminarFactura(Factura factura) {
        facturaRepository.delete(factura);
    }
    
}
