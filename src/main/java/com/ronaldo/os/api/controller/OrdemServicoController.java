package com.ronaldo.os.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ronaldo.os.api.model.OrdemServicoInput;
import com.ronaldo.os.api.model.OrdemServicoModelDto;
import com.ronaldo.os.domain.model.OrdemServico;
import com.ronaldo.os.domain.repository.OrdemServicoRepository;
import com.ronaldo.os.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	//boilerPlate java
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModelDto criar(@Valid @RequestBody OrdemServicoInput ordemServicoIput) {
		OrdemServico ordemServico = toEntity(ordemServicoIput);
		return toModel(gestaoOrdemServico.criar(ordemServico));
	}
	
	@GetMapping
	public List<OrdemServicoModelDto> listar(){
		return toCollectionModel(ordemServicoRepository.findAll());
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServico.finalizar(ordemServicoId);
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModelDto> buscar(@PathVariable Long ordemServicoId){
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
		
		if(ordemServico.isPresent()) {			
			OrdemServicoModelDto ordemServicoModel = toModel(ordemServico.get());
			return ResponseEntity.ok(ordemServicoModel);
		}
		return ResponseEntity.notFound().build();
	}
	/**
	 * modelMapper ajuda a não repetir cotigo
	 * Se não tem que transformar manualmente OrdemServico em OrdemServicoModelDto
	 */
	private OrdemServicoModelDto toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoModelDto.class);
	}
	/**
	 * Converte uma lista de OrdemServico em uma lista de OrdemServicoModelDto
	 * @param ordensServico
	 * @return
	 */
	private List<OrdemServicoModelDto> toCollectionModel(List<OrdemServico> ordensServico){
		return ordensServico.stream().map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());
	}
	/**
	 * 
	 */
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
}













