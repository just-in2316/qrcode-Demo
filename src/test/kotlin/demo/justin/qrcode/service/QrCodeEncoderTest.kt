package demo.justin.qrcode.service

import demo.justin.qrcode.model.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class QrCodeEncoderTest(@Autowired val qrCodeEncoder: QrCodeEncoder) {

    @Test
    fun generateQrCodeUrl() {
        val qrCodeUrl = QrCodeUrl("http://www.google.com")
        val result = qrCodeEncoder.generateQrCodeUrl(qrCodeUrl)
        assertNotNull(result)
        assertTrue(result!!.isSuccessfull())
        assertNotNull(result.image)
        assertNotNull(result.encodedText)
        assertFalse(result.hasError())
    }

    @Test
    fun generateQrCodeText() {
        val qrCodeText = QrCodeText("http://www.google.com")
        val result = qrCodeEncoder.generateQrCodeText(qrCodeText)
        assertNotNull(result)
        assertTrue(result!!.isSuccessfull())
        assertNotNull(result.image)
        assertNotNull(result.encodedText)
        assertFalse(result.hasError())
    }


}