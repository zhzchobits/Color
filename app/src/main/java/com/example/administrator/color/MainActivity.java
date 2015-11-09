package com.example.administrator.color;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {
    private Bitmap IntBitmap;
    private Bitmap OutBitmap;
    private ImageView OutimageView;
    private ImageView IntimageView;
    private BitmapFactory.Options options;
    private Button button;
    public static final int min_mosaic_block_size = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntimageView=(ImageView)findViewById(R.id.imageView);
        OutimageView=(ImageView)findViewById(R.id.imageView2);
        button=(Button)findViewById(R.id.button);
        options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //options.inPurgeable = true;
        IntBitmap= BitmapFactory.decodeResource(this.getResources(),
                R.drawable.tp,options);
        //IntBitmap= BitmapFactory.decodeResource(this.getResources(),
               // R.drawable.y3);
        options.inJustDecodeBounds=false;
        options.inSampleSize=2;
        IntBitmap= BitmapFactory.decodeResource(this.getResources(),
                R.drawable.tp,options);
        //OutBitmap= BitmapFactory.decodeResource(this.getResources(),
                //R.drawable.y3);
        OutBitmap = Bitmap.createBitmap(IntBitmap.getWidth(), IntBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        IntimageView.setImageBitmap(IntBitmap);
        OutimageView.setImageBitmap(OutBitmap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // emboss(IntBitmap,OutBitmap);
                //makeb(IntBitmap, OutBitmap);
                //makeMosaic(IntBitmap,OutBitmap,32);
                //overlay(IntBitmap,OutBitmap);
                 //keepblue(IntBitmap,OutBitmap);
                //int i=getH(IntBitmap);
                //keepgreen(IntBitmap, OutBitmap);
                additive(IntBitmap,OutBitmap);
                //getcolor(IntBitmap);
                //makeComicFilter(IntBitmap,OutBitmap);
                //System.out.println(i+"H");
                OutimageView.setImageBitmap(OutBitmap);
            }
        });
    }
    public void getcolor(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int R = 0;
        int G = 0;
        int B = 0;
        int[] pixels = new int[width * height];
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                int curr_color = pixels[j * width + i];
                     R=Color.red(curr_color) ;
                     G=Color.green(curr_color);
                     B=Color.blue(curr_color);
                System.out.println(R+"%"+G+"%"+B);
            }
        }
    }
    //����
    public void emboss(Bitmap bitmapin,Bitmap bitmapout){
        int width = bitmapin.getWidth();
        int height = bitmapin.getHeight();
        Log.i("zhzw=",width+"zhzh="+height);
        //Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;
        int[] pixels = new int[width * height];
        bitmapin.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos = 0;
        int preColor = 0;
        int prepreColor = 0;
        preColor = pixels[0];


        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                int curr_color = pixels[j * width + i];
                int r = Color.red(curr_color) - Color.red(prepreColor) + 128;
                int g = Color.green(curr_color) - Color.green(prepreColor) + 128;
                int b = Color.blue(curr_color) - Color.blue(prepreColor) + 128;
                int a = Color.alpha(curr_color);
                int newcolor = (int)(r * 0.3 + g * 0.59 + b * 0.11);
                pixels[j * width + i]= Color.argb(a, newcolor, newcolor, newcolor);
                prepreColor = preColor;
                preColor = curr_color;
            }
        }
        /*for (int i = 0, length = height - 1; i < length; i++) {
            for (int k = 0, len = width - 1; k < len; k++) {
                pos = i * width + k;
                pixColor = pixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);

                pixColor = pixels[pos + 1];
                newR = Color.red(pixColor) - pixR + 127;
                newG = Color.green(pixColor) - pixG + 127;
                newB = Color.blue(pixColor) - pixB + 127;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[pos] = Color.argb(255, newR, newG, newB);
            }
        }*/
        bitmapout.setPixels(pixels, 0, width, 0, 0, width, height);
    }
    //�Զ��Աȶ�
    public void makeb(Bitmap bitmap1,Bitmap bitmap2){
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();
        int[] HistRed=new int[256];
        int[] HistGreen=new int[256];
        int[] HistBlue=new int[256];
        double dlowcut= 0.1;
        double dhighcut =0.1;
        int[] bmap=new int[256];
        int[] rmap=new int[256];
        int[] gmap=new int[256];
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int[] pixels = new int[width * height];
        bitmap1.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos ;
        int pixolor;
        for (int i=0;i<256;i++){
            HistBlue[i]=HistGreen[i]=HistRed[i]=bmap[i]=rmap[i]=gmap[i]=0;
        }
        for (int y = 0; y< height; y++) {
            for (int x = 0; x < width; x++) {
                pos = y * width + x;
                pixolor = pixels[pos];
                pixR = Color.red(pixolor);
                pixG = Color.green(pixolor);
                pixB = Color.blue(pixolor);
                HistBlue[pixB]++;
                HistGreen[pixG]++;
                HistRed[pixR]++;
            }
        }
        int PixelAmount = bitmap1.getHeight()*bitmap1.getWidth();
        int isum = 0;
        // blue
        int iminblue=0;
        int imaxblue=0;
        for (int y = 0;y<256;y++)//�����������һ��ܹ��˽���
        {
            isum= isum+HistBlue[y];
            if (isum>=PixelAmount*dlowcut*0.01)

            {
                iminblue = y;
                break;
            }
        }
        isum = 0;
        for (int y=255;y>=0;y--)
        {
            isum=isum+HistBlue[y];
            if (isum>=PixelAmount*dhighcut*0.01)

            {
                imaxblue=y;
                break;
            }
        }
        //red
        isum=0;
        int iminred=0;
        int imaxred=0;
        for (int y = 0;y<256;y++)//�����������һ��ܹ��˽���
        {
            isum= isum+HistRed[y];
            if (isum>=PixelAmount*dlowcut*0.01)

            {
                iminred = y;
                break;
            }
        }
        isum = 0;
        for (int y=255;y>=0;y--)
        {
            isum=isum+HistRed[y];
            if (isum>=PixelAmount*dhighcut*0.01)

            {
                imaxred=y;
                break;
            }
        }
        //green
        isum=0;
        int imingreen=0;
        int imaxgreen=0;
        for (int y = 0;y<256;y++)//�����������һ��ܹ��˽���
        {
            isum= isum+HistGreen[y];
            if (isum>=PixelAmount*dlowcut*0.01)

            {
                imingreen = y;
                break;
            }
        }
        isum = 0;
        for (int y=255;y>=0;y--)
        {
            isum=isum+HistGreen[y];
            if (isum>=PixelAmount*dhighcut*0.01)

            {
                imaxgreen=y;
                break;
            }
        }
        /////////�Զ�ɫ��
        //�Զ��Աȶ�
        int imin = 255;int imax =0;
        if (imin>iminblue)
            imin = iminblue;
        if (imin>iminred)
            imin = iminred;
        if (imin>imingreen)
            imin = imingreen;
        iminblue = imin ;
        imingreen=imin;
        iminred = imin  ;
        if (imax<imaxblue)
            imax    = imaxblue;
        if (imax<imaxgreen)
            imax    =imaxgreen;
        if (imax<imaxred)
            imax    =imaxred;
        imaxred = imax;
        imaxgreen = imax;
        imaxblue=imax;
        /////////////////
        //blue
        for (int y=0;y<256;y++)
        {
            if (y<=iminblue)
            {
                bmap[y]=0;
            }
            else
            {
                if (y>imaxblue)
                {
                    bmap[y]=255;
                }
                else
                {
                    //  BlueMap(Y) = (Y - MinBlue) / (MaxBlue - MinBlue) * 255      '��������
                    float ftmp = (float)(y-iminblue)/(imaxblue-iminblue);
                    bmap[y]=(int)(ftmp*255);
                }
            }
        }
        //red
        for (int y=0;y<256;y++)
        {
            if (y<=iminred)
            {
                rmap[y]=0;
            }
            else
            {
                if (y>imaxred)
                {
                    rmap[y]=255;
                }
                else
                {
                    float ftmp = (float)(y-iminred)/(imaxred-iminred);
                    rmap[y]=(int)(ftmp*255);
                }
            }
        }
        for (int y=0;y<256;y++){
            if (y<=imingreen)
            {
                gmap[y]=0;
            }
            else
            {
                if (y>imaxgreen)
                {
                    gmap[y]=255;
                }
                else
                {
                    //  BlueMap(Y) = (Y - MinBlue) / (MaxBlue - MinBlue) * 255      '��������
                    float ftmp = (float)(y-imingreen)/(imaxgreen-imingreen);
                    gmap[y]=(int)(ftmp*255);
                }
            }
        }
        for (int y = 0; y< height; y++) {
            for (int x = 0; x < width; x++) {
                pos = y * width + x;
                pixolor = pixels[pos];
                pixR = Color.red(pixolor);
                pixG = Color.green(pixolor);
                pixB = Color.blue(pixolor);
                newR = rmap[pixR];
                newG = gmap[pixG];
                newB = bmap[pixB];
                pixels[pos] = Color.argb(255, newR, newG, newB);
            }
        }
        bitmap2.setPixels(pixels, 0, width, 0, 0, width, height);
    }
    //�����
    public static void makeMosaic(Bitmap bitmap1,Bitmap bitmap2,
                                    int blockSize){

        if (blockSize < min_mosaic_block_size) {
            blockSize = min_mosaic_block_size;
        }

        int bw = bitmap1.getWidth();
        int bh = bitmap1.getHeight();
        int[] bitmapPxs = new int[bw * bh];
        bitmap1.getPixels(bitmapPxs, 0, bw, 0, 0, bw, bh);
        int rowCount = (int) Math.ceil((float) bh / blockSize);
        int columnCount = (int) Math.ceil((float) bw / blockSize);
        int maxX = bw;
        int maxY = bh;
        for (int r = 0; r < rowCount; r++) {
           for (int c = 0; c < columnCount; c++) {
               int startX =  c * blockSize + 1;
                int startY =  r * blockSize + 1;
           dimBlock(bitmapPxs, startX, startY, blockSize, maxX, maxY);
             }
           }
        //dimBlock(bitmapPxs, 400, 800, 256, maxX, maxY);
         bitmap2.setPixels(bitmapPxs, 0, bw, 0, 0, bw, bh);
    }

    /**
     * �ӿ���ȡ���Ŵ󣬴Ӷ�ﵽ����˵�ģ��Ч��
     *
     * @param pxs
     * @param startX
     * @param startY
     * @param blockSize
     * @param maxX
     * @param maxY
     */
    private static void dimBlock(int[] pxs, int startX, int startY,
                                 int blockSize, int maxX, int maxY) {
        int stopX = startX + blockSize - 1;
        int stopY = startY + blockSize - 1;
        if (stopX > maxX) {
            stopX = maxX;
        }
        if (stopY > maxY) {
            stopY = maxY;
        }
        //
        int sampleColorX = startX + blockSize / 2;
        int sampleColorY = startY + blockSize / 2;
        //
        if (sampleColorX > maxX) {
            sampleColorX = maxX;
        }
        if (sampleColorY > maxY) {
            sampleColorY = maxY;
        }
        int colorLinePosition = (sampleColorY - 1) * maxX;
        int sampleColor = pxs[colorLinePosition + sampleColorX - 1];// ���ش�1��ʼ�����������0��ʼ
        for (int y = startY; y <= stopY; y++) {
            int p = (y - 1) * maxX;
            for (int x = startX; x <= stopX; x++) {
                // ���ش�1��ʼ�����������0��ʼ
                pxs[p + x - 1] = sampleColor;
            }
        }
    }
    //͸������
    public void overlay(Bitmap bitmap1,Bitmap bitmap2){
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();
        Bitmap overlay = BitmapFactory.decodeResource(this.getResources(), R.drawable.fg);
        int w = overlay.getWidth();
        int h = overlay.getHeight();
        float scaleX = width * 1F / w;
        float scaleY = height * 1F / h;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        Bitmap overlayCopy = Bitmap.createBitmap(overlay, 0, 0, w, h, matrix, true);
        int pixColor = 0;
        int layColor = 0;

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixA = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;
        int newA = 0;

        int layR = 0;
        int layG = 0;
        int layB = 0;
        int layA = 0;

        final float alpha = 0.8F;

        int[] srcPixels = new int[width * height];
        int[] layPixels = new int[width * height];
        bitmap1.getPixels(srcPixels, 0, width, 0, 0, width, height);
        overlayCopy.getPixels(layPixels, 0, width, 0, 0, width, height);
        int pos = 0;
        for (int i = 0; i < height; i++)
        {
            for (int k = 0; k < width; k++)
            {
                pos = i * width + k;
                pixColor = srcPixels[pos];
                layColor = layPixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                pixA = Color.alpha(pixColor);

                layR = Color.red(layColor);
                layG = Color.green(layColor);
                layB = Color.blue(layColor);
                layA = Color.alpha(layColor);

                newR = (int) (pixR * alpha + layR * (1 - alpha));
                newG = (int) (pixG * alpha + layG * (1 - alpha));
                newB = (int) (pixB * alpha + layB * (1 - alpha));
                layA = (int) (pixA * alpha + layA * (1 - alpha));

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                newA = Math.min(255, Math.max(0, layA));

                srcPixels[pos] = Color.argb(newA, newR, newG, newB);
            }
        }
        bitmap2.setPixels(srcPixels, 0, width, 0, 0, width, height);
    }
    //ʶ����ɫ
    public void keepblue(Bitmap bitmap1,Bitmap bitmap2){
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();
        int r = 0;
        int g = 0;
        int b = 0;
        int T = 0;
        float h = 0;
        int pixColor =0;
        int k;
        int i;
        int[] srcPixels = new int[width * height];
        int newcolor;
        bitmap1.getPixels(srcPixels, 0, width, 0, 0, width, height);
        for ( i = 0; i < height; i++) {
            for ( k = 0; k < width; k++){
                pixColor = srcPixels[i*width+k];
                r = Color.red(pixColor);
                g = Color.green(pixColor);
                b = Color.blue(pixColor);
                float maxColor = Math.max(r, Math.max(g, b));
                float minColor = Math.min(r, Math.min(g, b));
                if (r == maxColor)
                    h = (g - b) / (maxColor - minColor);
                else if (g == maxColor)
                    h = (float) (2.0 + (b - r) / (maxColor - minColor));
                else
                    h = (float) (4.0 + (r - g) / (maxColor - minColor));

                h /= 6;
                if (h < 0)
                    h++;
                T= (int) Math.round(h * 240.0);
                newcolor = (int) (r * 0.3 + g * 0.59 + b * 0.11);
                if (T<117||T>170){
                    srcPixels[i * width + k] = Color.argb(255, newcolor, newcolor, newcolor);
                }

            }
        }
        bitmap2.setPixels(srcPixels, 0, width, 0, 0, width, height);
    }
    //�ֲ����&����ȥ��ĥƤ
    public void additive(Bitmap bitmap1,Bitmap bitmap2){
        int width=bitmap1.getWidth();
        int height=bitmap1.getHeight();
        int r=0;
        int g=0;
        int b=0;
        int pixColor=0;
        int []srcPixels=new int[width*height];
        int []yS=new int[width*height];
        int []yG=new int[width*height];
        int []Cb=new int[width*height];
        int []Cr=new int[width*height];
        int k;
        int i;
        int x=0;
        int y=0;
        bitmap1.getPixels(srcPixels, 0, width, 0, 0, width, height);
        for(i = 0; i < height; i++){
            for ( k = 0; k < width; k++){
                pixColor = srcPixels[i*width+k];
                r = Color.red(pixColor);
                g = Color.green(pixColor);
                b = Color.blue(pixColor);
                yS[i*width+k]=(int)(0.299*r+0.587*g+0.114*b);
                Cb[i*width+k]=(int)(0.564*(b-yS[i*width+k]));
                Cr[i*width+k]=(int)(0.713*(r-yS[i*width+k]));
            }
        }
        Log.i("zhz","第一次循环YS500="+yS[500]);
        for (i=0;i<height;i++){
            for (k=0;k<width;k++){
                int Y=0;
                Log.i("zhz","Y="+Y);
                int q=i-10>0?i-10:0;
                int p=i+10>height?height:i+10;
                int w=k-10>0?k-10:0;
                int o=k+10>width?width:k+10;
                Log.i("zhz","q&p&w&o"+q+" "+p+" "+w+" "+o);
                for(x=q;x<p;x++){
                    for (y=w;y<o;y++){
                       Y+=yS[x*width+y];
                    }
                }
                //Y=xunhuan(x, y, yS, height, width, i, k, Y);
                Log.i("zhz","响应过Y"+Y);
                yG[i*width+k]=(int)(0.3*(Y/400)+0.7*yS[i*width+k]);
            }
        }
        Log.i("zhz", "第二次循环YG500=" + yG[500]);
        for (i=0;i<height;i++){
            for (k=0;k<width;k++){
                r=(int)(yG[i*width+k]+1.402*Cr[i*width+k]);
                g=(int)(yG[i*width+k]-0.344*Cb[i*width+k]-0.714*Cr[i*width+k]);
                b=(int)(yG[i*width+k]+1.772*Cb[i*width+k]);
                srcPixels[i * width + k] = Color.argb(255, r, g, b);
            }
        }
        Log.i("zhz","第三次循环SP500="+srcPixels[500]);
        bitmap2.setPixels(srcPixels, 0, width, 0, 0, width, height);

    }
    private static int xunhuan(int x,int y,int [] yS,int height,int width,int i,int k,int Y) {
        for(x=(i-10)>0?(i-10):0;x<((i+10)>height?height:(i+10));x++){
            for (y=(k-10)>0?(k-10):0;y<((k+10)>width?width:(k+10));k++){
               // Y+=yS[x*width+y];
                //Log.i("zhz","x y"+x+y);
               // Log.i("zhz","xunhuan响应过Y"+Y);
            }
        }
        return Y;
    }


    public void keepgreen(Bitmap bitmap1,Bitmap bitmap2){
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();
        int r = 0;
        int g = 0;
        int b = 0;
        int T = 0;
        float h = 0;
        int pixColor =0;
        int k;
        int i;
        int[] srcPixels = new int[width * height];
        int newcolor;
        bitmap1.getPixels(srcPixels, 0, width, 0, 0, width, height);
        for ( i = 0; i < height; i++) {
            for ( k = 0; k < width; k++){
                pixColor = srcPixels[i*width+k];
                r = Color.red(pixColor);
                g = Color.green(pixColor);
                b = Color.blue(pixColor);
                float maxColor = Math.max(r, Math.max(g, b));
                float minColor = Math.min(r, Math.min(g, b));
                if (r == maxColor)
                    h = (g - b) / (maxColor - minColor);
                else if (g == maxColor)
                    h = (float) (2.0 + (b - r) / (maxColor - minColor));
                else
                    h = (float) (4.0 + (r - g) / (maxColor - minColor));

                h /= 6;
                if (h < 0)
                    h++;
                T= (int) Math.round(h * 240.0);
                newcolor = (int) (r * 0.3 + g * 0.59 + b * 0.11);
                if (T<55||T>115){
                    srcPixels[i * width + k] = Color.argb(255, newcolor, newcolor, newcolor);
                }

            }
        }
        bitmap2.setPixels(srcPixels, 0, width, 0, 0, width, height);
    }
    public static int getH(Bitmap bitmap){
        int T = 0;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float r = 0;
        float g = 0;
        float b = 0;
        float h = 0;
        int pixColor =0;
        int[] srcPixels = new int[width * height];
        bitmap.getPixels(srcPixels, 0, width, 0, 0, width, height);
        for ( int i = 0; i < height; i++) {
            for (int k = 0; k < width; k++) {
                pixColor=srcPixels[i*width+k];
                r = Color.red(pixColor)/ 255.0f;
                g = Color.green(pixColor)/ 255.0f;
                b = Color.blue(pixColor)/ 255.0f;
                float maxColor = Math.max(r, Math.max(g, b));
                float minColor = Math.min(r, Math.min(g, b));
                if (r == maxColor)
                    h = (g - b) / (maxColor - minColor);
                else if (g == maxColor)
                    h = (float) (2.0 + (b - r) / (maxColor - minColor));
                else
                    h = (float) (4.0 + (r - g) / (maxColor - minColor));

                h /= 6;
                if (h < 0)
                    h++;
                T += (int) Math.round(h * 240.0);
            }
        }
        T/=(width * height);
        return T;
    }
    public void makeComicFilter(Bitmap bitmap1,Bitmap bitmap2){
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();
        int k;
        int i;
        int r = 0;
        int g = 0;
        int b = 0;
        int pixel;
        int[] srcPixels = new int[width * height];
        int pixColor =0;
        int newcolor;
        bitmap1.getPixels(srcPixels, 0, width, 0, 0, width, height);
        for (  i = 0; i < height; i++) {
            for ( k = 0; k < width; k++) {
                pixColor=srcPixels[i*width+k];
                r = Color.red(pixColor);
                g = Color.green(pixColor);
                b = Color.blue(pixColor);
                // R = |g �C b + g + r| * r / 256;
                pixel = g - b + g + r;
                if (pixel < 0)
                    pixel = -pixel;
                pixel = pixel * r / 256;
                if (pixel > 255)
                    pixel = 255;
                r = pixel;

                // G = |b �C g + b + r| * r / 256;
                pixel = b - g + b + r;
                if (pixel < 0)
                    pixel = -pixel;
                pixel = pixel * r / 256;
                if (pixel > 255)
                    pixel = 255;
                g = pixel;

                // B = |b �C g + b + r| * g / 256;
                //pixel = b - g + b + r;
                pixel = r-g+r+b;
                if (pixel < 0)
                    pixel = -pixel;
                pixel=pixel*b/256;
                //pixel = pixel * g / 256;
                if (pixel > 255)
                    pixel = 255;
                b = pixel;
                //newcolor = (int) (r * 0.3 + g * 0.59 + b * 0.11);
                srcPixels[i * width + k] = Color.argb(255, r,g,b);
                //srcPixels[i * width + k] = Color.argb(255, newcolor,newcolor,newcolor);
            }
        }
        bitmap2.setPixels(srcPixels, 0, width, 0, 0, width, height);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
