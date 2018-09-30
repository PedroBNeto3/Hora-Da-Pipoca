package br.usjt.ads.arqdes.model.service;

import java.io.IOException;
import java.util.ArrayList;

import br.usjt.ads.arqdes.model.dao.GeneroDAO;
import br.usjt.ads.arqdes.model.entity.Genero;

public class GeneroService
{
	private GeneroDAO generoDAO;
	
	public GeneroService()
	{
		generoDAO = new GeneroDAO();
	}
	
	public Genero buscarGenero(int iId) throws IOException
	{
		return generoDAO.buscarGenero(iId);
	}
	
	public ArrayList<Genero> listarGeneros() throws IOException
	{
		return generoDAO.listarGeneros();
	}
	
	public ArrayList<Genero> buscarGenerosFilmes() throws IOException
	{
		return generoDAO.buscarGenerosFilmes();
	}
}
