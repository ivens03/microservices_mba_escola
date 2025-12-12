package br.com.mba.spring.colegio.disciplinas.service.impl;

import br.com.mba.spring.colegio.disciplinas.dto.NotaDTO;
import br.com.mba.spring.colegio.disciplinas.model.Nota;

import java.util.List;

public interface NotaServiceImpl {
    Nota lancharNota(NotaDTO dto);
    Nota atualizarNota(Long id, NotaDTO dto);
    List<Nota> listarTodasNotas();
    Nota buscarNotaPorId(Long id);
    List<Nota> buscarNotasPorAluno(Long idAluno);
    void excluirNota(Long id);
}
