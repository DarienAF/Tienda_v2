
package com.tienda_v2.service.impl;

import com.tienda_v2.dao.CategoriaDao;
import com.tienda_v2.domain.Categoria;
import com.tienda_v2.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService{

    @Autowired
    private CategoriaDao categoriaDao;
    
    @Override
    public List<Categoria> getCategorias(boolean activo) {
        var categorias = categoriaDao.findAll();
        return categorias;
    }
}
