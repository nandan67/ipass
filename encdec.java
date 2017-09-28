//for encrypt the data
private static String doEncrypt(String encodekey, String inputStr) throws CryptoException {
        try {

            // getting cipher object of AES Tranforamation
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            byte[] key = encodekey.getBytes("UTF-8");
            MessageDigest sha256hash = MessageDigest.getInstance("SHA-512");

            // this is important to copy only first 128 bit of key, this must be
        
            key = sha256hash.digest(key);
            key = Arrays.copyOf(key, 16);

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] inputBytes = inputStr.getBytes();
            byte[] outputBytes = cipher.doFinal(inputBytes);

            System.out.println("outputBytes" + outputBytes);

            return new String(Base64.getEncoder().encode(outputBytes));

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }


//for decrypt the data

public static String doDecrypt(String encodekey, String encrptedStr) {
        try {

            Cipher dcipher = Cipher.getInstance(TRANSFORMATION);
            byte[] key = encodekey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            dcipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] dec = Base64.getDecoder().decode(encrptedStr.getBytes());
            byte[] utf8 = dcipher.doFinal(dec);

            // create new string based on the specified charset
            return new String(utf8, "UTF8");

        } catch (Exception e) {

            e.printStackTrace();

        }
        return null;
    }
