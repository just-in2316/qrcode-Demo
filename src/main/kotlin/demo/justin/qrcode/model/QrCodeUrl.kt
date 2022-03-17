package demo.justin.qrcode.model

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotEmpty

@Validated
class QrCodeUrl(@field:NotEmpty val urlToBeEncoded: String)