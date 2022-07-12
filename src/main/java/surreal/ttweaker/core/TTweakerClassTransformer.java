package surreal.ttweaker.core;

import com.google.common.collect.Maps;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;
import scala.tools.asm.Type;

import java.util.Iterator;
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
                if (method.name.equals(TTweakerLoadingPlugin.deobf ? "update" : "func_73660_a")) {
                    AbstractInsnNode node = null;
                    for (AbstractInsnNode n : method.instructions.toArray()) {
                        if (/*n.getOpcode() == ALOAD && n.getPrevious().getOpcode() == IFGT*/
                        n.getOpcode() == GETFIELD && n.getNext().getOpcode() == IFGT) {
                            node = n.getNext();
                            break;
                        }
                    }

                    if (node != null) {
                        AbstractInsnNode amongus = new JumpInsnNode(IF_ICMPGE, ((JumpInsnNode) node).label);

                        method.instructions.insertBefore(node, new IntInsnNode(BIPUSH, 20));
                        node = node.getNext();
                        method.instructions.remove(node.getPrevious());
                        method.instructions.insertBefore(node, amongus);
                        node = node.getNext();

                        for (int i = 0; i < 3; i++) {
                            if (i == 2) amongus = new JumpInsnNode(IFEQ, ((JumpInsnNode) node).label);
                            node = node.getNext();
                            method.instructions.remove(node.getPrevious());
                        }

                        InsnList list = new InsnList();
                        list.add(new MethodInsnNode(INVOKESTATIC, HOOKS, "hasFuel", "(Lnet/minecraft/item/ItemStack;)Z", false));
                        list.add(amongus);
                        method.instructions.insertBefore(node, list);

                        node = node.getNext().getNext().getNext().getNext();
                        method.instructions.remove(node.getPrevious());

                        list.clear();
                        list.add(new InsnNode(DUP));
                        list.add(new FieldInsnNode(GETFIELD, "net/minecraft/tileentity/TileEntityBrewingStand", "fuel", "I"));
                        list.add(new VarInsnNode(ALOAD, 1));
                        list.add(new MethodInsnNode(INVOKESTATIC, HOOKS, "getFuelValue", "(Lnet/minecraft/item/ItemStack;)I", false));
                        list.add(new InsnNode(IADD));
                        method.instructions.insertBefore(node, list);

                        /*
                        * methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitFieldInsn(GETFIELD, "surreal/ttweaker/TTweaker", "fuck", "I");
            methodVisitor.visitVarInsn(ILOAD, 1);
            methodVisitor.visitInsn(IADD);
            methodVisitor.visitFieldInsn(PUTFIELD, "surreal/ttweaker/TTweaker", "fuck", "I");
                        * */
                    }
                }
                if (method.name.equals(TTweakerLoadingPlugin.deobf ? "isItemValidForSlot" : "func_94041_b")) {
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
        MAP.put("net.minecraft.inventory.ContainerBrewingStand", (cls) -> {
            Iterator<MethodNode> var1 = cls.methods.iterator();

            while(true) {
                MethodNode method;
                AbstractInsnNode node;
                do {
                    do {
                        if (!var1.hasNext()) {
                            return;
                        }

                        method = var1.next();
                    } while(!method.name.equals("<init>"));

                    node = null;
                    AbstractInsnNode[] var4 = method.instructions.toArray();

                    for (AbstractInsnNode n : var4) {
                        if (n.getOpcode() == 187 && ((TypeInsnNode) n).desc.equals("net/minecraft/inventory/ContainerBrewingStand$Fuel")) {
                            node = n;
                        }
                    }
                } while(node == null);

                for(int i = 0; i < 7; ++i) {
                    node = node.getNext();
                    method.instructions.remove(node.getPrevious());
                }

                InsnList list = new InsnList();
                list.add(new VarInsnNode(25, 2));
                list.add(new InsnNode(7));
                list.add(new IntInsnNode(16, 17));
                list.add(new IntInsnNode(16, 17));
                list.add(new MethodInsnNode(184, HOOKS, "getFuelSlot", "(Lnet/minecraft/inventory/IInventory;III)Lsurreal/ttweaker/core/TTweakerHooks$Fuel;", false));
                method.instructions.insertBefore(node, list);
            }
        });
    }
}
