package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.usjt.ads.arqdes.model.entity.Genero;

public class GeneroDAO
{

	public Genero buscarGenero(int iId) throws IOException
	{
		Genero genero = null;
		String sSQL = " SELECT id, nome FROM genero WHERE id = ? ";

		try ( Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(sSQL); )
		{
			pst.setInt(1, iId);

			try ( ResultSet resultSet = pst.executeQuery(); )
			{
				if ( resultSet.next() )
				{
					genero = new Genero();
					genero.setId(iId);
					genero.setNome(resultSet.getString("nome"));
				}
			}
		}
		catch ( SQLException err )
		{
			err.printStackTrace();
			throw new IOException(err);
		}
		
		return genero;
	}

	public ArrayList<Genero> listarGeneros() throws IOException
	{
		ArrayList<Genero> generos = new ArrayList<>();
		String sSQL = "select id, nome from genero order by nome";

		try ( Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(sSQL);
				ResultSet resultSet = pst.executeQuery(); )
		{
			while ( resultSet.next() )
			{
				Genero genero = new Genero();
				genero.setId(resultSet.getInt("id"));
				genero.setNome(resultSet.getString("nome"));
				generos.add(genero);
			}
		}
		catch ( SQLException err )
		{
			err.printStackTrace();
			throw new IOException(err);
		}
		
		return generos;
	}
}
