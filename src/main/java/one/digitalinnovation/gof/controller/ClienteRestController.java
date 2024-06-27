package one.digitalinnovation.gof.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.service.ClienteService;
import org.springframework.http.HttpStatus;


/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações (Banco de Dados H2 e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 * 
 * @author falvojr
 */
@RestController
@RequestMapping("clientes")
@Api(value = "Cliente API", tags = "Cliente")
public class ClienteRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteRestController.class);

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	@ApiOperation(value = "Busca todos os clientes")
	public ResponseEntity<Iterable<Cliente>> buscarTodos() {
		LOGGER.info("Buscando todos os clientes");
		return ResponseEntity.ok(clienteService.buscarTodos());
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Busca um cliente por ID")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
		try {
			Cliente cliente = clienteService.buscarPorId(id);
			return ResponseEntity.ok(cliente);
		} catch (Exception e) {
			LOGGER.error("Erro ao buscar cliente com ID {}", id, e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado", e);
		}
	}

	@PostMapping
	@ApiOperation(value = "Insere um novo cliente")
	public ResponseEntity<Cliente> inserir(@Valid @RequestBody Cliente cliente) {
		LOGGER.info("Inserindo cliente: {}", cliente);
		clienteService.inserir(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza um cliente existente")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
		try {
			clienteService.atualizar(id, cliente);
			return ResponseEntity.ok(cliente);
		} catch (Exception e) {
			LOGGER.error("Erro ao atualizar cliente com ID {}", id, e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado", e);
		}
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleta um cliente por ID")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		try {
			clienteService.deletar(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			LOGGER.error("Erro ao deletar cliente com ID {}", id, e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado", e);
		}
	}
}
