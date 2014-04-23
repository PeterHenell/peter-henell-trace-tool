package databasetracing.transformers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import databasetracing.tracing.TraceResult;

public class ImageTransformer extends AbstractTraceResultTransformer implements TraceResultTransformer<BufferedImage> {

    public BufferedImage transformFrom(TraceResult traceResult) {
        ImageDrawer drawer = new ImageDrawer(ImageDrawerColorProfile.getPrintableColorProfile());
        return drawer.draw(traceResult);
    }


    public void saveTransformation(TraceResult traceResult, String outputFolder) {
        try {
            System.out.println("saving image: " + getOutputFileName(traceResult, outputFolder));
            File outputfile = getOutputFile(traceResult, outputFolder);
            BufferedImage bi = transformFrom(traceResult);
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public String getFileSuffix() {
        return ".png";
    }

}
