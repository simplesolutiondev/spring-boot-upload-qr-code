package dev.simplesolution.uploadqr.service;

public interface QRCodeService {

    String decodeQR(byte[] qrCodeBytes);

}
