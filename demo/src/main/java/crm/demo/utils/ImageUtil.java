package crm.demo.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import crm.demo.controllers.CrmController;

public class ImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(CrmController.class);

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4096];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4096];
        try {
            while (!inflater.finished()) {
                // int count = inflater.inflate(tmp);
                // outputStream.write(tmp, 0, count);
                // logger.info("Log message: lặp vô hạn");
                int count = inflater.inflate(tmp);
                if (count == 0 && inflater.needsInput()) {
                    // Đảm bảo cung cấp thêm dữ liệu cho Inflater nếu cần
                    throw new RuntimeException("Inflater needs more input");
                }
                outputStream.write(tmp, 0, count);
                inflater.end();
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }
}
