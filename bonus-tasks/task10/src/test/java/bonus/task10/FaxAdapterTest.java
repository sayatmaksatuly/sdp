package bonus.task10;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FaxAdapterTest {
    @Test
    void documentIsRasterizedAndAcknowledgementDecodedThroughTargetInterface() {
        AncientFaxMachine fax = new AncientFaxMachine();
        DocumentSender sender = new FaxAdapter(fax, new DocumentRasterizer());
        SendResult result = sender.send(new Document("Hello fax"), "+77001234567");
        assertTrue(result.successful());
        assertEquals("1987", result.confirmationCode());
        assertEquals("Fax delivered", result.detail());
        assertEquals("+77001234567", fax.lastNumber());
        assertEquals(640 * 512, fax.lastPixels().length);
        int inkPixels = 0;
        for (byte pixel : fax.lastPixels()) {
            if (Byte.toUnsignedInt(pixel) < 255) {
                inkPixels++;
            }
        }
        assertTrue(inkPixels > 0);
        assertTrue(inkPixels < fax.lastPixels().length);
        byte[] copy = fax.lastPixels();
        copy[0] = 0;
        assertEquals(255, Byte.toUnsignedInt(fax.lastPixels()[0]));
    }

    @Test
    void busyAndUnknownNoiseAreMeaningfulFailures() {
        SendResult busy = new FaxAdapter(new AncientFaxMachine("krr::BUSY::shh"), new DocumentRasterizer())
                .send(new Document("hello"), "123");
        assertFalse(busy.successful());
        assertEquals("Recipient line busy", busy.detail());
        for (String noise : new String[] {"", "krrr", "NOTACK:123", "ACK:bad"}) {
            SendResult result = new FaxAdapter(new AncientFaxMachine(noise), new DocumentRasterizer())
                    .send(new Document("hello"), "123");
            assertFalse(result.successful());
            assertEquals("", result.confirmationCode());
        }
    }

    @Test
    void invalidDocumentRecipientAndDependenciesAreRejected() {
        AncientFaxMachine fax = new AncientFaxMachine();
        DocumentSender sender = new FaxAdapter(fax, new DocumentRasterizer());
        assertThrows(IllegalArgumentException.class, () -> new Document(" "));
        assertThrows(IllegalArgumentException.class, () -> new Document(null));
        assertThrows(IllegalArgumentException.class, () -> new Document("x".repeat(2001)));
        assertThrows(IllegalArgumentException.class, () -> sender.send(new Document("a"), "email"));
        assertThrows(NullPointerException.class, () -> sender.send(null, "123"));
        assertThrows(NullPointerException.class, () -> new FaxAdapter(null, new DocumentRasterizer()));
        assertThrows(NullPointerException.class, () -> new FaxAdapter(fax, null));
        assertEquals(0, fax.lastPixels().length);
    }

    @Test
    void maximumDocumentFitsInFixedRaster() {
        byte[] pixels = new DocumentRasterizer().rasterize(new Document("x".repeat(2000)));
        assertEquals(DocumentRasterizer.WIDTH * DocumentRasterizer.HEIGHT, pixels.length);
        assertEquals(255, Byte.toUnsignedInt(pixels[pixels.length - 1]));
    }
}
