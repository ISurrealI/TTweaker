package surreal.ttweaker.core.util;

import net.minecraft.client.Minecraft;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ASMUtil {

    public static void writeClass(ClassNode cls) {
        FileOutputStream stream;
        File file = new File(Minecraft.getMinecraft().gameDir, "classOutputs/" + cls.name + ".class");
        file.getParentFile().mkdirs();

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cls.accept(writer);

        try {
            stream = new FileOutputStream(file);
            stream.write(writer.toByteArray());
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
