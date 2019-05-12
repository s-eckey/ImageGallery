package de.eckey.imagegallery.email;

import de.eckey.imagegallery.data.Image;
import de.eckey.imagegallery.property.EMailProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.*;

public class EMailReceiver {
    private Logger logger = LoggerFactory.getLogger(EMailReceiver.class);

    @Autowired
    private EMailProperties mailProperties;

    public List<Image> getNewImages() {
        logger.debug("loading new images from mail-address {} out of folder {}", mailProperties.getEmail(), mailProperties.getFolder());
        final List<Image> newImages = new ArrayList<>();
        try {
            final Store store = connect();

            final Folder folder = store.getFolder(mailProperties.getFolder());
            folder.open(Folder.READ_WRITE);

            final FlagTerm unseenFlagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            final Message[] unreadMessages = folder.search(unseenFlagTerm);

            logger.debug("new messages count: {}", unreadMessages.length);
            Arrays.asList(unreadMessages).forEach(message -> {
                try {
                    newImages.addAll(getImagesFromMessage(message));
                    message.setFlag(Flags.Flag.SEEN, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            folder.close(true);
            store.close();

        } catch (Exception e) {
            logger.error("Error occurred while fetching e-mails", e);
        }
        return newImages;
    }

    private List<Image> getImagesFromMessage(Message message) throws MessagingException, IOException {
        logger.debug("reading images from message Subject: {}", message.getSubject());
        final List<Image> newImages = new ArrayList<>();
        if (hasAttachments(message)) {
            final Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                final MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                if (StringUtils.isNotBlank(bodyPart.getDisposition()) && StringUtils.equalsAnyIgnoreCase(bodyPart.getDisposition(), Part.ATTACHMENT, Part.INLINE)) {

                    final String fileName = bodyPart.getFileName();

                    if (endsWithIgnoreCase(fileName, ".png")
                            || endsWithIgnoreCase(fileName, ".jpg")
                            || endsWithIgnoreCase(fileName, ".jpeg")
                            || endsWithIgnoreCase(fileName, ".tif")
                            || endsWithIgnoreCase(fileName, ".gif")) { // only process images

                        logger.debug("adding new attachment-image with name '{}' and size '{}kb' ...", fileName, bodyPart.getSize() / 1000);

                        //final File imageFile = File.createTempFile("imageGallery", null);
                        //logger.debug("created temp file: {}", imageFile.getAbsolutePath());
                        //bodyPart.saveFile(imageFile);

                        final javaxt.io.Image image = new javaxt.io.Image(bodyPart.getInputStream());
                        image.rotate();

                        newImages.add(new Image(substringBeforeLast(fileName, "."), image.getByteArray(), substringAfterLast(fileName, ".")));
                        logger.debug("done");
                    } else {
                        logger.debug("attachment not an image {}", fileName);
                    }
                }
            }
        } else {
            logger.debug("message {} has no attachments", message.getSubject());
        }
        return newImages;
    }

    private boolean hasAttachments(final Message msg) {
        try {
            if (msg.isMimeType("multipart/mixed")) {
                final Multipart mp = (Multipart) msg.getContent();
                if (mp.getCount() > 1) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("Could not check if Message has attachments", e);
        }
        return false;
    }

    private Store connect() throws MessagingException {
        final Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.fetchsize", "2500000"); // 2.5MB

        final Session session = Session.getInstance(props);

        final Store store = session.getStore("imaps");
        store.connect("smtp.gmail.com", mailProperties.getEmail(), mailProperties.getPassword());
        return store;
    }
}
