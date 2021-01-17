package com.ronaldo.os.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronaldo.os.api.model.Comentario;
import com.ronaldo.os.domain.exception.EntidadeNaoEncontradaException;
import com.ronaldo.os.domain.exception.NegocioException;
import com.ronaldo.os.domain.model.Cliente;
import com.ronaldo.os.domain.model.OrdemServico;
import com.ronaldo.os.domain.model.StatusOrdemServico;
import com.ronaldo.os.domain.repository.ClienteRepository;
import com.ronaldo.os.domain.repository.ComentarioRepository;
import com.ronaldo.os.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		/**
		 * Busca o Cliente para ser criada a ordem de serviço
		 * Se não contiver nenhum Cliente em Optional retorna a Exceção
		 * @return retorna um Optional<Cliente> ou Exceção
		 */
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		return ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}

	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
									.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}
	
	public void finalizar(Long ordemServicoid) {
		OrdemServico ordemSevico = buscar(ordemServicoid);
		ordemSevico.finalizar();
		ordemServicoRepository.save(ordemSevico);
	}
}
