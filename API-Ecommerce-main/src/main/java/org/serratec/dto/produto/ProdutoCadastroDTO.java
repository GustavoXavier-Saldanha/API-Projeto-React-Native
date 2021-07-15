package org.serratec.dto.produto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.tomcat.util.codec.binary.Base64;
import org.serratec.exception.CategoriaException;
import org.serratec.models.Categoria;
import org.serratec.models.Produto;
import org.serratec.repository.CategoriaRepository;

public class ProdutoCadastroDTO {
	@NotNull
	@NotBlank
	private String codigo;
	
	@NotNull
	@NotBlank
	private String nome;
	
	@NotNull
	@NotBlank
	private String descricao;
	
	@NotNull
	@NotBlank
	@Min(value = 0)
	private Double preco;
	
	@NotNull
	@NotBlank
	@Min(value = 0)
	private Integer quantidadeEstoque;
	
	@NotNull
	@NotBlank
	private Categoria categoria;
	
	@NotNull
	@NotBlank
	private LocalDateTime dataCadastro;
	
	private String imgBase64;
	
	public Produto toProduto(CategoriaRepository categoriaRepository) throws CategoriaException {
		Produto p = new Produto();
		p.setCodigo(this.codigo);
		p.setNome(this.nome);
		p.setDescricao(this.descricao);
		p.setPreco(this.preco);
		p.setQuantidadeEstoque(this.quantidadeEstoque);
		
		Categoria categoria = categoriaRepository.findById(this.categoria.getId()).orElseThrow(() -> new CategoriaException("Categoria n�o encontrada."));
		p.setCategoria(categoria);
		p.setDataCadastro(LocalDateTime.now());
		
		if(imgBase64 != null) {
			byte[] capa = Base64.decodeBase64(imgBase64);
			p.setImagemBase64(capa);	
		}
		
		return p;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public String getImgBase64() {
		return imgBase64;
	}
	
	
}
