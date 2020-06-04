import java.io.UnsupportedEncodingException;


public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {

        String mess = "";

        byte[] byteArr = mess.getBytes("UTF-8");
        RIPEMD160 first = new RIPEMD160();
         first.update(byteArr, 0, byteArr.length);
         byte[] res = new byte[first.getDigestLength()];
         first.doFinal(res, 0);


       StringBuffer hexString = new StringBuffer();
       for(int i = 0; i < res.length; i++){
           hexString.append(Integer.toHexString(0xff & res[i]));
       }
        System.out.println(hexString.toString());


    }
}
