package nielsj.crypto.view;

// class Util merely is a set of useful methods

public class Util {

  // conversion to and from multiline strings
  // 'multiline' strings are displayed on multiple lines
  // (to avoid horizontal scrolling)

  // from String to multiline String
  public static String stringToMultiLine(String str, int charPerLine) {
    int inLength = str.length();
    StringBuffer sb = new StringBuffer("");
    int localc = 0;
    for (int i = 0; i < inLength; i++) {
      sb.append(str.charAt(i));
      localc++;
      if (localc == charPerLine && i + 1 < inLength) {
        sb.append("\n");
        localc = 0;
      }
    }
    return sb.toString();
  }

  // from multiLine String to (ordinary) String
  // (change name so name does not assume Hex format)

  public static String stringToSingleLine(String hexString) {
    int inLength = hexString.length();
    StringBuffer sb = new StringBuffer("");
    String s = sb.toString();
    char c;
    for (int i = 0; i < inLength; i++) {
      c = hexString.charAt(i);
      if (c != '\n') {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  // byte array to 'byte string'
  public static String byteArrayToByteString(byte[] byteArray){
    StringBuffer sb = new StringBuffer("");
    for (int i = 0;i < byteArray.length; i++) {
      byte b = byteArray[i];
      Byte B = new Byte(b);
      sb.append(B.toString() + " ");
    }
    String bytes = sb.toString();
    return bytes;
  }

  // byte array to 'bit string'
  public static String byteArrayToBitString(byte[] byteArray){
    StringBuffer sb = new StringBuffer("");
    for (int i = 0;i < byteArray.length; i++) {
      byte b = byteArray[i];
      String bs = Integer.toBinaryString((b + 256) % 256);
      // for some reason the above is required..
      // now addeng leading zeros
      while (bs.length() < 8) bs = "0" + bs;
      sb.append(bs + " ");
    }
    String bits = sb.toString();
    return bits;
  }

}