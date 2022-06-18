package surreal.ttweaker.core;

import com.google.common.collect.Maps;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;
import scala.tools.asm.Type;

import java.util.Map;
import java.util.function.Consumer;

import static org.objectweb.asm.Opcodes.*;

public class TTweakerClassTransformer implements IClassTransformer {
    private static final String HOOKS = Type.getInternalName(TTweakerHooks.class);
    private static final Map<String, Consumer<ClassNode>> MAP = Maps.newHashMap();

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (MAP.containsKey(transformedName)) {
            TTweakerLoadingPlugin.LOGGER.info("Manipulating " + transformedName);
            return transform(basicClass, MAP.get(transformedName));
        }
        return basicClass;
    }

    private byte[] transform(byte[] classBeingTransformed, Consumer<ClassNode> consumer) {
        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(classBeingTransformed);
            classReader.accept(classNode, 0);

            consumer.accept(classNode);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classBeingTransformed;
    }

    static {
        MAP.put("net.minecraft.tileentity.TileEntityBrewingStand", cls -> {
            for (MethodNode method : cls.methods) {
                if (method.name.equals("update")) {
                    AbstractInsnNode node = null;
                    for (AbstractInsnNode n : method.instructions.toArray()) {
                        if (n.getOpcode() == ALOAD && n.getPrevious().getOpcode() == IFGT) {
                            node = n.getNext();
                            break;
                        }
                    }

                    if (node != null) {
                        AbstractInsnNode amongus = null;

                        for (int i = 0; i < 3; i++) {
                            if (i == 2) amongus = new JumpInsnNode(IFEQ, ((JumpInsnNode) node).label);
                            node = node.getNext();
                            method.instructions.remove(node.getPrevious());
                        }

                        InsnList list = new InsnList();
                        list.add(new MethodInsnNode(INVOKESTATIC, HOOKS, "hasFuel", "(Lnet/minecraft/item/ItemStack;)Z", false));
                        list.add(amongus);
                        method.instructions.insertBefore(node, list);
                    }
                }
                if (method.name.equals("isItemValidForSlot")) {
                    AbstractInsnNode node = null;
                    for (AbstractInsnNode n : method.instructions.toArray()) {
                        if (n.getOpcode() == ALOAD && n.getNext().getOpcode() == INVOKEVIRTUAL) {
                            node = n;
                            break;
                        }
                    }

                    if (node != null) {
                        for (int i = 0; i < 3; i++) {
                            node = node.getNext();
                            method.instructions.remove(node.getPrevious());
                        }

                        for (int i = 0; i < 7; i++) {
                            node = node.getNext();
                        }

                        for (int i = 0; i < 10; i++) {
                            node = node.getNext();
                            method.instructions.remove(node.getPrevious());
                        }

                        InsnList list = new InsnList();
                        list.add(new VarInsnNode(ALOAD, 2));
                        list.add(new MethodInsnNode(INVOKESTATIC, HOOKS, "hasFuel", "(Lnet/minecraft/item/ItemStack;)Z", false));
                        method.instructions.insertBefore(node, list);
                    }

                    break;
                }
            }
        });

        MAP.put("net.minecraft.inventory.ContainerBrewingStand$Fuel", cls -> {
            for (MethodNode method : cls.methods) {
                if (method.name.equals("isValidBrewingFuel")) {
                    AbstractInsnNode node = null;
                    for (AbstractInsnNode n : method.instructions.toArray()) {
                        if (n.getOpcode() == ALOAD) {
                            node = n.getNext();
                            break;
                        }
                    }

                    if (node != null) {
                        for (int i = 0; i < 10; i++) {
                            node = node.getNext();
                            method.instructions.remove(node.getPrevious());
                        }

                        InsnList list = new InsnList();
                        list.add(new MethodInsnNode(INVOKESTATIC, HOOKS, "hasFuel", "(Lnet/minecraft/item/ItemStack;)Z", false));
                        method.instructions.insertBefore(node, list);
                    }

                    break;
                }
            }
        });
    }
}
