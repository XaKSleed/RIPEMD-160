public class RIPEMD160 {
    private static final int digestLength = 20;
    private int h0, h1, h2, h3, h4;

    private int[] x = new int[16];
    private int x0ff;
    private int xBufOff;
    private final byte[] xBuf = new byte[64];
    private long byteCount;

    public RIPEMD160(){
        reset();
    }

    public int getDigestLength(){
        return digestLength;
    }


    private void reset(){
         h0 = 0x67452301;
         h1 = 0xEFCDAB89;
         h2 = 0x98BADCFE;
         h3 = 0x10325476;
         h4 = 0xC3D2E1F0;

         x0ff = 0;

         for(int i = 0; i != x.length; i++){
             x[i] = 0;
         }
         byteCount = 0;
         xBufOff = 0;
         for(int i = 0; i < xBuf.length; i++){
             xBuf[i] = 0;
         }
    }

    public void update(byte[] in, int inoff, int len){
        len = Math.max(0, len);
        int i = 0;

        int limit = ((len) & ~3);

        for(; i < limit; i+=4){
            processWord(in, inoff + i);
        }

        while(i < len){
            xBuf[xBufOff++] = in[inoff + i++];
        }

        byteCount += len;

    }
    public void update(byte in){
        xBuf[xBufOff++] = in;

        if(xBufOff == xBuf.length){
            processWord(xBuf, 0);
            xBufOff = 0;
        }
        byteCount++;
    }

    private void processWord(byte[] in, int inoff){
        x[x0ff++] = (in[inoff]&0xff) | ((in[inoff+1]&0xff) << 8)|((in[inoff+2]&0xff) << 16) | ((in[inoff+3]&0xff) << 24);
        if(x0ff == 16){
            processBlock();
        }
    }

    private void processBlock(){
        int a, aa;
        int b, bb;
        int c, cc;
        int d, dd;
        int e, ee;

        a = aa = h0;
        b = bb = h1;
        c = cc = h2;
        d = dd = h3;
        e = ee = h4;


        a = RL(a + f1(b,c,d) + x[ 0], 11) + e; c = RL(c, 10);
        e = RL(e + f1(a,b,c) + x[ 1], 14) + d; b = RL(b, 10);
        d = RL(d + f1(e,a,b) + x[ 2], 15) + c; a = RL(a, 10);
        c = RL(c + f1(d,e,a) + x[ 3], 12) + b; e = RL(e, 10);
        b = RL(b + f1(c,d,e) + x[ 4],  5) + a; d = RL(d, 10);
        a = RL(a + f1(b,c,d) + x[ 5],  8) + e; c = RL(c, 10);
        e = RL(e + f1(a,b,c) + x[ 6],  7) + d; b = RL(b, 10);
        d = RL(d + f1(e,a,b) + x[ 7],  9) + c; a = RL(a, 10);
        c = RL(c + f1(d,e,a) + x[ 8], 11) + b; e = RL(e, 10);
        b = RL(b + f1(c,d,e) + x[ 9], 13) + a; d = RL(d, 10);
        a = RL(a + f1(b,c,d) + x[10], 14) + e; c = RL(c, 10);
        e = RL(e + f1(a,b,c) + x[11], 15) + d; b = RL(b, 10);
        d = RL(d + f1(e,a,b) + x[12],  6) + c; a = RL(a, 10);
        c = RL(c + f1(d,e,a) + x[13],  7) + b; e = RL(e, 10);
        b = RL(b + f1(c,d,e) + x[14],  9) + a; d = RL(d, 10);
        a = RL(a + f1(b,c,d) + x[15],  8) + e; c = RL(c, 10);

        aa = RL(aa + f5(bb,cc,dd) + x[ 5] + 0x50a28be6,  8) + ee; cc = RL(cc, 10);
        ee = RL(ee + f5(aa,bb,cc) + x[14] + 0x50a28be6,  9) + dd; bb = RL(bb, 10);
        dd = RL(dd + f5(ee,aa,bb) + x[ 7] + 0x50a28be6,  9) + cc; aa = RL(aa, 10);
        cc = RL(cc + f5(dd,ee,aa) + x[ 0] + 0x50a28be6, 11) + bb; ee = RL(ee, 10);
        bb = RL(bb + f5(cc,dd,ee) + x[ 9] + 0x50a28be6, 13) + aa; dd = RL(dd, 10);
        aa = RL(aa + f5(bb,cc,dd) + x[ 2] + 0x50a28be6, 15) + ee; cc = RL(cc, 10);
        ee = RL(ee + f5(aa,bb,cc) + x[11] + 0x50a28be6, 15) + dd; bb = RL(bb, 10);
        dd = RL(dd + f5(ee,aa,bb) + x[ 4] + 0x50a28be6,  5) + cc; aa = RL(aa, 10);
        cc = RL(cc + f5(dd,ee,aa) + x[13] + 0x50a28be6,  7) + bb; ee = RL(ee, 10);
        bb = RL(bb + f5(cc,dd,ee) + x[ 6] + 0x50a28be6,  7) + aa; dd = RL(dd, 10);
        aa = RL(aa + f5(bb,cc,dd) + x[15] + 0x50a28be6,  8) + ee; cc = RL(cc, 10);
        ee = RL(ee + f5(aa,bb,cc) + x[ 8] + 0x50a28be6, 11) + dd; bb = RL(bb, 10);
        dd = RL(dd + f5(ee,aa,bb) + x[ 1] + 0x50a28be6, 14) + cc; aa = RL(aa, 10);
        cc = RL(cc + f5(dd,ee,aa) + x[10] + 0x50a28be6, 14) + bb; ee = RL(ee, 10);
        bb = RL(bb + f5(cc,dd,ee) + x[ 3] + 0x50a28be6, 12) + aa; dd = RL(dd, 10);
        aa = RL(aa + f5(bb,cc,dd) + x[12] + 0x50a28be6,  6) + ee; cc = RL(cc, 10);

        e = RL(e + f2(a,b,c) + x[ 7] + 0x5a827999,  7) + d; b = RL(b, 10);
        d = RL(d + f2(e,a,b) + x[ 4] + 0x5a827999,  6) + c; a = RL(a, 10);
        c = RL(c + f2(d,e,a) + x[13] + 0x5a827999,  8) + b; e = RL(e, 10);
        b = RL(b + f2(c,d,e) + x[ 1] + 0x5a827999, 13) + a; d = RL(d, 10);
        a = RL(a + f2(b,c,d) + x[10] + 0x5a827999, 11) + e; c = RL(c, 10);
        e = RL(e + f2(a,b,c) + x[ 6] + 0x5a827999,  9) + d; b = RL(b, 10);
        d = RL(d + f2(e,a,b) + x[15] + 0x5a827999,  7) + c; a = RL(a, 10);
        c = RL(c + f2(d,e,a) + x[ 3] + 0x5a827999, 15) + b; e = RL(e, 10);
        b = RL(b + f2(c,d,e) + x[12] + 0x5a827999,  7) + a; d = RL(d, 10);
        a = RL(a + f2(b,c,d) + x[ 0] + 0x5a827999, 12) + e; c = RL(c, 10);
        e = RL(e + f2(a,b,c) + x[ 9] + 0x5a827999, 15) + d; b = RL(b, 10);
        d = RL(d + f2(e,a,b) + x[ 5] + 0x5a827999,  9) + c; a = RL(a, 10);
        c = RL(c + f2(d,e,a) + x[ 2] + 0x5a827999, 11) + b; e = RL(e, 10);
        b = RL(b + f2(c,d,e) + x[14] + 0x5a827999,  7) + a; d = RL(d, 10);
        a = RL(a + f2(b,c,d) + x[11] + 0x5a827999, 13) + e; c = RL(c, 10);
        e = RL(e + f2(a,b,c) + x[ 8] + 0x5a827999, 12) + d; b = RL(b, 10);

        ee = RL(ee + f4(aa,bb,cc) + x[ 6] + 0x5c4dd124,  9) + dd; bb = RL(bb, 10);
        dd = RL(dd + f4(ee,aa,bb) + x[11] + 0x5c4dd124, 13) + cc; aa = RL(aa, 10);
        cc = RL(cc + f4(dd,ee,aa) + x[ 3] + 0x5c4dd124, 15) + bb; ee = RL(ee, 10);
        bb = RL(bb + f4(cc,dd,ee) + x[ 7] + 0x5c4dd124,  7) + aa; dd = RL(dd, 10);
        aa = RL(aa + f4(bb,cc,dd) + x[ 0] + 0x5c4dd124, 12) + ee; cc = RL(cc, 10);
        ee = RL(ee + f4(aa,bb,cc) + x[13] + 0x5c4dd124,  8) + dd; bb = RL(bb, 10);
        dd = RL(dd + f4(ee,aa,bb) + x[ 5] + 0x5c4dd124,  9) + cc; aa = RL(aa, 10);
        cc = RL(cc + f4(dd,ee,aa) + x[10] + 0x5c4dd124, 11) + bb; ee = RL(ee, 10);
        bb = RL(bb + f4(cc,dd,ee) + x[14] + 0x5c4dd124,  7) + aa; dd = RL(dd, 10);
        aa = RL(aa + f4(bb,cc,dd) + x[15] + 0x5c4dd124,  7) + ee; cc = RL(cc, 10);
        ee = RL(ee + f4(aa,bb,cc) + x[ 8] + 0x5c4dd124, 12) + dd; bb = RL(bb, 10);
        dd = RL(dd + f4(ee,aa,bb) + x[12] + 0x5c4dd124,  7) + cc; aa = RL(aa, 10);
        cc = RL(cc + f4(dd,ee,aa) + x[ 4] + 0x5c4dd124,  6) + bb; ee = RL(ee, 10);
        bb = RL(bb + f4(cc,dd,ee) + x[ 9] + 0x5c4dd124, 15) + aa; dd = RL(dd, 10);
        aa = RL(aa + f4(bb,cc,dd) + x[ 1] + 0x5c4dd124, 13) + ee; cc = RL(cc, 10);
        ee = RL(ee + f4(aa,bb,cc) + x[ 2] + 0x5c4dd124, 11) + dd; bb = RL(bb, 10);

        d = RL(d + f3(e,a,b) + x [3] + 0x6ed9eba1, 11) + c; a = RL(a, 10);
        c = RL(c + f3(d,e,a) + x[10] + 0x6ed9eba1, 13) + b; e = RL(e, 10);
        b = RL(b + f3(c,d,e) + x[14] + 0x6ed9eba1,  6) + a; d = RL(d, 10);
        a = RL(a + f3(b,c,d) + x[ 4] + 0x6ed9eba1,  7) + e; c = RL(c, 10);
        e = RL(e + f3(a,b,c) + x[ 9] + 0x6ed9eba1, 14) + d; b = RL(b, 10);
        d = RL(d + f3(e,a,b) + x[15] + 0x6ed9eba1,  9) + c; a = RL(a, 10);
        c = RL(c + f3(d,e,a) + x[ 8] + 0x6ed9eba1, 13) + b; e = RL(e, 10);
        b = RL(b + f3(c,d,e) + x[ 1] + 0x6ed9eba1, 15) + a; d = RL(d, 10);
        a = RL(a + f3(b,c,d) + x[ 2] + 0x6ed9eba1, 14) + e; c = RL(c, 10);
        e = RL(e + f3(a,b,c) + x[ 7] + 0x6ed9eba1,  8) + d; b = RL(b, 10);
        d = RL(d + f3(e,a,b) + x[ 0] + 0x6ed9eba1, 13) + c; a = RL(a, 10);
        c = RL(c + f3(d,e,a) + x[ 6] + 0x6ed9eba1,  6) + b; e = RL(e, 10);
        b = RL(b + f3(c,d,e) + x[13] + 0x6ed9eba1,  5) + a; d = RL(d, 10);
        a = RL(a + f3(b,c,d) + x[11] + 0x6ed9eba1, 12) + e; c = RL(c, 10);
        e = RL(e + f3(a,b,c) + x[ 5] + 0x6ed9eba1,  7) + d; b = RL(b, 10);
        d = RL(d + f3(e,a,b) + x[12] + 0x6ed9eba1,  5) + c; a = RL(a, 10);

        dd = RL(dd + f3(ee,aa,bb) + x[15] + 0x6d703ef3,  9) + cc; aa = RL(aa, 10);
        cc = RL(cc + f3(dd,ee,aa) + x[ 5] + 0x6d703ef3,  7) + bb; ee = RL(ee, 10);
        bb = RL(bb + f3(cc,dd,ee) + x[ 1] + 0x6d703ef3, 15) + aa; dd = RL(dd, 10);
        aa = RL(aa + f3(bb,cc,dd) + x[ 3] + 0x6d703ef3, 11) + ee; cc = RL(cc, 10);
        ee = RL(ee + f3(aa,bb,cc) + x[ 7] + 0x6d703ef3,  8) + dd; bb = RL(bb, 10);
        dd = RL(dd + f3(ee,aa,bb) + x[14] + 0x6d703ef3,  6) + cc; aa = RL(aa, 10);
        cc = RL(cc + f3(dd,ee,aa) + x[ 6] + 0x6d703ef3,  6) + bb; ee = RL(ee, 10);
        bb = RL(bb + f3(cc,dd,ee) + x[ 9] + 0x6d703ef3, 14) + aa; dd = RL(dd, 10);
        aa = RL(aa + f3(bb,cc,dd) + x[11] + 0x6d703ef3, 12) + ee; cc = RL(cc, 10);
        ee = RL(ee + f3(aa,bb,cc) + x[ 8] + 0x6d703ef3, 13) + dd; bb = RL(bb, 10);
        dd = RL(dd + f3(ee,aa,bb) + x[12] + 0x6d703ef3,  5) + cc; aa = RL(aa, 10);
        cc = RL(cc + f3(dd,ee,aa) + x[ 2] + 0x6d703ef3, 14) + bb; ee = RL(ee, 10);
        bb = RL(bb + f3(cc,dd,ee) + x[10] + 0x6d703ef3, 13) + aa; dd = RL(dd, 10);
        aa = RL(aa + f3(bb,cc,dd) + x[ 0] + 0x6d703ef3, 13) + ee; cc = RL(cc, 10);
        ee = RL(ee + f3(aa,bb,cc) + x[ 4] + 0x6d703ef3,  7) + dd; bb = RL(bb, 10);
        dd = RL(dd + f3(ee,aa,bb) + x[13] + 0x6d703ef3,  5) + cc; aa = RL(aa, 10);

        c = RL(c + f4(d,e,a) + x[ 1] + 0x8f1bbcdc, 11) + b; e = RL(e, 10);
        b = RL(b + f4(c,d,e) + x[ 9] + 0x8f1bbcdc, 12) + a; d = RL(d, 10);
        a = RL(a + f4(b,c,d) + x[11] + 0x8f1bbcdc, 14) + e; c = RL(c, 10);
        e = RL(e + f4(a,b,c) + x[10] + 0x8f1bbcdc, 15) + d; b = RL(b, 10);
        d = RL(d + f4(e,a,b) + x[ 0] + 0x8f1bbcdc, 14) + c; a = RL(a, 10);
        c = RL(c + f4(d,e,a) + x[ 8] + 0x8f1bbcdc, 15) + b; e = RL(e, 10);
        b = RL(b + f4(c,d,e) + x[12] + 0x8f1bbcdc,  9) + a; d = RL(d, 10);
        a = RL(a + f4(b,c,d) + x[ 4] + 0x8f1bbcdc,  8) + e; c = RL(c, 10);
        e = RL(e + f4(a,b,c) + x[13] + 0x8f1bbcdc,  9) + d; b = RL(b, 10);
        d = RL(d + f4(e,a,b) + x[ 3] + 0x8f1bbcdc, 14) + c; a = RL(a, 10);
        c = RL(c + f4(d,e,a) + x[ 7] + 0x8f1bbcdc,  5) + b; e = RL(e, 10);
        b = RL(b + f4(c,d,e) + x[15] + 0x8f1bbcdc,  6) + a; d = RL(d, 10);
        a = RL(a + f4(b,c,d) + x[14] + 0x8f1bbcdc,  8) + e; c = RL(c, 10);
        e = RL(e + f4(a,b,c) + x[ 5] + 0x8f1bbcdc,  6) + d; b = RL(b, 10);
        d = RL(d + f4(e,a,b) + x[ 6] + 0x8f1bbcdc,  5) + c; a = RL(a, 10);
        c = RL(c + f4(d,e,a) + x[ 2] + 0x8f1bbcdc, 12) + b; e = RL(e, 10);


        cc = RL(cc + f2(dd,ee,aa) + x[ 8] + 0x7a6d76e9, 15) + bb; ee = RL(ee, 10);
        bb = RL(bb + f2(cc,dd,ee) + x[ 6] + 0x7a6d76e9,  5) + aa; dd = RL(dd, 10);
        aa = RL(aa + f2(bb,cc,dd) + x[ 4] + 0x7a6d76e9,  8) + ee; cc = RL(cc, 10);
        ee = RL(ee + f2(aa,bb,cc) + x[ 1] + 0x7a6d76e9, 11) + dd; bb = RL(bb, 10);
        dd = RL(dd + f2(ee,aa,bb) + x[ 3] + 0x7a6d76e9, 14) + cc; aa = RL(aa, 10);
        cc = RL(cc + f2(dd,ee,aa) + x[11] + 0x7a6d76e9, 14) + bb; ee = RL(ee, 10);
        bb = RL(bb + f2(cc,dd,ee) + x[15] + 0x7a6d76e9,  6) + aa; dd = RL(dd, 10);
        aa = RL(aa + f2(bb,cc,dd) + x[ 0] + 0x7a6d76e9, 14) + ee; cc = RL(cc, 10);
        ee = RL(ee + f2(aa,bb,cc) + x[ 5] + 0x7a6d76e9,  6) + dd; bb = RL(bb, 10);
        dd = RL(dd + f2(ee,aa,bb) + x[12] + 0x7a6d76e9,  9) + cc; aa = RL(aa, 10);
        cc = RL(cc + f2(dd,ee,aa) + x[ 2] + 0x7a6d76e9, 12) + bb; ee = RL(ee, 10);
        bb = RL(bb + f2(cc,dd,ee) + x[13] + 0x7a6d76e9,  9) + aa; dd = RL(dd, 10);
        aa = RL(aa + f2(bb,cc,dd) + x[ 9] + 0x7a6d76e9, 12) + ee; cc = RL(cc, 10);
        ee = RL(ee + f2(aa,bb,cc) + x[ 7] + 0x7a6d76e9,  5) + dd; bb = RL(bb, 10);
        dd = RL(dd + f2(ee,aa,bb) + x[10] + 0x7a6d76e9, 15) + cc; aa = RL(aa, 10);
        cc = RL(cc + f2(dd,ee,aa) + x[14] + 0x7a6d76e9,  8) + bb; ee = RL(ee, 10);

        b = RL(b + f5(c,d,e) + x[ 4] + 0xa953fd4e,  9) + a; d = RL(d, 10);
        a = RL(a + f5(b,c,d) + x[ 0] + 0xa953fd4e, 15) + e; c = RL(c, 10);
        e = RL(e + f5(a,b,c) + x[ 5] + 0xa953fd4e,  5) + d; b = RL(b, 10);
        d = RL(d + f5(e,a,b) + x[ 9] + 0xa953fd4e, 11) + c; a = RL(a, 10);
        c = RL(c + f5(d,e,a) + x[ 7] + 0xa953fd4e,  6) + b; e = RL(e, 10);
        b = RL(b + f5(c,d,e) + x[12] + 0xa953fd4e,  8) + a; d = RL(d, 10);
        a = RL(a + f5(b,c,d) + x[ 2] + 0xa953fd4e, 13) + e; c = RL(c, 10);
        e = RL(e + f5(a,b,c) + x[10] + 0xa953fd4e, 12) + d; b = RL(b, 10);
        d = RL(d + f5(e,a,b) + x[14] + 0xa953fd4e,  5) + c; a = RL(a, 10);
        c = RL(c + f5(d,e,a) + x[ 1] + 0xa953fd4e, 12) + b; e = RL(e, 10);
        b = RL(b + f5(c,d,e) + x[ 3] + 0xa953fd4e, 13) + a; d = RL(d, 10);
        a = RL(a + f5(b,c,d) + x[ 8] + 0xa953fd4e, 14) + e; c = RL(c, 10);
        e = RL(e + f5(a,b,c) + x[11] + 0xa953fd4e, 11) + d; b = RL(b, 10);
        d = RL(d + f5(e,a,b) + x[ 6] + 0xa953fd4e,  8) + c; a = RL(a, 10);
        c = RL(c + f5(d,e,a) + x[15] + 0xa953fd4e,  5) + b; e = RL(e, 10);
        b = RL(b + f5(c,d,e) + x[13] + 0xa953fd4e,  6) + a; d = RL(d, 10);

        bb = RL(bb + f1(cc,dd,ee) + x[12],  8) + aa; dd = RL(dd, 10);
        aa = RL(aa + f1(bb,cc,dd) + x[15],  5) + ee; cc = RL(cc, 10);
        ee = RL(ee + f1(aa,bb,cc) + x[10], 12) + dd; bb = RL(bb, 10);
        dd = RL(dd + f1(ee,aa,bb) + x[ 4],  9) + cc; aa = RL(aa, 10);
        cc = RL(cc + f1(dd,ee,aa) + x[ 1], 12) + bb; ee = RL(ee, 10);
        bb = RL(bb + f1(cc,dd,ee) + x[ 5],  5) + aa; dd = RL(dd, 10);
        aa = RL(aa + f1(bb,cc,dd) + x[ 8], 14) + ee; cc = RL(cc, 10);
        ee = RL(ee + f1(aa,bb,cc) + x[ 7],  6) + dd; bb = RL(bb, 10);
        dd = RL(dd + f1(ee,aa,bb) + x[ 6],  8) + cc; aa = RL(aa, 10);
        cc = RL(cc + f1(dd,ee,aa) + x[ 2], 13) + bb; ee = RL(ee, 10);
        bb = RL(bb + f1(cc,dd,ee) + x[13],  6) + aa; dd = RL(dd, 10);
        aa = RL(aa + f1(bb,cc,dd) + x[14],  5) + ee; cc = RL(cc, 10);
        ee = RL(ee + f1(aa,bb,cc) + x[ 0], 15) + dd; bb = RL(bb, 10);
        dd = RL(dd + f1(ee,aa,bb) + x[ 3], 13) + cc; aa = RL(aa, 10);
        cc = RL(cc + f1(dd,ee,aa) + x[ 9], 11) + bb; ee = RL(ee, 10);
        bb = RL(bb + f1(cc,dd,ee) + x[11], 11) + aa; dd = RL(dd, 10);

        dd += c + h1;
        h1 = h2 + d + ee;
        h2 = h3 + e + aa;
        h3 = h4 + a + bb;
        h4 = h0 + b + cc;
        h0 = dd;

        x0ff = 0;
        for (int i = 0; i != x.length; i++)
        {
            x[i] = 0;
        }

    }


    private int RL(int x, int n){
        return (x << n) | (x >>> (32 - n));
    }

    private int f1(int x, int y, int z){
        return x ^ y ^ z;
    }
    private int f2(int x, int y, int z){
        return (x & y) | (~x & z);
    }
    private int f3(int x, int y, int z){
        return (x | ~y) ^ z;
    }
    private int f4(int x, int y, int z){
        return (x & z) | (y & ~z);
    }
    private int f5(int x, int y, int z){
        return x ^ (y | ~z);
    }

    public int doFinal(byte[] out, int outOff){
        finish();

        unpackWord(h0, out, outOff);
        unpackWord(h1, out, outOff + 4);
        unpackWord(h2, out, outOff + 8);
        unpackWord(h3, out, outOff + 12);
        unpackWord(h4, out, outOff + 16);

        reset();
        return digestLength;
    }

    private void finish(){
        long bitLength = (byteCount << 3);
        update((byte)128);
        while(xBufOff != 0){
            update((byte)0);
        }
        processLength(bitLength);
        processBlock();
    }

    private void processLength(long bitLength){
        if(x0ff > 14){
            processBlock();
        }
        x[14] = (int)(bitLength & 0xffffffff);
        x[15] = (int)(bitLength >>> 32);
    }

    private  void unpackWord(int word, byte[] out, int outOff){
        out[outOff] = (byte)word;
        out[outOff + 1] = (byte)(word >>> 8);
        out[outOff + 2] = (byte)(word >>> 16);
        out[outOff + 3] = (byte)(word >>> 24);
    }










}
