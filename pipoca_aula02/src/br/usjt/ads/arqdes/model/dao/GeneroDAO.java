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

	public Genero buscarGenero(int id) throws IOException
	{
		Genero genero = null;
		String sSQL = "";
		
		sSQL = "select id, nome from genero where id=?";

		try ( Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(sSQL); )
		{
			pst.setInt(1, id);
			
			try ( ResultSet resultSet = pst.executeQuery(); )
			{
				if ( resultSet.next() )
				{
					genero = new Genero();
					genero.setId(id);
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
		ArrayList<Genero> arrGeneros = new ArrayList<>();
		String sSQL = "";

		sSQL = "select id, nome from genero order by nome";
		
		arrGeneros = new ArrayList<>();

		try ( Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(sSQL);
				ResultSet resultSet = pst.executeQuery(); )
		{

			while ( resultSet.next() )
			{
				Genero genero = new Genero();
				genero.setId(resultSet.getInt("id"));
				genero.setNome(resultSet.getString("nome"));
				
				arrGeneros.add(genero);
			}
		}
		catch ( SQLException err )
		{
			err.printStackTrace();
			throw new IOException(err);
		}
		
		return arrGeneros;
	}
}
