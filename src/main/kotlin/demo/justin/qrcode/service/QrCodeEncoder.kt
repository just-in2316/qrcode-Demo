package demo.justin.qrcode.service

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import demo.justin.qrcode.model.*
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

@Component
class QrCodeEncoder {

    val log = LoggerFactory.getLogger(QrCodeEncoder::class.java)

    val size = 250
    val fileType = "png"

    fun generateQrCodeUrl(qrCodeUrl: QrCodeUrl?): QrCodeProcessingResult? {
        val extracted = QrCodeUrlParser(qrCodeUrl!!).parse()
        return generateImageAsBase64(extracted)
    }

    fun generateQrCodeText(qrCodeText: QrCodeText?): QrCodeProcessingResult? {
        val extracted = QrCodeTextParser(qrCodeText!!).parse()
        return generateImageAsBase64(extracted)
    }

    private fun generateImageAsBase64(textToBeEncoded: String): QrCodeProcessingResult? {
        val result = QrCodeProcessingResult()
        result.encodedText = textToBeEncoded
        try {
            val qrCodeWriter = QRCodeWriter()
            val bitMatrix = qrCodeWriter.encode(textToBeEncoded, BarcodeFormat.QR_CODE, size, size, createHintMap())
            val width = bitMatrix.width
            val height = bitMatrix.height
            val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
            image.createGraphics()
            val graphics = image.graphics as Graphics2D
            graphics.color = Color.WHITE
            graphics.fillRect(0, 0, width, height)
            graphics.color = Color.BLACK
            for (i in 0 until width) {
                for (j in 0 until width) {
                    if (bitMatrix[i, j]) {
                        graphics.fillRect(i, j, 1, 1)
                    }
                }
            }
            val fileName = UUID.randomUUID().toString()
            val myFile = File.createTempFile(fileName, ".$fileType")
            ImageIO.write(image, fileType, myFile)
            val bytes = FileUtils.readFileToByteArray(myFile)
            val imageText = "data:image/png;base64,${Base64Utils.encodeToString(bytes)}"
            result.image = imageText
        } catch (e: Exception) {
            val msg = "Processing QR code failed."
            log.error(msg, e)
            result.errorMessage = msg
        }
        log.info("QR Code for text {} was successfully created.", textToBeEncoded)
        result.successMessage = "QR Code was successfully created."
        return result
    }


    private fun createHintMap(): MutableMap<EncodeHintType, Any?> {
        val hintMap: MutableMap<EncodeHintType, Any?> = EnumMap(EncodeHintType::class.java)
        hintMap[EncodeHintType.CHARACTER_SET] = "UTF-8"
        hintMap[EncodeHintType.MARGIN] = 1
        hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L
        return hintMap
    }
}