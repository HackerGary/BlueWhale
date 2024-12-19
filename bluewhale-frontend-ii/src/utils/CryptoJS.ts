import CryptoJS from "crypto-js"
export interface CrypotoType {
    encrypt: Function
    decrypt: Function
}
export default class Crypoto implements CrypotoType {
    private key = "hello603hello603" // 要跟后端约定规则
    private keyHex = this.getHexKey()

    private getHexKey() {
        return CryptoJS.enc.Latin1.parse(this.key)
    }

    encrypt(msg: string) {
        const encrypted = CryptoJS.AES.encrypt(msg, this.keyHex, {
            iv: this.keyHex, // 这个参数我之前忘传了，使用CBC的时候一直会报错，所以千万不要忘记哦，如果创建成功，但是控制台一直报错什么undefined的呀，什么[0]啥的，就检查一下 是不是自己使用有问题
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        return encrypted.toString()
    }

    decrypt(msg: string) {
        const decrypted = CryptoJS.AES.decrypt(msg, this.keyHex, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        })
        return decrypted.toString(CryptoJS.enc.Utf8);
    }
}