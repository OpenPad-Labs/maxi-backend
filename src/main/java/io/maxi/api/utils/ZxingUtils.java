package io.maxi.api.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * 二维码
 */
public class ZxingUtils {
	private static final Logger logger = LoggerFactory.getLogger(ZxingUtils.class);

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	private static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	/**
	 * 将内容contents生成长宽均为width的图片，图片路径由imgPath指定
	 */
	public static File getQRCodeImge(String contents, int width, String imgPath) {
		return getQRCodeImge(contents, width, width, imgPath);
	}

	/**
	 * 将内容contents生成长为width，宽为width的图片，图片路径由imgPath指定
	 */
	public static File getQRCodeImge(String contents, int width, int height, String imgPath) {
		try {
			Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
			hints.put(EncodeHintType.CHARACTER_SET, "UTF8");

			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);

			File imageFile = new File(imgPath);
			writeToFile(bitMatrix, "png", imageFile);

			return imageFile;

		} catch (Exception e) {
			logger.error("create QR code error!", e);
			return null;
		}
	}

	/**
	 * 生成二维码，返回base64串
	 * 
	 * @param contents
	 * @param width
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String getQRCodeImge(String contents, int width) {
		try {
			Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
			hints.put(EncodeHintType.CHARACTER_SET, "UTF8");

			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, width, hints);

			BufferedImage image = toBufferedImage(bitMatrix);
			ByteArrayOutputStream os = new ByteArrayOutputStream();// 新建流。
			ImageIO.write(image, "png", os);
			byte b[] = os.toByteArray();
			return new BASE64Encoder().encode(b);
		} catch (Exception e) {
			logger.error("create QR code error!", e);
			return null;
		}
	}

	/**
	 *
	 * 获取base64编码的二维码
	 *
	 * @param address
	 * @return String
	 *
	 * @author mfXing
	 */
	public static String getQRCode(String address) {
		String QRStr = ZxingUtils.getQRCodeImge(address, 200);
		return "data:image/png;base64," + QRStr;
	}

	public static void main(String[] args) {
		String symbol = "15fSn8Ufu5rf7Xeou6pdYVn1gqMiSuDULV";
		String QRStr = ZxingUtils.getQRCode(symbol);
		System.out.println(QRStr);
	}
}