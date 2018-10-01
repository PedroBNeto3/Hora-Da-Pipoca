package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.usjt.ads.arqdes.model.entity.Genero;

@Repository
public class GeneroDAO
{

	Connection conn;

	@Autowired
	public GeneroDAO(DataSource ds) throws IOException
	{
		try
		{
			conn = ds.getConnection();
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}
	}

	public Genero buscarGenero(int id) throws IOException
	{
		Genero genero = null;
		String sSQL = "";
		sSQL = "select id, nome from genero where id=?";

		try (PreparedStatement prepStatement = conn.prepareStatement(sSQL);)
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

		try (PreparedStatement prepStatement = conn.prepareStatement(sSQL);
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
		ArrayList<Genero> arrGeneros = new ArrayList<>();
		Genero genero = null;
		String sSQL = "";

		sSQL  = "select distinct g.id, g.nome, f.id_genero from genero g, filme f where g.id = f.id_genero";

		try (PreparedStatement prepStatement = conn.prepareStatement(sSQL);
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
