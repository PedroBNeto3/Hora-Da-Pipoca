package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.usjt.ads.arqdes.model.entity.Genero;

public class GeneroDAO {

	public Genero buscarGenero(int id) throws IOException
	{
		Genero genero = null;
		String sSQL = "select id, nome from genero where id=?";

		try (Connection conn = ConnectionFactory.getConnection(); 
				PreparedStatement prepStatement = conn.prepareStatement(sSQL);)
		{
			prepStatement.setInt(1, id);

			try (ResultSet resultSet = prepStatement.executeQuery();)
			{
				if (resultSet.next())
				{
					genero = new Genero();
					genero.setId(id);
					genero.setNome(resultSet.getString("nome"));
				}
			}
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}

		return genero;
	}

	public ArrayList<Genero> listarGeneros() throws IOException
	{
		ArrayList<Genero> arrGeneros = null;
		Genero genero = null;
		String sSQL = "";

		arrGeneros = new ArrayList<>();
		sSQL = "select id, nome from genero order by nome";

		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement prepStatement = conn.prepareStatement(sSQL);
				ResultSet resultSet = prepStatement.executeQuery();)
		{
			while (resultSet.next())
			{
				genero = new Genero();
				genero.setId(resultSet.getInt("id"));
				genero.setNome(resultSet.getString("nome"));
				arrGeneros.add(genero);
			}
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}

		return arrGeneros;
	}
	
	public ArrayList<Genero> buscarGenerosFilmes() throws IOException
	{
		ArrayList<Genero> arrGeneros = null;
		Genero genero = null;
		String sSQL = "";

		arrGeneros = new ArrayList<>();
		sSQL = "select distinct g.id, g.nome, f.id_genero from genero g, filme f where g.id = f.id_genero;";

		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement prepStatement = conn.prepareStatement(sSQL);
				ResultSet resultSet = prepStatement.executeQuery();)
		{
			while (resultSet.next())
			{
				genero = new Genero();
				genero.setId(resultSet.getInt("g.id"));
				genero.setNome(resultSet.getString("g.nome"));
				arrGeneros.add(genero);
			}
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}

		return arrGeneros;
	}
}
