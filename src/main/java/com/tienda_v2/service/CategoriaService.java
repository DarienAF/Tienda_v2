
package com.tienda_v2.service;

import com.tienda_v2.domain.Categoria;
import java.util.List;

public interface CategoriaService {
    public List<Categoria> getCategorias(boolean activo);
}
