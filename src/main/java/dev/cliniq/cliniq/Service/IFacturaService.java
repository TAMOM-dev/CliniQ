package dev.cliniq.cliniq.Service;

import java.util.List;

import dev.cliniq.cliniq.Model.Factura;

public interface IFacturaService {
    
    public List<Factura> listarFacturas();

    public Factura buscarFacturaPorId(Long idFactura);

    public void guardarFactura(Factura factura);

    public void eliminarFactura(Factura factura);
}
