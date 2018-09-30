package br.usjt.ads.arqdes.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory
{
	static
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException err)
		{
			err.printStackTrace();
			throw new RuntimeException(err);
		}
	}

	public static Connection getConnection() throws IOException
	{
		try
		{
			return DriverManager.getConnection("jdbc:mysql://localhost/pipocadb?"
					+ "user=Alunos&password=alunos&useSSL=false&allowMultiQueries=true");
		}
		catch (SQLException err)
		{
			err.printStackTrace();
			throw new IOException(err);
		}
	}

}
