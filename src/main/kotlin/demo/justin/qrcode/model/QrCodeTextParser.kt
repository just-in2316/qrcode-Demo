package demo.justin.qrcode.model

class QrCodeTextParser (val qrCodeText: QrCodeText) : AbstractQrCodeParser() {
    override fun parse(): String {
        return qrCodeText.textToBeEncoded.replace(" ", "%20")
    }
}