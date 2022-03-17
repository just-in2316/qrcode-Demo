package demo.justin.qrcode.web

import demo.justin.qrcode.model.*
import demo.justin.qrcode.utils.TestUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.validation.BindingResult
import org.springframework.validation.support.BindingAwareModelMap

@SpringBootTest
internal class QrCodeControllerTest(@Autowired val qrCodeController: QrCodeController) {

    @Test
    fun thatIndexPasses() {
        val expected = "index"
        val actual: String? = qrCodeController.index(TestUtils.createModel())
        assertNotNull(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun thatQrCodeUrlPasses() {
        val expected = "qr-code-url"
        val model: BindingAwareModelMap = TestUtils.createModel()
        val actual: String? = qrCodeController.qrCodeUrl(model)
        assertNotNull(actual)
        assertEquals(expected, actual)
        assertNotNull(model["qrCodeUrl"])
    }

    @Test
    fun thatProcessQrCodeUrlPasses() {
        val expected = "result"
        val qrCodeUrl = QrCodeUrl("http://www.google.com")
        val model: BindingAwareModelMap = TestUtils.createModel()
        val bindingResult: BindingResult = TestUtils.createBindingResult(qrCodeUrl)
        val actual: String? = qrCodeController.processUrl(model, qrCodeUrl, bindingResult)
        assertNotNull(actual)
        assertEquals(expected, actual)
        assertNotNull(model["image"])
        assertNull(model["qrCodeUrl"])
    }

}