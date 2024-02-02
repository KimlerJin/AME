//package com.ame.util.serialize;
//
//
//
//import java.io.*;
//
//public abstract class SerializationUtil {
//
//    private SerializationUtil() {
//    }
//
//    /**
//     * 序列化（对象 -> 字节数组） with Protostuff
//     */
//    @SuppressWarnings("unchecked")
//    public static <T> byte[] serialize(T obj) {
//        Class<T> cls = (Class<T>) obj.getClass();
//        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
//        try {
//            return ProtostuffIOUtil.toByteArray(obj, RuntimeSchema.getSchema(cls), buffer);
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        } finally {
//            buffer.clear();
//        }
//    }
//
//    /**
//     * 反序列化（字节数组 -> 对象） with Protostuff
//     */
//    public static <T> T deserialize(byte[] data, Class<T> cls) {
//        if (data == null) {
//            return null;
//        }
//        try {
//            Schema<T> schema = RuntimeSchema.getSchema(cls);
//            T message = schema.newMessage();
//            ProtostuffIOUtil.mergeFrom(data, message, schema);
//            return message;
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 序列化（对象 -> 字节数组） with JDk
//     */
//    public static byte[] jdkSerialize(Object obj) {
//        byte[] bytes = null;
//        try (
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                ObjectOutputStream oos = new ObjectOutputStream(bos);) {
//            oos.writeObject(obj);
//            oos.flush();
//            bytes = bos.toByteArray();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return bytes;
//    }
//
//    /**
//     * 反序列化（字节数组 -> 对象）With jdk
//     */
//    public static <T> T jdkDeserialize(byte[] data, Class<T> cls) {
//        T obj = null;
//        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
//             ObjectInputStream ois = new ObjectInputStream(bis);) {
//            obj = (T) ois.readObject();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }
//        return obj;
//    }
//
//}
