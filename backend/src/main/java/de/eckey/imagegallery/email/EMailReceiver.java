package de.eckey.imagegallery.email;

import de.eckey.imagegallery.data.Image;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;

public class EMailReceiver {
    Logger logger = LoggerFactory.getLogger(EMailReceiver.class);

    @Autowired
    private EMailProperties mailProperties;

    public List<Image> getNewImages() {
        logger.debug("loading new images from mail {} out of folder {}", mailProperties.getEmail(), mailProperties.getFolder());
        final List<Image> newImages = new ArrayList();
        try {
            final Store store = connect();

            final Folder folder = store.getFolder(mailProperties.getFolder());
            folder.open(Folder.READ_WRITE);

            final FlagTerm unseenFlagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            final Message[] unreadMessages = folder.search(unseenFlagTerm);

            logger.debug("new messages: {}", unreadMessages.length);
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
            e.printStackTrace();
        }
        return newImages;
    }

    private List<Image> getImagesFromMessage(Message message) throws MessagingException, IOException {
        logger.debug("reading images from message Subject: {}", message.getSubject());
        final List<Image> newImages = new ArrayList<>();
        if (message.isMimeType("multipart/*")) {
            final Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                final BodyPart bodyPart = multipart.getBodyPart(i);
                if (StringUtils.isNotBlank(bodyPart.getDisposition()) && StringUtils.equalsAnyIgnoreCase(bodyPart.getDisposition(), Part.ATTACHMENT, Part.INLINE)) {
                    // if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                    final String fileName = bodyPart.getFileName();
                    if (endsWithIgnoreCase(fileName, ".png")
                            || endsWithIgnoreCase(fileName, ".jpg")
                            || endsWithIgnoreCase(fileName, ".jpeg")
                            || endsWithIgnoreCase(fileName, ".tif")
                            || endsWithIgnoreCase(fileName, ".gif")) { // only process images
                        logger.debug("adding new attachment-image with name: {}", fileName);
                        final Image newImage = new Image(fileName, IOUtils.toByteArray(bodyPart.getInputStream()), StringUtils.substringAfterLast(fileName, "."));
                        newImages.add(newImage);
                    } else {
                        logger.debug("attachment not an image {}", fileName);
                    }
                }
            }
        } else {
            logger.debug("message is not MimeType 'multipart/*'. Subject: {}", message.getSubject());
        }
        return newImages;
    }

    private Store connect() throws MessagingException {
        final Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");

        final Session session = Session.getDefaultInstance(props, null);

        final Store store = session.getStore("imaps");
        store.connect("smtp.gmail.com", mailProperties.getEmail(), mailProperties.getPassword());
        return store;
    }
}
