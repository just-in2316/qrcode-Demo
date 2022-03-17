package demo.justin.qrcode.model;

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotEmpty

@Validated
class QrCodeText(@field:NotEmpty val textToBeEncoded: String)
