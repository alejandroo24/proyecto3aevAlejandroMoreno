package DataBase;

import dataAccess.XMLManager;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="connection")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectionProperties implements Serializable {
    private final String file = "connectionProperties.xml";
    private static final long serialVersionUID = 1L;
    @XmlElement
    private  String server;
    @XmlElement
    private String port;
    @XmlElement
    private String database;
    @XmlElement
    private String user;
    @XmlElement
    private String password;

    public ConnectionProperties() {
    }

    public ConnectionProperties(String server, String port, String database, String user, String password) {
        this.server = server;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        guardarConfiguracion();
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getURL(){
        return "jdbc:mysql://" + server + ":" + port + "/" + database;
    }

    public static String getFile() {
        return "connectionProperties.xml";
    }

    public void guardarConfiguracion() {
        boolean resultado = XMLManager.writeXML(this, file);
        if (resultado) {
            System.out.println("Archivo de configuración creado correctamente: " + file);
        } else {
            System.out.println("Error al crear el archivo de configuración.");
        }
    }
}
