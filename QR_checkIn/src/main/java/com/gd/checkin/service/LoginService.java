package com.gd.checkin.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.*;

import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gd.checkin.mapper.MemberMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class LoginService {
	private static final String SECRET_KEY = "KimTaeHoonSecretKeyForDataEncryption"; // JWT 암호화,복호화 키
	private final Long expiredTime = 1000 * 30L;// 토큰 유호시간 설정 30초

	@Autowired MemberMapper MemberMapper;
	
	// 토큰 생성 메소드
	public String createToken() {
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // HS256알고리즘으로 암호화 하겠다.
		
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY); // SECRET_KEY키 byte형변환
		Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName()); // 키 암호화 매개변수(byte값, 암호화알고리즘)
		
		return Jwts.builder() // 토큰 생성 후 리턴
				.setSubject("")
				.setClaims(createClaims())
				.signWith(signatureAlgorithm, signingKey)
				.setExpiration(new Date(System.currentTimeMillis() + expiredTime))
				.compact();
	}
	
	private static Map<String, Object> createClaims() { // 추후 DB에서 데이터 가져와서 넣을 예정
		
		Map<String, Object> claims = new HashMap<>(); 
		claims.put("name", "KimTaeHoon"); 
		claims.put("role", "ROLE_ADMIN");
		return claims;
	}
	
	// QR생성
	public String QRcreate() throws WriterException, IOException {
		
		String data = createToken();
		
        StringBuffer contents = new StringBuffer();
        contents.append(data);
        
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix matrix = qrWriter.encode(contents.toString(), BarcodeFormat.QR_CODE, 400, 400);
        MatrixToImageConfig config = new MatrixToImageConfig();
        
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix,config);

        File temp = new File("");
		String path = temp.getAbsolutePath();
		File file = new File(path + "\\src\\main\\webapp\\resource\\myqr.png");
        
        ImageIO.write(qrImage, "png", file);
        return data;
	}
}