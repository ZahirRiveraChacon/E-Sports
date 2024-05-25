package Controlador.ControladoresBD;

import Modelo.Enfrentamiento;
import Modelo.Equipo;
import Modelo.Jornada;
import Modelo.Juego;

import java.sql.*;

// TODO : JAVADOC
public class ControladorEnfrentamientos {

    private Connection con;
    private Enfrentamiento e;

    public ControladorEnfrentamientos(Connection con) {
        this.con = con;
    }

    public void borrarEnfrentamiento(int idEnfrentamiento) throws Exception{

        PreparedStatement sentenciaPre = null;
        try {
            String plantilla = "DELETE FROM enfrentamientos WHERE id_enfrentamiento = ?";
            sentenciaPre = con.prepareStatement(plantilla);
            sentenciaPre.setInt(1, idEnfrentamiento);

            int n = sentenciaPre.executeUpdate();
            if (n != 1) {
                throw new Exception("No se ha podido eliminar ningún enfrentamiento.");
            }
        }
        catch (SQLIntegrityConstraintViolationException e) {
            throw new Exception("No se puede eliminar el enfrentamiento debido a restricciones de integridad.", e);
        }
        catch (Exception e) {
            throw new Exception("Error al borrar el enfrentamiento.", e);
        }
        finally {
            if (sentenciaPre != null) {
                try {
                    sentenciaPre.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public Enfrentamiento buscarEnfrentamiento(int idEnfrentamiento) throws Exception {

        try {
            String plantilla = "SELECT * FROM enfrentamientos WHERE id_enfrentamiento = ?";
            PreparedStatement sentenciaPre = con.prepareStatement(plantilla);
            sentenciaPre.setInt(1,idEnfrentamiento);

            ResultSet rs = sentenciaPre.executeQuery();
            if (rs.next()) {
                e = new Enfrentamiento();
                e.setIdEnfrentamiento(idEnfrentamiento);
                e.setHoraEnfrentamiento(rs.getTimestamp("hora_enfrentamiento"));
                e.setResultado1(rs.getInt("resultado1"));
                e.setResultado2(rs.getInt("resultado2"));
                Equipo equipo1 = new Equipo();
                equipo1.setIdEquipo(rs.getInt("id_equipo1"));
                e.setEquipo1(equipo1);
                Equipo equipo2 = new Equipo();
                equipo2.setIdEquipo(rs.getInt("id_equipo2"));
                e.setEquipo2(equipo2);
                Jornada jornada = new Jornada();
                jornada.setIdJornada(rs.getInt("id_jornada"));
                e.setJornada(jornada);
            }
            else {
                throw new Exception("No hay ningún enfrentamiento registrado con ese ID.");
            }
            sentenciaPre.close();
            return e;
        }
        catch (Exception e) {
            throw new Exception("Error al buscar enfrentamiento.");
        }

    }

    public void modificarEnfrentamiento(Enfrentamiento e) throws Exception{

        //

    }

}
