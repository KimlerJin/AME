package com.ame.util.net;


import com.ame.core.exception.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Scanner;

public final class NetUtils {

    private static Logger logger = LoggerFactory.getLogger(NetUtils.class);

    /**
     * Pre-loaded local address
     */
    private static InetAddress localIpAddress;

    private static String localMacAddress;

    private static String[] localMacAddresses = new String[50];

    private static String cpuId = "";

    private static String motherboardSn = "";

    static {
        try {
            Enumeration<NetworkInterface> enumerations = NetworkInterface.getNetworkInterfaces();
            int count = 0;
            while (enumerations.hasMoreElements()) {
                NetworkInterface networkInterface = enumerations.nextElement();

                // 获取mac地址
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < mac.length; i++) {
                        if (i != 0) {
                            sb.append("-");
                        }
                        // mac[i] & 0xFF 是为了把byte转化为正整数
                        String s = Integer.toHexString(mac[i] & 0xFF);
                        sb.append(s.length() == 1 ? 0 + s : s);
                    }
                    localMacAddresses[count] = sb.toString().toUpperCase();
                    logger.info("----Host Mac Address:" + localMacAddresses[count]);
                    count++;
                }

                // 获取ip地址
                if (!networkInterface.isLoopback()) {
                    Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
                    while (addressEnumeration.hasMoreElements()) {
                        InetAddress address = addressEnumeration.nextElement();

                        // ignores all invalidated addresses
                        if (address.isLinkLocalAddress() || address.isLoopbackAddress()
                            || address.isAnyLocalAddress()) {
                            continue;
                        }
                        localIpAddress = address;
                        localMacAddress = localMacAddresses[count - 1];
                    }
                }
            }

            if (localMacAddresses.length < 1 || localIpAddress == null) {
                throw new PlatformException(
                    "Can not get the local ip/mac address");
            }

//            getUnique();

        } catch (Exception e) {
            logger.error("", e);
            throw new PlatformException(
                "Can not get the local ip/mac address");
        }
    }


    private static void getUnique() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            cpuId = getWindowsCpuId();
            motherboardSn = getWindowsMotherboardSn();
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            cpuId = getUnixCpuId();
            motherboardSn = getUnixMotherboardSn();
        } else {
            throw new PlatformException("Unsupported operating system");
        }
    }


    private static String getWindowsCpuId() {
        try {
            Process process = new ProcessBuilder("wmic", "cpu", "get", "ProcessorId").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.trim().equalsIgnoreCase("ProcessorId")) {
                    return line.trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new PlatformException("getWindowsCpuId error");
        }
        return null;
    }

    private static String getUnixCpuId() {
        try {
            Process process = new ProcessBuilder("sh", "-c", "cat /proc/cpuinfo | grep 'processor' | awk '{print $3}'").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    return line.trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new PlatformException("getUnixCpuId error");
        }
        return null;
    }

    private static String getWindowsMotherboardSn() {
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "baseboard", "get", "serialnumber"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            while (sc.hasNext()) {
                result = sc.next().trim();
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new PlatformException("getWindowsMotherboardSn error");
        }
        return result;
    }

    private static String getUnixMotherboardSn() {
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"sudo", "dmidecode", "-t", "baseboard"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.contains("Serial Number")) {
                    result = line.split(":")[1].trim();
                    break;
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new PlatformException("getUnixMotherboardSn error");
        }
        return result;
    }



    /**
     * Retrieve local address
     *
     * @return the string local address
     */
    public static String getLocalIpAddress() {
        return localIpAddress.getHostAddress();
    }

    public static String[] getLocalMacAddresses() {
        return localMacAddresses;
    }

    public static String getLocalMacAddress() {
        return localMacAddress;
    }


    public static String getCpuId() {
        return cpuId;
    }


    public static String getMotherboardSn() {
        return motherboardSn;
    }

}
