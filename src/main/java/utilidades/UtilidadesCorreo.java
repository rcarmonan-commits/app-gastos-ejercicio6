package utilidades;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import modelo.CRUDConfiguracionSMTP;
import modelo.ConfiguracionSMTP;

public class UtilidadesCorreo {

    public static void enviarCorreo(String destinatario, String asunto, String mensajeCuerpo) throws MessagingException {
        
        CRUDConfiguracionSMTP crudConfig = new CRUDConfiguracionSMTP();
        ConfiguracionSMTP config = crudConfig.obtenerConfiguracion();
        
        if (config == null) {
            throw new MessagingException("No se encontró configuración SMTP en la base de datos.");
        }

        final String username = config.getUsuario();
        final String password = config.getClave();
        String host = config.getHost();
        String puerto = config.getPuerto();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", puerto);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        
        // Enviar como HTML
        message.setContent(mensajeCuerpo, "text/html; charset=utf-8");

        Transport.send(message);
    }
}
