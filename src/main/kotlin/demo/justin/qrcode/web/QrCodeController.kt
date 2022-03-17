package demo.justin.qrcode.web

import demo.justin.qrcode.config.ApplicationProperties
import demo.justin.qrcode.model.*
import demo.justin.qrcode.service.QrCodeEncoder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class QrCodeController(
    val applicationProperties: ApplicationProperties,
    val qrCodeEncoder: QrCodeEncoder
) {
    val pageIndex = "index"
    val pageResult = "result"
    val pageQrCodeUrl = "qr-code-url"
    val pageQrCodeText = "qr-code-text"
    val pageQrCodeSms = "qr-code-sms"
    val qrCodeImage = "image"
    val textToBeEncoded = "text"
    val successMessage = "successMessage"
    val errorMessage = "errorMessage"

    val log = LoggerFactory.getLogger(QrCodeController::class.java)

    @GetMapping(value = ["/", "/index"])
    fun index(model: Model): String? {
        addCommonModelAttributes(model)
        return pageIndex
    }

    @GetMapping("/qr-code-url")
    fun qrCodeUrl(model: Model): String? {
        addCommonModelAttributes(model)
        model.addAttribute("qrCodeUrl", QrCodeUrl(""))
        return pageQrCodeText
    }

    @PostMapping("/process/url")
    fun processUrl(
        model: Model,
        @ModelAttribute("qrCodeUrl") @Valid qrCodeUrl: QrCodeUrl?,
        bindingResult: BindingResult
    ): String? {
        addCommonModelAttributes(model)
        if (!bindingResult.hasErrors()) {
            log.info("generate QR Code for Url {}", qrCodeUrl!!.urlToBeEncoded)
            val result = qrCodeEncoder.generateQrCodeUrl(qrCodeUrl)
            addResultModelAttributes(model, result!!)
            return pageResult
        }
        return pageQrCodeUrl
    }

    @GetMapping("/qr-code-text")
    fun qrCodeText(model: Model): String? {
        addCommonModelAttributes(model)
        model.addAttribute("qrCodeText", QrCodeText(""))
        return pageQrCodeText
    }

    @PostMapping("/process/text")
    fun processText(
        model: Model,
        @ModelAttribute("qrCodeText") @Valid qrCodeText: QrCodeText?,
        bindingResult: BindingResult
    ): String? {
        addCommonModelAttributes(model)
        if (!bindingResult.hasErrors()) {
            log.info("generate QR Code for Url {}", qrCodeText!!.textToBeEncoded)
            val result = qrCodeEncoder.generateQrCodeText(qrCodeText)
            addResultModelAttributes(model, result!!)
            return pageResult
        }
        return pageQrCodeText
    }

    private fun addCommonModelAttributes(model: Model) {
        model.addAttribute("titleMessage", applicationProperties.title)
        model.addAttribute("appInfo", applicationProperties.appInfo)
    }

    private fun addResultModelAttributes(model: Model, result: QrCodeProcessingResult) {
        model.addAttribute(qrCodeImage, result.image)
        model.addAttribute(textToBeEncoded, result.encodedText)
        if (result.isSuccessfull()) {
            model.addAttribute(successMessage, result.successMessage)
        } else {
            model.addAttribute(errorMessage, result.errorMessage)
        }
    }
}