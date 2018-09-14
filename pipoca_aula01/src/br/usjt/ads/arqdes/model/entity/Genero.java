package br.usjt.ads.arqdes.model.entity;

public class Genero
{
	private int id;
	private String nome;

	public int getId()
	{
		return id;
	}

	public void setId(int idParam)
	{
		id = idParam;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nomeParam)
	{
		nome = nomeParam;
	}

	@Override
	public String toString()
	{
		return "Genero [id=" + id + ", nome=" + nome + "]";
	}
}
