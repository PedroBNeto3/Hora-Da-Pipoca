package br.usjt.ads.arqdes.model.service;

import java.io.IOException;
import java.util.ArrayList;

import br.usjt.ads.arqdes.model.dao.GeneroDAO;
import br.usjt.ads.arqdes.model.entity.Genero;

public class GeneroService
{
	private GeneroDAO dao;

	public GeneroService()
	{
		this.dao = new GeneroDAO();
	}

	public Genero buscarGenero(int iId) throws IOException
	{
		return dao.buscarGenero(iId);
	}

	public ArrayList<Genero> listarGeneros() throws IOException
	{
		return dao.listarGeneros();
	}
}
