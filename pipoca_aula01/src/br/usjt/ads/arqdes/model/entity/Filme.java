package br.usjt.ads.arqdes.model.entity;

import java.util.Date;

public class Filme
{
	private int id;
	private String titulo;
	private String descricao;
	private double popularidade;
	private Date dataLancamento;
	private String posterPath;
	private String diretor;
	private Genero genero;

	public int getId()
	{
		return id;
	}

	public void setId(int idParam)
	{
		id = idParam;
	}

	public String getTitulo()
	{
		return titulo;
	}

	public void setTitulo(String tituloParam)
	{
		titulo = tituloParam;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricaoParam)
	{
		descricao = descricaoParam;
	}

	public double getPopularidade()
	{
		return popularidade;
	}

	public void setPopularidade(double popularidadeParam)
	{
		popularidade = popularidadeParam;
	}

	public Date getDataLancamento()
	{
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamentoParam)
	{
		dataLancamento = dataLancamentoParam;
	}

	public String getPosterPath()
	{
		return posterPath;
	}

	public void setPosterPath(String posterPathParam)
	{
		posterPath = posterPathParam;
	}

	public String getDiretor()
	{
		return diretor;
	}

	public void setDiretor(String diretorParam)
	{
		diretor = diretorParam;
	}

	public Genero getGenero()
	{
		return genero;
	}

	public void setGenero(Genero generoParam)
	{
		genero = generoParam;
	}

	@Override
	public String toString()
	{
		return "Filme [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", popularidade=" + popularidade
				+ ", dataLancamento=" + dataLancamento + ", posterPath=" + posterPath + ", diretor=" + diretor
				+ ", genero=" + genero + "]";
	}
}