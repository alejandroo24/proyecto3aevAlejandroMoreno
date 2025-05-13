package dataAccess;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

    public class XMLManager {

        /**
         * Escribe un objeto en un archivo XML.
         * @param <T> Tipo de objeto a guardar.
         * @param objeto Objeto que se desea guardar en formato XML.
         * @param fileName Nombre del archivo donde se guardará el objeto.
         * @return True si el objeto se guardó correctamente, false en caso contrario.
         */
        public static <T> boolean writeXML(T objeto, String fileName) {
            boolean result = false;
            try {
                // Crea el contexto JAXB para el tipo de objeto que se va a serializar
                JAXBContext context = JAXBContext.newInstance(objeto.getClass());

                // Crea el marshaller para convertir el objeto a XML
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");


                marshaller.marshal(objeto, new File(fileName));
                result = true;
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            return result;
        }

        /**
         * Lee un objeto desde un archivo XML.
         * @param <T> Tipo de objeto a leer.
         * @param objeto Objeto que se usará como referencia para el tipo de datos.
         * @param fileName Nombre del archivo XML desde donde se leerá el objeto.
         * @return El objeto leído desde el archivo XML.
         */
        public static <T> T readXML(T objeto, String fileName) {
            T result = null;
            File file = new File(fileName);
            try {
                JAXBContext context = JAXBContext.newInstance(objeto.getClass());

                Unmarshaller unmarshaller = context.createUnmarshaller();
                result = (T) unmarshaller.unmarshal(file);
                // Se hace un casteo porque todas las clases heredan de Object.
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            return result;
        }
    }
