package demo.justin.qrcode.model

class QrCodeUrlParser(val qrCodeUrl: QrCodeUrl) : AbstractQrCodeParser() {
    override fun parse(): String {
        return qrCodeUrl.urlToBeEncoded.replace(" ", "%20")
    }
}